package br.com.zup.carros

import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(
    rollback = false,  // faz commit no final de cada transação
    transactionMode = TransactionMode.SINGLE_TRANSACTION,  // @BeforeEach e @Test sao rodados na mesma trasação. @CleanUp é rodado em transação separado
    transactional = false // Em cenarios de multipla concorrencia, api grpc ou threads e não queira que o micronaut abra uma transação então o teste não vai mais abrir fazer commit ou rollback das transação. Ou seja cada chamada para repositorio vai ser auto commit por padrão
)
class CarroTest {

    /*
   Estratégias:
   louça : sujou, limpou -> @AfterEach
   louça : limpou, usou  -> @BeforeEach
   louça: usa louça descartável -> rollback = true (Pode dar falso positivo)
   louça: usa a louça, joga fora, compra nova -> recriar o banco a cada teste
    */

    @Inject
    lateinit var repository: CarroRepository

    // E aberto uma transação para o AfterEach e para o BeforeEach

    @BeforeEach //É executado antes de cada teste
    fun setup() {
        repository.deleteAll()

    }

    @AfterEach // O método anotado deve ser executado após cada @Test
    fun cleanUp() {
        repository.deleteAll()

    }

    @Test //inicia a transação
    fun `deve inserir um novo carro`() {

        // ação
        repository.save(Carro("Gol", "HPX-1234"))

        //validação
        assertEquals(1, repository.count())
    } //padrão rollback para não afetar o banco


    @Test
    fun `deve encontrar carro por placa`() {
        // cenario
        repository.save(Carro("Palio", "OIP-9876"))

        // ação
        val encontrado = repository.existsByPlaca("OIP-9876")

        // validação
        assertTrue(encontrado)
    }

}
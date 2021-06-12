package br.com.zup.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastraAutorControllerTest {

    //Lateinit indica que será inicializado no futuro!
    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    //Já definimos no método enderecoMock, que esse objeto será mockado
    //Criamos uma referencia agora, apenas para podermos configurar suas ações!
    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @Test
    internal fun `deve cadastrar um novo autor`() {

        //Cenario
        val autorRequest = NovoAutorRequest(
            "Rafael Pontes",
            "rafael.ponte@zup.com.br",
            "O mentor",
            "123456",
            "1"
        )

        val enderecoResponse = EnderecoResponse(
            "Rua das Laranjeiras",
            "Rio de Janeiro",
            "RJ"
        )

        //Aqui estamos simulando: quando o método consulta do enderecoCliente for executado a resposta será HttpResponse.ok com Body de enderecoResponse já criado por nós!
        Mockito.`when`(enderecoClient.consulta(autorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("/autores", autorRequest)

        //Açao
        val response = client.toBlocking().exchange(request, Any::class.java)

        //Verificacao
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))

    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock() : EnderecoClient{
        return Mockito.mock(EnderecoClient::class.java)
    }
}
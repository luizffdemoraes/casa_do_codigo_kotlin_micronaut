package br.com.zup.autores


import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put

import javax.inject.Inject
import javax.transaction.Transactional

@Controller(value = "/autores/{id}")
class AtualizaAutorController(@Inject val autorRepository: AutorRepository){

    /*
        Busca o objeto no banco
        caso não encontre informe a inexistencia do autor
        atualiza o campo
        retorna ok
        No Micronaut temos o save que é um insert
        enquanto o update realiza uma atualização

        ->  Nos estamos usando o Micronaut Data JPA
        no JPA quando fazemos uma query e pegamos uma entidade
        em vem como manager e quando esta neste estado conseguimos fazer qualquer alteração
        e quando fecha a conexão com banco de dados/ transação as alterações com os atributos
        são aplicadas como update na tabela
     */
    @Put
    @Transactional
    fun atualizar(@PathVariable id: Long, @Body descricao: String) : HttpResponse<Any>  {
       val possivelAutor = autorRepository.findById(id)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        autor.descricao = descricao

        //Não e mais necessário pois colocamos o @Transactional o update agora é implicito
//        autorRepository.update(autor)

        return HttpResponse.ok(DetalhesDoAutorResponse(autor))



    }

}


package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated

import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(@Inject val autorRepository: AutorRepository) {

   /*
     HttpResponse é um tipo parametrizado <> é necessário informar qual e o tipo do corpo de resposta
    - Ao informar o Any ele é analogo ao Object do Java, ao informar o any estamos dando um retorno
    de corpo generico
    */
    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest): HttpResponse<Any> {

        //request => domínio

        println("Requisicao => ${request}")

        val autor = request.paraAutor()
        autorRepository.save(autor)

        println("Autor => ${autor.nome}")

       val uri = UriBuilder.of("/autores/{id}")
           .expand(mutableMapOf(Pair("id", autor.id)))

       return HttpResponse.created(uri)

    }


}
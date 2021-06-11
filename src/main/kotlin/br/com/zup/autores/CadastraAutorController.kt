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
class CadastraAutorController(
    val autorRepository: AutorRepository,
    val enderecoClient: EnderecoClient
) {


    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest): HttpResponse<Any> {

        println("Requisicao => ${request}")

        val enderecoResponse = enderecoClient.consulta(request.cep)

        val autor = request.paraAutor(enderecoResponse.body()!!)

        autorRepository.save(autor)

        println("Autor => ${autor.nome}")

        val uri = UriBuilder.of("/autores/{id}")
            .expand(mutableMapOf(Pair("id", autor.id)))

        return HttpResponse.created(uri)

    }

    /*
     HttpResponse é um tipo parametrizado <> é necessário informar qual e o tipo do corpo de resposta
    - Ao informar o Any ele é analogo ao Object do Java, ao informar o any estamos dando um retorno
    de corpo generico
     request => domínio
     fazer uma requisição para um serviço externo vamos criar um Client HTTP
              ter a certeza que o endereço não vai ser null
       if (enderecoResponse.body() == null) {
           return HttpResponse.badRequest()
       }

       !! confiar cegamente
    */


}
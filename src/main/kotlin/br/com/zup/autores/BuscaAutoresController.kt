package br.com.zup.autores


import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

import javax.inject.Inject

@Controller("/autores")
class BuscaAutoresController(@Inject val autorRepository: AutorRepository) {


    /*
    pegar autores do banco de dados
    converter para um objeto dto de saida
    map para cada autor retorna um objeto do tipo detalhes
    retornar essa lista
     */
    @Get
    fun lista() : HttpResponse<List<DetalhesDoAutorResponse>> {
        val autores = autorRepository.findAll()

        val resposta = autores.map { autor -> DetalhesDoAutorResponse(autor) }

        return HttpResponse.ok(resposta)

    }

}
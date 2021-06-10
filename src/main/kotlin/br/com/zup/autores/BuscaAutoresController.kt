package br.com.zup.autores


import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

import javax.inject.Inject

@Controller("/autores")
class BuscaAutoresController(@Inject val autorRepository: AutorRepository) {


    /*
    pegar autores do banco de dados
    converter para um objeto dto de saida
    map para cada autor retorna um objeto do tipo detalhes
    retornar essa lista
    -> Passar parametro na Query autores?email=rafael.ponta@zup.com.br
    precisamos informar um valor default = "" caso contrario
    sempre solicitará no momento da requisição na URI
     */
    @Get
    fun lista(@QueryValue(defaultValue = "") email: String): HttpResponse<Any> {

        if (email.isBlank()) {
            val autores = autorRepository.findAll()

            val resposta = autores.map { autor -> DetalhesDoAutorResponse(autor) }

            return HttpResponse.ok(resposta)

        }

        // Ir no banco de dados e realizar a busca dado um email
        val possivelAutor = autorRepository.findByEmail(email)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor =  possivelAutor.get()

        return HttpResponse.ok(DetalhesDoAutorResponse(autor))

    }

}
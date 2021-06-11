package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client


@Client("\${cep.url}")
interface EnderecoClient {


    @Get("/{cep}/json/")
    @Consumes(MediaType.APPLICATION_XML)
    fun consulta(cep: String): HttpResponse<EnderecoResponse>

}

    /*
    Alteração para retorno em xml metodo Get - parametro consumes
    caso fosse um metodo Post - parametro produces
    consumes = [MediaType.APPLICATION_XML]
    */
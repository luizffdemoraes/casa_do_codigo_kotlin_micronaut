package br.com.zup.autores


import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put

import javax.inject.Inject

@Controller(value = "/autores/{id}")
class AtualizaAutorController(@Inject val autorRepository: AutorRepository){

    /*
        Busca o objeto no banco
        caso não encontre informe a inexistencia do autor
        atualiza o campo
        retorna ok
        No Micronaut temos o save que é um insert
        enquanto o update realiza uma atualização
     */
    @Put
    fun atualizar(@PathVariable id: Long, @Body descricao: String) : HttpResponse<Any>  {
       val possivelAutor = autorRepository.findById(id)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        autor.descricao = descricao

        autorRepository.update(autor)

        return HttpResponse.ok(DetalhesDoAutorResponse(autor))



    }

}


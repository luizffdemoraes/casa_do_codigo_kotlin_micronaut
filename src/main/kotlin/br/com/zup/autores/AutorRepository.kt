package br.com.zup.autores

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface AutorRepository: JpaRepository<Autor, Long> {

  // -> Writing Queries
  fun findByEmail(email: String): Optional<Autor>

  // -> Explicit Queries
  @Query("SELECT a FROM Autor a WHERE a.email = :email", nativeQuery = true)
  fun buscaPorEmail(email: String) : Optional<Autor>

}
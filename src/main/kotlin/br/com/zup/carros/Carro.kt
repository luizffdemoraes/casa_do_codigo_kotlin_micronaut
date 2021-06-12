package br.com.zup.carros

import io.micronaut.core.annotation.Introspected
import javax.persistence.*

import javax.validation.constraints.NotBlank

@Entity
data class Carro(
    @field:NotBlank
    @Column(nullable = false)
    val modelo: String,

    @field:NotBlank
    @Column(nullable = false, unique = true)
    val placa: String // AAA-9A99
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}

package br.com.zup.carros

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented //Documentada na Java DOC
@Target(FIELD, CONSTRUCTOR) //Podemos utilizar em propriedades, campos e no contrutor da classe
@Retention(RUNTIME) //Java reter anotação em tempo de execução
@Constraint(validatedBy = [PlacaValidator::class]) //Classe que vai validar de fato
annotation class Placa(val message: String = "placa formato inválido")

//ConstraintValidator do micronaut - vantagem mais rapida pois o micronaut
// evita uso de reflexão e sim introspecção em tempo de execução
@Singleton
class PlacaValidator: ConstraintValidator<Placa, String> {
    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<Placa>,
        context: ConstraintValidatorContext
    ): Boolean {

        if (value == null) {
            return true
        }

        //https://ricardo.coelho.eti.br/regex-mercosul/ - AAA-0A00 - Expressão regular - [A-Z]{3}[0-9][0-9A-Z][0-9]{2}
        return value.matches("[A-Z]{3}[0-9][0-9A-Z][0-9]{2}".toRegex())

    }


}

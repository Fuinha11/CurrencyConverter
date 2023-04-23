package com.example.plugins

import com.example.exception.InvalidCurrencyException
import com.example.exception.MissingParameterException
import com.example.service.getConversion
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val allowedCurrencies = listOf(
    "EUR", "USD", "GBP", "JPY"
)


fun Application.configureRouting() {
    routing {
        get("/convert") {
            val amount = (this.context.parameters["amount"] ?: throw MissingParameterException("amount")).toInt()
            val convertFrom = this.context.parameters["convertFrom"] ?: throw MissingParameterException("convertFrom")
            val convertTo = this.context.parameters["convertTo"] ?: throw MissingParameterException("convertTo")
            if (!allowedCurrencies.contains(convertFrom))
                throw InvalidCurrencyException(convertFrom)
            if (!allowedCurrencies.contains(convertTo))
                throw InvalidCurrencyException(convertTo)
            val conversion = getConversion(convertFrom, convertTo)
            val converted = amount * conversion
            call.respondText("value = $converted")
        }
    }
}

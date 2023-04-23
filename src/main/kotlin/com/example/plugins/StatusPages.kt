package com.example.plugins

import com.example.exception.IntegrationException
import com.example.exception.InvalidCurrencyException
import com.example.exception.MissingParameterException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatus() {
    install(StatusPages){
        exception<Throwable> { call, cause ->
            when (cause) {
                is MissingParameterException -> call.respondText(text = "The parameter ${cause.parameterName} is missing." , status = HttpStatusCode.BadRequest)
                is InvalidCurrencyException -> call.respondText(text = "The currency ${cause.currency} is not valid." , status = HttpStatusCode.BadRequest)
                is NumberFormatException -> call.respondText(text = "Amount must be a valid Integer." , status = HttpStatusCode.BadRequest)
                is IntegrationException -> call.respondText(text = "" , status = HttpStatusCode.ServiceUnavailable)
                else -> call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
            }
        }
    }
}
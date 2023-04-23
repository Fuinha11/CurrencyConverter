package com.example.service

import com.example.exception.IntegrationException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class FixerResponse(
    val base: String,
    val date: String,
    val success: String,
    val timestamp: String,
    val rates: Map<String, Double>
)

suspend fun getConversion(fromCurrency: String, toCurrency: String) : Double {
    val apiKey = "YQ53g8gG5WxLhpU8nkQuaUCwVX5OR3vi"
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }
    val response = client.get("https://api.apilayer.com/fixer/latest") {
        url {
            headers.append("apikey", apiKey)
            parameters.append("base", fromCurrency)
            parameters.append("symbols", toCurrency)
        }
    }
    if (response.status.value in 200..299) {
        val body: FixerResponse = response.body()
        return body.rates.get(toCurrency)!!
    } else throw IntegrationException()
}


package com.example

import com.example.exception.InvalidCurrencyException
import com.example.exception.MissingParameterException
import com.example.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test //this is a flaky test
    fun testSuccess() = testApplication {
        application {
            configureRouting()
        }
        val response = client.get("/convert?convertFrom=USD&convertTo=EUR&amount=1000")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test(expected = MissingParameterException::class)
    fun testMissingParameter() = testApplication {
        application {
            configureRouting()
        }
        client.get("/convert?convertFrom=USD&convertTo=EUR")
    }

    @Test(expected = InvalidCurrencyException::class)
    fun testInvalidCurrency() = testApplication {
        application {
            configureRouting()
        }
        client.get("/convert?convertFrom=USD&convertTo=ERR&amount=1000")
    }
}

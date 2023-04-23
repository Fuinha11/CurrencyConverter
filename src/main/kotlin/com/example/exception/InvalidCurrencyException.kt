package com.example.exception

class InvalidCurrencyException(
    val currency: String
): Throwable()
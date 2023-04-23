package com.example.exception

class MissingParameterException(
    val parameterName: String
): Throwable()
package com.example.exception

import java.lang.reflect.Parameter

class MissingParameterException(
    val parameterName: String
): Throwable()
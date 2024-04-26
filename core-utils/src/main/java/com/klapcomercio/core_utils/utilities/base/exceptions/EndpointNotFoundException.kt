package com.klapcomercio.core_utils.utilities.base.exceptions

private const val MESSAGE: String = "Endpoint call not found exception"

class EndpointNotFoundException(code: Int) : RepositoryException(code, MESSAGE)
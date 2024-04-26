package com.klapcomercio.core_utils.utilities.base.exceptions

private const val MESSAGE: String = "Bad Credentials"
private const val CODE = 401

object WrongCredentialsException : RepositoryException(CODE, MESSAGE)
package com.klapcomercio.core_utils.utilities.base.exceptions

private const val MESSAGE: String = "Session expired"
private const val CODE = 401

object SessionExpiredException : RepositoryException(CODE, MESSAGE)
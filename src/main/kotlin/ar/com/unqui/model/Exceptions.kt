package ar.com.unqui.model

class DuplicateItemException(message: String) : Exception(message)
class NotFoundUser: Exception("User not found")
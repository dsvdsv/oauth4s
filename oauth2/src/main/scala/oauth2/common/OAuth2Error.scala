package oauth2.common

sealed trait OAuth2Error extends Throwable with Product with Serializable

object OAuth2Error {}

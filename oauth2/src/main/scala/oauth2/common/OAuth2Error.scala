package oauth2.common

sealed trait OAuth2Error extends Throwable with Product with Serializable

object OAuth2Error {
  case object InvalidRedirectUrlConfiguration
      extends RuntimeException("At least one redirect_uri must be registered with the client.")
      with OAuth2Error

  case class InvalidRedirectUrl(redirect: RedirectUri)
      extends RuntimeException(s"Invalid redirect: $redirect does not match one of the registered values.")
      with OAuth2Error

  case object ClientNotExist           extends RuntimeException("Client does not exit") with OAuth2Error
  case object ResponseTypeNotSupported extends RuntimeException("Response type not supported") with OAuth2Error
  case object WrongScopes              extends RuntimeException("Wrong scopes") with OAuth2Error
  case object WrongScopeConfiguration  extends RuntimeException("Client scopes do not configure") with OAuth2Error
}

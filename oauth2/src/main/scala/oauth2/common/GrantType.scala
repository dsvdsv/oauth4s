package oauth2.common

abstract sealed class GrantType private (val repl: String) extends Serializable with Product

object GrantType {
  case object AuthorizationCode extends GrantType("authorization_code")
  case object RefreshToken extends GrantType("refresh_token")
  case object ClientCredentials extends GrantType("client_credentials")
  case object Password extends GrantType("password")
  case object Implicit extends GrantType("implicit")
  case object DeviceCode extends GrantType("device_code")

}

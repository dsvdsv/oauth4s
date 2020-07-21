package oauth2.common
package authorize

sealed trait AuthorizeResp extends Serializable with Product

object AuthorizeResp {
  case class Implicit(token: String)                                   extends AuthorizeResp
  case class AuthorizationCode(code: String, redirectUri: RedirectUri) extends AuthorizeResp
}

package oauth2.common.authorize

import oauth2.common.UserId

class Auth[F[_]] {
  def approveOrDeny(userId: UserId, authorizeReq: AuthorizeReq): F[AuthorizeResp] =
    ???
}

object Auth {
  def make[F[_]](): F[Auth[F]] = ???
}

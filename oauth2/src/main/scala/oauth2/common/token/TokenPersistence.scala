package oauth2.common.token

import cats.tagless.{autoFunctorK, finalAlg}

@autoFunctorK @finalAlg
trait TokenPersistence[F[_]] {
  def loadToken(tokenValue: String): F[Option[Token]]

  def storeToken(token: Token): F[Unit]
}

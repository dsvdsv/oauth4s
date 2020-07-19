package oauth2.common.client

import cats.tagless.{autoFunctorK, finalAlg}

@autoFunctorK @finalAlg
trait ClientStore[F[_]] {
  def loadClient(clientId: String): F[Option[Client]]
}

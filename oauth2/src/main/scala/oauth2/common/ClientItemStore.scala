package oauth2.common

import cats.tagless.{autoFunctorK, finalAlg}

@autoFunctorK @finalAlg
trait ClientItemStore[F[_]] {
  def loadClientItem(clientId: String): F[Option[ClientItem]]
}

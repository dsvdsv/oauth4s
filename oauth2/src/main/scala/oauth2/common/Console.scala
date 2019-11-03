package oauth2.common

import cats.tagless._

@autoFunctorK @finalAlg
trait Console[F[_]] {
  def read: F[String]
}

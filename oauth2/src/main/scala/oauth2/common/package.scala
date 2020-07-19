package oauth2

import cats.mtl.{ApplicativeAsk, FunctorRaise}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.Url

package object common {
  type OAuth2ErrorThrow[F[_]] = FunctorRaise[F, OAuth2Error]
  type OAuth2ConfigAsk[F[_]] = ApplicativeAsk[F, OAuth2Config]

  type ClientId = String Refined NonEmpty
  type UserId = String Refined NonEmpty

  type Scope = String Refined NonEmpty
  type RedirectUri = String Refined Url
}

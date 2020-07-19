package oauth2.common
package authorize

import java.time.Instant
import java.util.concurrent.TimeUnit

import client._
import cats.data.NonEmptySet
import cats.Monad
import cats.effect.Clock
import cats.implicits._
import cats.mtl.FunctorRaise
import eu.timepit.refined.cats._
import cats.mtl.syntax.all._

import scala.collection.immutable.SortedSet

class Auth[F[_]: Monad: OAuth2ErrorThrow: OAuth2ConfigAsk] private (
    clientStore: ClientStore[F],
    codeGenerator: CodeGenerator[F],
    clock: Clock[F]
) {
  def approveOrDeny(userId: UserId, req: AuthorizeReq): F[AuthorizeResp] =
    req.responseType match {
      case ResponseType.Code =>
        for {
          clientOpt <- clientStore.loadClient(req.clientId)
          client    <- clientOpt.fold(OAuth2Error.ClientNotExist.raise[F, Client])(_.pure)

          redirectUrl <- pickupRedirectUrl(req.redirectUri, client)
          scopes      <- chooseScope(req.scope, client.scopes)
          expires     <- computeExpiry
        } yield ()
      case ResponseType.Token =>
        ???
    }

  private def pickupRedirectUrl(requestedRedirectOpt: Option[RedirectUri], client: Client): F[RedirectUri] = {
    val sortedUrls = SortedSet.empty[RedirectUri] ++ client.registeredRedirectUri
    NonEmptySet
      .fromSet(sortedUrls)
      .fold(OAuth2Error.InvalidRedirectUrlConfiguration.raise[F, RedirectUri]) { redirectUrls =>
        requestedRedirectOpt.fold(redirectUrls.head.pure[F]) { requestedRedirect =>
          if (redirectUrls.contains_(requestedRedirect)) {
            requestedRedirect.pure[F]
          } else {
            OAuth2Error.InvalidRedirectUrl(requestedRedirect).raise[F, RedirectUri]
          }
        }
      }
  }

  private def computeExpiry: F[Instant] =
    (OAuth2Config.approvalPeriod.get).reader[F].map { period =>
      clock.realTime(TimeUnit.MILLISECONDS).map(Instant)
      Instant.now().plusNanos(per.toNanos)
    }

  private def chooseScope(requestedScopes: Option[Scope], configuredScopes: Set[Scope]): F[NonEmptySet[Scope]] = {
    val sortedScopes = SortedSet.empty[Scope] ++ configuredScopes
    NonEmptySet
      .fromSet(sortedScopes)
      .fold(OAuth2Error.WrongScopeConfiguration.raise[F, NonEmptySet[Scope]])(_.pure[F])
      .flatMap { defaultScopes =>
        val requestScopes = requestedScopes
          .flatMap(extractScopes)
          .getOrElse(defaultScopes)

        if (requestScopes === defaultScopes) {
          requestScopes.pure[F]
        } else {
          OAuth2Error.WrongScopeConfiguration.raise[F, NonEmptySet[Scope]]
        }
      }
  }
}

object Auth {
  def make[F[_]](): F[Auth[F]] = ???
}

package oauth2.common
package client

import monocle.macros.Lenses

import scala.concurrent.duration.FiniteDuration

@Lenses
final case class Client(
    clientId: ClientId,
    clientSecret: Option[String],
    scopes: Set[Scope],
    authorizedGrandTypes: Set[GrantType],
    registeredRedirectUri: Set[RedirectUri],
    accessTokenValidity: Option[FiniteDuration],
    refreshTokenValidity: Option[FiniteDuration],
    autoApprove: Set[Scope]
)

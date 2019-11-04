package oauth2.common

import scala.concurrent.duration.FiniteDuration

final case class ClientItem(
  clientId: String,
  resourceIds: Set[String],
  clientSecret: Option[String],
  scopes: Set[Scope],
  authorizedGrandTypes: Set[GrantType],
  registeredRedirectUri: Set[RedirectUri],
  authorities: List[String],
  accessTokenValidity: Option[FiniteDuration],
  refreshTokenValidity: Option[FiniteDuration],
  autoApprove: Set[Scope]
)

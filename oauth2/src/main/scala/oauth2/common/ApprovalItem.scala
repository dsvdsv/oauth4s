package oauth2.common

import java.time.Instant

import monocle.macros.Lenses

@Lenses
final case class ApprovalItem(
    clientId: String,
    scope: Scope,
    status: ApprovalStatus,
    expireAt: Option[Instant],
    lastUpdateAt: Instant
)

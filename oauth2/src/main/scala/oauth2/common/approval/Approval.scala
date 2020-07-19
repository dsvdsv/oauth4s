package oauth2.common
package approval

import java.time.Instant

import monocle.macros.Lenses

@Lenses
final case class Approval(
    userId: UserId,
    clientId: ClientId,
    scope: Scope,
    status: ApprovalStatus,
    expireAt: Option[Instant],
    lastUpdateAt: Instant
)

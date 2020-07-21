package oauth2.common
package authorize

import approval.ApprovalStatus

final case class AuthorizeReq(
    clientId: String,
    responseType: ResponseType,
    approvalStatus: ApprovalStatus,
    scope: Option[Scope],
    redirectUri: Option[RedirectUri],
    state: Option[String]
)

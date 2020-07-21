package oauth2.common.approval

import cats.tagless.{autoFunctorK, finalAlg}

@autoFunctorK @finalAlg
trait ApprovalStore[F[_]] {
  def createApproval(item: Approval): F[Unit]
  def loadApprovals(clientId: String): F[List[Approval]]
}

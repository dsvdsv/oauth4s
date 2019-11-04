package oauth2.common

import cats.tagless.{autoFunctorK, finalAlg}

@autoFunctorK @finalAlg
trait ApprovalItemStore[F[_]] {
  def createApprovalItem(item: ApprovalItem): F[Unit]
  def loadApprovalItems(clientId: String): F[List[ApprovalItem]]
}

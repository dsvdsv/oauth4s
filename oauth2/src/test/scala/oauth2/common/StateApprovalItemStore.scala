package oauth2.common

import cats.mtl.MonadState
import cats.{Monad, Monoid}
import monocle.macros.Lenses
import oauth2.common.approval.{Approval, ApprovalStore}

object StateApprovalItemStore {
  @Lenses
  final case class InnerState(approvalItems: List[Approval])

  object InnerState {
    val empty = InnerState(List.empty)

    implicit val InnerStateMonoid: Monoid[InnerState] =
      new Monoid[InnerState] {
        def empty: InnerState =
          InnerState.empty
        def combine(a: InnerState, b: InnerState) =
          InnerState(
            approvalItems = a.approvalItems ++ b.approvalItems
          )
      }
  }

  def apply[F[_]: Monad](implicit FMS: MonadState[F, InnerState]): ApprovalStore[F] =
    new ApprovalStore[F] {
      def createApproval(item: Approval): F[Unit] =
        FMS.modify { state =>
          state.copy(approvalItems = state.approvalItems :+ item)
        }

      def loadApprovals(clientId: String): F[List[Approval]] =
        FMS.inspect { _.approvalItems.filter(_.clientId.contains(clientId)) }
    }
}

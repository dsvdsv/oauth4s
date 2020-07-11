package oauth2.common

import cats.mtl.MonadState
import cats.{Monad, Monoid}
import monocle.macros.Lenses

object StateApprovalItemStore {
  @Lenses
  final case class InnerState(approvalItems: List[ApprovalItem])

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

  def apply[F[_]: Monad](implicit FMS: MonadState[F, InnerState]): ApprovalItemStore[F] =
    new ApprovalItemStore[F] {
      def createApprovalItem(item: ApprovalItem): F[Unit] =
        FMS.modify { state =>
          state.copy(approvalItems = state.approvalItems :+ item)
        }

      def loadApprovalItems(clientId: String): F[List[ApprovalItem]] =
        FMS.inspect { _.approvalItems.filter(_.clientId.contains(clientId)) }
    }
}

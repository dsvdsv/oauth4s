package oauth2.common

import cats.mtl.MonadState
import cats.{Monad, Monoid}
import monocle.macros.Lenses
import oauth2.common.client.{Client, ClientStore}

object StateClientItemStore {
  @Lenses
  final case class InnerState(clientItems: List[Client])

  object InnerState {
    val empty = InnerState(List.empty)

    implicit val InnerStateMonoid: Monoid[InnerState] =
      new Monoid[InnerState] {
        def empty: InnerState =
          InnerState.empty
        def combine(a: InnerState, b: InnerState) =
          InnerState(
            clientItems = a.clientItems ++ b.clientItems
          )
      }
  }

  def apply[F[_]: Monad](implicit FMS: MonadState[F, InnerState]): ClientStore[F] =
    new ClientStore[F] {
      def loadClient(clientId: String): F[Option[Client]] =
        FMS.inspect { _.clientItems.find(_.clientId.contains(clientId)) }
    }
}

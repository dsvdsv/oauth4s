package oauth2.common
package token

import java.time.Instant

import cats.data.NonEmptyList
import monocle.macros.Lenses

sealed trait Token extends Product with Serializable { self =>
  import Token._

  def tokenValue: String
  def issuedAt: Instant

  def fold[A](fac: AccessToken => A, frt: RefreshToken => A): A = self match {
    case at: AccessToken  => fac(at)
    case rt: RefreshToken => frt(rt)
  }
}

object Token {
  @Lenses
  final case class AccessToken(
      tokenValue: String,
      issuedAt: Instant,
      expiredAt: Instant,
      scopes: NonEmptyList[Scope],
      parameters: Map[String, String],
      refreshTokenValue: Option[String]
  ) extends Token

  @Lenses
  final case class RefreshToken(
      tokenValue: String,
      issuedAt: Instant,
      expiredAt: Option[Instant]
  ) extends Token

}

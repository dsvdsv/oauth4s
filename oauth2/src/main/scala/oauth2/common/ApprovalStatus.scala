package oauth2.common

import cats.Eq
import cats.instances.string._
import cats.syntax.eq._
import monocle.Prism

sealed abstract class ApprovalStatus private (val repl: String) extends Serializable with Product

object ApprovalStatus {
  case object Approved extends ApprovalStatus("APPROVED")
  case object Denied   extends ApprovalStatus("DENIED")

  val all = Array(Approved, Denied)
  val fromCode: Prism[String, ApprovalStatus] =
    Prism[String, ApprovalStatus](code => all.find(_.repl === code))(_.repl)

  def unsafeFromCode(code: String): ApprovalStatus =
    fromCode
      .getOption(code)
      .getOrElse(
        throw new NoSuchElementException(s"Unknown approval status: $code")
      )

  implicit val approvalStatusEq: Eq[ApprovalStatus] =
    Eq.by(_.repl)
}

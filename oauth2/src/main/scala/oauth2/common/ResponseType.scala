package oauth2.common

import cats.instances.string._
import cats.syntax.eq._
import monocle.Prism

sealed abstract class ResponseType private (val repl: String) extends Serializable with Product

object ResponseType {

  case object Code  extends ResponseType("code")
  case object Token extends ResponseType("token")

  val all = Array(Code, Token)
  val fromCode: Prism[String, ResponseType] =
    Prism[String, ResponseType](code => all.find(_.repl === code))(_.repl)

  def unsafeFromCode(code: String): ResponseType =
    fromCode
      .getOption(code)
      .getOrElse(
        throw new NoSuchElementException(s"Unknown response_type: $code")
      )
}

package oauth2.common

import cats.effect._
import cats.implicits._

object App extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    args.headOption match {
      case Some(name) =>
        IO(println(s"Hello, ${name}.")).as(ExitCode.Success)
      case None =>
        IO(System.err.println("Usage: MyApp name")).as(ExitCode(2))
    }
}

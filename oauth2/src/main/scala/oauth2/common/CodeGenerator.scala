package oauth2.common

import java.security.SecureRandom

import cats.effect.Sync
import cats.tagless._

@autoFunctorK @finalAlg
trait CodeGenerator[F[_]] {
  def generate: F[String]
}

object CodeGenerator {
  private val defaultChars =
    "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray

  def default[F[_]: Sync, G[_]: Sync]: F[CodeGenerator[G]] =
    CodeGenerator[F, G](6)

  def apply[F[_], G[_]](length: Int)(implicit F: Sync[F], G: Sync[G]): F[CodeGenerator[G]] =
    F.delay {
      val random = new SecureRandom
      new CodeGenerator[G] {
        def generate: G[String] =
          G.delay {
            val verifierBytes = new Array[Byte](length)
            random.nextBytes(verifierBytes)
            getAuthorizationCodeString(verifierBytes)
          }

        def getAuthorizationCodeString(verifierBytes: Array[Byte]): String = {
          val chars = new Array[Char](verifierBytes.length)
          for (i <- chars.indices) {
            chars(i) = defaultChars((verifierBytes(i) & 0xFF) % chars.length)
          }
          new String(chars)
        }
      }
    }
}

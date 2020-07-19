package oauth2.common

import monocle.macros.Lenses

import scala.concurrent.duration.FiniteDuration

@Lenses
final case class OAuth2Config(
    approvalPeriod: FiniteDuration
)

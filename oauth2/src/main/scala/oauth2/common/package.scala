package oauth2

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.Url

package object common {

  type ClientId = String Refined NonEmpty
  type UserId = String Refined NonEmpty

  type Scope = String Refined NonEmpty
  type RedirectUri = String Refined Url
}

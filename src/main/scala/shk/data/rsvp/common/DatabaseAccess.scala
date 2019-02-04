package shk.data.rsvp.common

import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

object DatabaseAccess {

  private val configPath = Config.currentEnv match {
    case Constants.PRODUCTION ⇒ Constants.PRODUCTION_DB
    case _                    ⇒ Constants.DEVELOPMENT_DB
  }
  val db = Database.forConfig(configPath)

}

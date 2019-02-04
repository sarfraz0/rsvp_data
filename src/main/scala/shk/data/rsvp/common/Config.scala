package shk.data.rsvp.common

import com.typesafe.config.ConfigFactory

object Config {

  val conf = ConfigFactory.load()

  val currentEnv = conf.getString(Constants.ENV)
}

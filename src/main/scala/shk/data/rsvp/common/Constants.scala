package shk.data.rsvp.common

object Constants {

  // Configuration keys
  val ENV = "env"
  val DB = "db"
  val PRODUCTION = "production"
  val DEVELOPMENT = "development"

  // Configuration paths
  val PRODUCTION_DB = f"${DB}.${PRODUCTION}"
  val DEVELOPMENT_DB = f"${DB}.${DEVELOPMENT}"

}

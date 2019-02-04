package shk.data.rsvp.business

// Entities
case class Guest(
  id: Option[Long],
  name: String,
  lastname: String,
  country: String,
  inviteSent: Option[Boolean],
  responseDate: Option[String],
  eventPlace: Option[String]
)

// Error
sealed abstract class CustomError
case object EntityFetchError extends CustomError
case object EntityCreateError extends CustomError
case object EntityUpdateError extends CustomError
case object IOProcSuccess

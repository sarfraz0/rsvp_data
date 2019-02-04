package shk.data.rsvp.services

// import scala.concurrent.{Await, Future}
import scala.concurrent.Future
// import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import org.slf4j.LoggerFactory

import shk.data.rsvp.business.{ Guest }
import shk.data.rsvp.models._
import shk.utils.{ Dates, Helpers }
import com.softwaremill.quicklens._

trait RsvpServComp {
  def rsvpService: RsvpServ

  trait RsvpServ {

    // business
    def getGuest(name: String, lastname: String, country: String, responseDate: String): Future[Option[Guest]]
    def postGuest(gust: Guest): Future[Option[Guest]]
  }
}

trait RsvpServCompImpl extends RsvpServComp {
  this: RsvpRepoComp ⇒
  def rsvpService = new RsvpServImpl

  class RsvpServImpl extends RsvpServ {

    val logger = LoggerFactory.getLogger(this.getClass)

    def getGuest(name: String, lastname: String, country: String, responseDate: String) = rsvpRepository.getGuest(name, lastname, country, responseDate)
    def postGuest(gust: Guest) = {
      val nowDate = Dates.now
      val sanitized = gust.modify(_.name).using(Helpers.sanitizeStr _)
        .modify(_.lastname).using(Helpers.sanitizeStr _)
        .modify(_.responseDate).using(x ⇒ Some(nowDate))
     logger.info("Inserting entity")
     rsvpRepository.postGuest(sanitized).flatMap { insertedCount: Int ⇒
       // Has the insert call succeeded?
       if (insertedCount == 1) {
         // Yep, getting the entity with it's id
         logger.info("Successfull insert, requesting new entity id")
         getGuest(sanitized.name, sanitized.lastname, sanitized.country, nowDate)
       } else {
         // Nope
         logger.warn("Insert failed, check the parameter given")
         Future(None)
       }
     }
    };
//     def postGuest(gust: Guest) = {
//       // Does the entity already exists?
//       logger.info("Inserting new entity")
//       getGuest(gust.name, gust.lastname, gust.country).flatMap { interGust: Option[Guest] ⇒
//         logger.info("Checking if entity already in DB")
//         if (interGust.isDefined) {
//           // it exists so returning it as is
//           logger.info("Requested insert, returning it")
//           Future(interGust)
//         } else {
//           // it doesn't so we'll try inserting it
//           logger.info("Entity does not exist, proceeding")
//           rsvpRepository.postGuest(gust).flatMap { insertedCount: Int ⇒
//             // Has the insert call succeeded?
//             if (insertedCount == 1) {
//               // Yep, getting the entity with it's id
//               logger.info("Successfull insert, requesting new entity id")
//               getGuest(gust.name, gust.lastname, gust.country)
//             } else {
//               // Nope
//               logger.warn("Insert failed, check the parameter given")
//               Future(None)
//             }
//           }
//         }
//       }
//     }

  }
}

object RsvpServiceImpl
    extends RsvpServCompImpl
    with RsvpRepoCompImpl

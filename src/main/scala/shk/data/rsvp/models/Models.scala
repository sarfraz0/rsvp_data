package shk.data.rsvp.models

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import org.slf4j.LoggerFactory;

import shk.data.rsvp.business.{ Guest }
import shk.data.rsvp.common.DatabaseAccess

trait RsvpRepoComp {
  def rsvpRepository: RsvpRepo

  trait RsvpRepo {
    def getGuest(name: String, lastname: String, country: String, responseDate: String): Future[Option[Guest]]
    def postGuest(gust: Guest): Future[Int]
  }
}

trait RsvpRepoCompImpl extends RsvpRepoComp {
  def rsvpRepository = new RsvpRepoImpl

  class RsvpRepoImpl extends RsvpRepo {

    val logger = LoggerFactory.getLogger(this.getClass)

    /*************************************************************************/

    class GuestTable(tag: Tag) extends Table[Guest](tag, "guests") {
      def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
      def name = column[String]("name_")
      def lastname = column[String]("lastname")
      def country = column[String]("country")
      def inviteSent = column[Boolean]("invite_sent")
      def responseDate = column[String]("response_date")
      def eventPlace = column[String]("event_place")

      def * = (id.?, name, lastname, country, inviteSent.?, responseDate.?, eventPlace.?) <> (Guest.tupled, Guest.unapply)
    }

    val guests = TableQuery[GuestTable]

    /*************************************************************************/

    def getGuest(name: String, lastname: String, country: String, responseDate: String) = {
      DatabaseAccess.db.run(
        guests.filter(_.name === name)
          .filter(_.lastname === lastname)
          .filter(_.country === country)
          .filter(_.responseDate === responseDate)
          .result.headOption
      )
    }

    def postGuest(gust: Guest) = {
      DatabaseAccess.db.run((guests += gust))
    }

  }
}

package shk.data.rsvp

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import org.slf4j.LoggerFactory;

import shk.data.rsvp.business._
import shk.data.rsvp.services.RsvpServiceImpl

object Main {

  val logger = LoggerFactory.getLogger(this.getClass);

  def main(args: Array[String]) {
    val testGuest = Guest(None, "Patrick", "Sodom", "France", None, None, None)
    logger.info(Await.result(RsvpServiceImpl.rsvpService.postGuest(testGuest), Duration.Inf).toString)
  }
}

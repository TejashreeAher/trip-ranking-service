package controllers

import javax.inject._

import models.Trip
import org.slf4j.LoggerFactory
import play.api.mvc._
import services.TripRankingService

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class TripRankingController @Inject()(service : TripRankingService) extends Controller {
  val LOGGER = LoggerFactory.getLogger(this.getClass.getName)

  def index = Action {
    val rankedTrips = service.rankTrips(null)
    Ok(rankedTrips.toString())
  }

  def getRankedTrips() = Action { implicit request =>
    try {
      val inputData = request.body.asJson.get
      val inputTrips = inputData.as[Seq[Trip]]
      val rankedTrips = service.rankTrips(inputTrips)
      LOGGER.info("Done ranking trips")
      Ok(rankedTrips.toString())
    } catch {
      case e: Exception=>
        LOGGER.error("Failed to rank trips")
        InternalServerError(
          "failed"
      )
    }
  }

}

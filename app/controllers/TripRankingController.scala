package controllers

import javax.inject._

import Model.Trip
import play.api.mvc._
import services.TripRankingService

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class TripRankingController @Inject()(cc: ControllerComponents,
                                      service : TripRankingService) extends AbstractController(cc) {

  def index = Action {
    val rankedTrips = service.rankTrips(null)
    Ok(rankedTrips.toString())
  }

  def getRankedTrips() = Action { implicit request =>
    try {
      val inputData = request.body.asJson.get
      val inputTrips = inputData.as[Seq[Trip]]
      val rankedTrips = service.rankTrips(inputTrips)
      Ok(rankedTrips.toString())
    } catch {
      case e: Exception=>
        InternalServerError(
          "failed"
      )
    }
  }

}

package Model

import play.api.libs.json.{JsResult, JsSuccess, JsValue, Reads}

case class Trip(tripId: Int,
                departureTime : Long,
                departureDate: Long,
                tripHours : Int,
                numConnections : Int,
                distance : Int,
                price: Double
               )

object Trip {
  implicit val storyReads = new Reads[Trip] {
    def reads(js: JsValue): JsResult[Trip] = {
      val tripId     = (js \ "trip_id").as[Int]
      val departureTime = (js \ "departure_time").as[Long]
      val departureDate = (js \ "departure_date").as[Long]
      val tripHours = (js \ "trip_hours").as[Int]
      val numConnections = (js \ "num_conenctions").as[Int]
      val distance = (js \ "distance").as[Int]
      val price = (js \ "price").as[Double]
      JsSuccess(
        Trip(tripId, departureTime, departureDate, tripHours, numConnections, distance, price)
      )
    }
  }
}

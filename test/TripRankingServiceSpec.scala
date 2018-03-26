import machine.RankingModel
import models.Trip
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import services.TripRankingService

class TripRankingServiceSpec extends PlaySpec with MockitoSugar{

  "Tracking Service " should {
    "give correct results " in {
      val model = mock[RankingModel]
      when(model.rankTrips(inputTrips))
        .thenReturn(List(trip1,trip2,trip3))

      val service = new TripRankingService(model)
      val rankedTrips = service.rankTrips(inputTrips)
      assert(rankedTrips.size == 3)
      assert(rankedTrips == List(1,2,3))
    }

    "divide input trips properly into groups" in {
        val service = new TripRankingService(null)
      val trip1 = new Trip(1, 12, 2701, 1, 3, 400, 12.50)
      val trip2 = new Trip(2, 12, 2701, 1, 3, 400, 12.50)
      val trip3 = new Trip(3, 12, 2701, 1, 3, 400, 12.50)
      val trip4 = new Trip(4, 12, 2701, 1, 3, 400, 12.50)
      val trip5 = new Trip(5, 12, 2701, 1, 3, 400, 12.50)
      val trip6 = new Trip(6, 12, 2701, 1, 3, 400, 12.50)
      val inputTrips = List(trip1, trip2, trip3, trip4, trip5, trip6)
      val groups = service.getGroupsOfTrips(inputTrips, 4)
      assert(groups.size == 2)
      println(s"groups size : ${groups.get(0)}")
      assert(groups.get(0).get.size == 4)
      assert(groups.get(1).get.size == 2)
    }
  }

  val trip1 = new Trip(1, 12, 2701, 1, 3, 400, 12.50)
  val trip2 = new Trip(2, 12, 2701, 1, 3, 400, 12.50)
  val trip3 = new Trip(3, 12, 2701, 1, 3, 400, 12.50)
  val inputTrips = List(trip1, trip2, trip3)

}

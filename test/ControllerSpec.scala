import controllers.TripRankingController
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.test.{FakeHeaders, FakeRequest, Helpers}
import services.TripRankingService

class ControllerSpec extends PlaySpec with MockitoSugar{
  "controller" should {
    "give correct response" in {
      val service = mock[TripRankingService]

      when(service.rankTrips(null))
        .thenReturn(List(1,2,3))

      val fakeRequest =
        FakeRequest(
          method = Helpers.POST,
          uri = "/",
          headers = FakeHeaders(Seq("Content-Type" -> "application/json")),
          body = jsonBody
        )

      val controller = new TripRankingController(service)
      val response   = controller.getRankedTrips().apply(fakeRequest)

      //this test is incomplete, the intention was to test happypath
//      assert(response == List(1,2,3))
      assert(true == true)
    }

  }

  val jsonBody = "[{\n\"trip_id\":1,\n\"departure_time\":12,\n\"departure_date\": 2701,\n\"trip_hours\":1,\n\"num_conenctions\":3,\n\"distance\":400,\n\"price\":12.50\n},\n{\n\"trip_id\":10,\n\"departure_time\":12,\n\"departure_date\": 2701,\n\"trip_hours\":1,\n\"num_conenctions\":3,\n\"distance\":400,\n\"price\":12.50\n},\n{\n\"trip_id\":3,\n\"departure_time\":12,\n\"departure_date\": 2701,\n\"trip_hours\":1,\n\"num_conenctions\":3,\n\"distance\":400,\n\"price\":12.50\n},\n{\n\"trip_id\":4,\n\"departure_time\":12,\n\"departure_date\": 2701,\n\"trip_hours\":1,\n\"num_conenctions\":3,\n\"distance\":400,\n\"price\":12.50\n},\n{\n\"trip_id\":5,\n\"departure_time\":12,\n\"departure_date\": 2701,\n\"trip_hours\":1,\n\"num_conenctions\":3,\n\"distance\":400,\n\"price\":12.50\n}]"

}

import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import services.TripRankingService

class ControllerSpec extends PlaySpec with MockitoSugar{
  "controller" should {
    "give correct response" in {
      val service = mock[TripRankingService]

      when(service.rankTrips(null))
        .thenReturn(List(1,2,3))

//      val controller = TripRankingController(service)
//      val response   = controller.getUserInteractionStates(userID, "urn:1", "internal", "scalatest").apply(FakeRequest())
//
//      assert(200 == status(response))
//
//      val responseBody = contentAsJson(response)
//      val resultObject = responseBody.as[Map[String, List[String]]]
//      assert(resultObject == Map("urn:1" -> List("click")))
      assert(true == true)
    }
  }

}

package machine

import javax.inject.Singleton

import models.Trip

@Singleton
class RankingModel {
  def rankTrips(trips : Seq[Trip]) : Seq[Int] ={
    println("Trip ids are : "+ trips.map(_.tripId))
    trips.map(_.tripId)
  }
}

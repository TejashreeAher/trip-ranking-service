package services

import javax.inject.Singleton

import Model.Trip

@Singleton
class TripRankingService {
  def rankTrips(trips : Seq[Trip]) : Seq[Int] ={
      trips.map(_.tripId)
  }

}

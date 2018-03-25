package services

import javax.inject.{Inject, Singleton}

import machine.RankingModel
import models.Trip


@Singleton
class TripRankingService @Inject()(model : RankingModel) {
  val BATCH_SIZE = 5 //This value is the maximum size that the model can take

  def rankTrips(trips : Seq[Trip]) : Seq[Int] ={
    val groupMap = getGroupsOfTrips(trips, BATCH_SIZE)

    val rankedInGroups = groupMap.map{case (key, value) => {
      (key, model.rankTrips(value))
    }}

    //currently this just joins the result of all the individual groups
    mergeRankedgroups(rankedInGroups)
  }

  def min(i: Int, j: Int) = {
    i > j match {
      case true => j
      case false => i
    }
  }

  def mergeRankedgroups(rankedGrps : Map[Int, Seq[Int]]): Seq[Int] ={
      rankedGrps.flatMap{case(key, value) => value}.toSeq
  }

  def getGroupsOfTrips(trips : Seq[Trip], batchSize: Int) : Map[Int, Seq[Trip]]={
    val tripArr = trips
    var groupIndex = 0
    var counter = 0
    var groupMap: Map[Int, Seq[Trip]] = Map.empty

    //divide the trips into batches of size 5 (size that the model can take) and call model individually for them
    while(counter < tripArr.length){
      val startIndex = groupIndex*batchSize
      val endIndex  = min(startIndex + batchSize-1, trips.length-1) //because slice excludes last one
      val newArra = tripArr.slice(startIndex, endIndex+1)
      groupMap = groupMap + (groupIndex ->newArra)
      counter = endIndex+1
      groupIndex+=1
    }
    groupMap
  }
}

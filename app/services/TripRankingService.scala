package services

import javax.inject.{Inject, Singleton}

import machine.RankingModel
import models.Trip


@Singleton
class TripRankingService @Inject()(model : RankingModel) {
  val BATCH_SIZE = model.BATCH_SIZE //This value is the maximum size that the model can take

  def rankTrips(trips : Seq[Trip]) : Seq[Int] ={
    sortTrips(trips).map(_.tripId)
  }

//  This is essentially merge sort with the difference that the merge step has multiple batches and recursively calls sort
  //method as the merged array can have more than 5 trips
  def sortTrips(trips : Seq[Trip]): Seq[Trip] = {
    val rankedTrips = if(trips.size <= BATCH_SIZE){
      model.rankTrips(trips)
    }else{
      val groupMap = getGroupsOfTrips(trips, BATCH_SIZE)
      val rankedInGroups = groupMap.map{case (key, value) => {
        (key, model.rankTrips(value))
      }}

      mergeRankedgroups(rankedInGroups)
    }
    rankedTrips
  }

  def min(i: Int, j: Int) = {
    i > j match {
      case true => j
      case false => i
    }
  }

  ///take care of duplicate trips in the input
  def isIndexOfFirstTrip(group: (Int, Seq[Trip]), firstTrip: Trip, groupCurrIndex: List[Int]):Boolean = {
    group._2.contains(firstTrip) &&
      group._2.zipWithIndex.collect
      {case(element, index) => {
        element.tripId match {
          case(firstTrip.tripId) => Some(index)
          case _ => None
        }
      }
      }.flatten
        .contains(groupCurrIndex(group._1))
    //second condition is required if the group has the number multiple times, check if one of them matches
  }

  def mergeRankedgroups(rankedGrps : Map[Int, Seq[Trip]]): Seq[Trip] ={
    var finalMergedTrip: Seq[Trip] = Seq.empty
    val numGroups = rankedGrps.size
    var groupCurrIndex = List.fill(numGroups)(0) //this keeps track of the pointer in the batches
    while(groupCurrIndex != rankedGrps.map(_._2.size).toList){
        val tempSeq = rankedGrps.map{case(index, value) => {
          if(groupCurrIndex(index) < value.size) Some(value(groupCurrIndex(index))) //take the elements from each batch corresponding to their current pointer
          else None
          }
        }.toSeq.flatten
      //sort them
        val mergedRankedTrips = sortTrips(tempSeq)

      //this tells us which group the first trip belongs to after sorting and updates it's pointer accordingly
        val firstTripIndex = rankedGrps.filter(isIndexOfFirstTrip(_, mergedRankedTrips(0), groupCurrIndex)).head._1//this tells us which batch, the first element belongs to
        finalMergedTrip = finalMergedTrip :+ mergedRankedTrips(0)
        groupCurrIndex = groupCurrIndex.updated(firstTripIndex, groupCurrIndex(firstTripIndex)+1)
    }
    finalMergedTrip
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

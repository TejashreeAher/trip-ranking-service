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
    val rankedTrips = mergeRankedgroups(rankedInGroups)
    rankedTrips.map(_.tripId)
  }

  def min(i: Int, j: Int) = {
    i > j match {
      case true => j
      case false => i
    }
  }

  def mergeRankedgroups2(rankedGrps : Map[Int, Seq[Int]]): Seq[Int] ={
      rankedGrps.flatMap{case(key, value) => value}.toSeq
  }



  //second implementation of merge
  def mergeRankedgroups(rankedGrps : Map[Int, Seq[Trip]]): Seq[Trip] ={
    var finalMergedTrip: Seq[Trip] = Seq.empty
    val numGroups = rankedGrps.size
    var groupCurrIndex = List.fill(numGroups)(0)
    println(s"inititalise group current index to : ${groupCurrIndex}")
    while(groupCurrIndex != rankedGrps.map(_._2.size).toList){
        val tempSeq = rankedGrps.map{case(index, value) => {
          if(groupCurrIndex(index) < value.size) Some(value(groupCurrIndex(index)))
          else None
          }
        }.toSeq.flatten
        println(s"Sequence to be merged : ${tempSeq}")
        val mergedRankedTrips = model.rankTrips(tempSeq)
        println(s"Sequence to be merged is sorted to : ${mergedRankedTrips}")
        val firstTripIndex = rankedGrps.filter(_._2.contains(mergedRankedTrips(0))).head._1//this tells us which batch, teh first element belongs to
        println(s"First trip belongs to group : ${firstTripIndex}")
        finalMergedTrip = finalMergedTrip :+ mergedRankedTrips(0)
        println(s"final sorted list has now : ${mergedRankedTrips(0)}")
        groupCurrIndex = groupCurrIndex.updated(firstTripIndex, groupCurrIndex(firstTripIndex)+1)
        println(s"groupCurrIndex now is ${groupCurrIndex}")
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

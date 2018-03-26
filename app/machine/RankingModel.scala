package machine

import javax.inject.Singleton

import models.Trip
import org.slf4j.LoggerFactory

@Singleton
class RankingModel {
  val LOGGER = LoggerFactory.getLogger(this.getClass.getName)
  val BATCH_SIZE = 5
  def rankTrips(trips : Seq[Trip]) : Seq[Trip] ={
   trips match {
     case(x ) if x.isEmpty => Seq.empty
     case _ => {
       trips.size match {
         case(n) if n>BATCH_SIZE => throw new Exception(s"Cannot calculate for more than ${BATCH_SIZE} trips")
         case _ =>  {
           LOGGER.info(s"Calcutaing for ${trips.size} trips")
           trips.sortBy(_.tripId)
         }
       }
     }
   }
  }
}

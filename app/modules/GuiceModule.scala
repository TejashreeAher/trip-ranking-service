package modules

import com.google.inject.AbstractModule
import machine.RankingModel

/**
  * Define the standard bindings
  */
class GuiceModule extends AbstractModule {

  override def configure(): Unit = {
//    bind(classOf[TripRankingService]).asEagerSingleton()
    bind(classOf[RankingModel]).asEagerSingleton()
  }

}

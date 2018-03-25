package modules

import com.google.inject.AbstractModule
import services.TripRankingService

/**
  * Define the standard bindings
  */
class GuiceModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[TripRankingService]).asEagerSingleton()
  }

}

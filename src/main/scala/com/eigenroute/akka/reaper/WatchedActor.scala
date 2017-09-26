package com.eigenroute.akka.reaper

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.eigenroute.akka.reaper.Reaper.WatchMe

abstract class WatchedActor(reaper: ActorRef) extends Actor with ActorLogging {
  
  reaper ! WatchMe(self)
  
}

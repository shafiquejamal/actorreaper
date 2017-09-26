package com.github.shafiquejamal.akka.reaper

import akka.actor.{Actor, ActorLogging, ActorRef}
import Reaper.WatchMe

abstract class WatchedActor(reaper: ActorRef) extends Actor with ActorLogging {
  
  reaper ! WatchMe(self)
  
}

package com.eigenroute.akka.reaper

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill, Terminated}

object Reaper {
  
  case class WatchMe(ref: ActorRef)
  
  case class AddLastActorStanding(ref: ActorRef)
  
  
}

abstract class Reaper extends Actor with ActorLogging {
  
  import Reaper._
  
  private var watched = Vector[ActorRef]()
  private var lastActorsStanding = Vector[ActorRef]()
  
  def allSoulsReaped(): Unit
  
  final override def receive: Receive = {
    case WatchMe(ref) =>
      context.watch(ref)
      if (!(lastActorsStanding contains ref)) watched = watched :+ ref
    case Terminated(ref) =>
      watched = watched filterNot ref.==
      lastActorsStanding = lastActorsStanding filterNot ref.==
      if (watched.isEmpty) {
        lastActorsStanding.foreach(_ ! PoisonPill)
        if (lastActorsStanding.isEmpty) allSoulsReaped()
      }
    case AddLastActorStanding(ref) =>
      lastActorsStanding = lastActorsStanding :+ ref
      watched = watched filterNot ref.==
  }
  
}

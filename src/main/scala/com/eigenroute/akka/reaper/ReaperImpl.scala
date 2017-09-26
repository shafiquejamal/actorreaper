package com.eigenroute.akka.reaper

import akka.actor.Props

class ReaperImpl(closing: Seq[() => Unit]) extends Reaper {
  
  override def allSoulsReaped(): Unit = {
    closing.foreach(closer => closer())
    context.system.terminate()
  }
  
}

object ReaperImpl {
  
  def props(closing: Seq[() => Unit]) = Props(new ReaperImpl(closing))
  
  def props(closing: () => Unit) = Props(new ReaperImpl(Seq(closing)))
}


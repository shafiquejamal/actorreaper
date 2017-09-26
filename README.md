# Actor Reaper

This is an implementation of the reaper pattern as described here: [reaper](http://letitcrash.com/post/30165507578/shutdown-patterns-in-akka-2)

I've added a feature, which I describe below.

## Last Actor Standing

Suppose you create a bunch of actors to do some work (MyWorkers), one actor to save the results (MySaver) and one actor to print the results (MyPrinter). MySaver and MyPrinter should be terminated after all of the MyWorkers mailboxes are empty. To let the reaper know this, you send the message `LastActorStanding(actorRef)` to the reaper. The following code illustrates this:

```scala
val mySaver = system.actorOf(MySaver.props(reaper))
val myPrinter = system.actorOf(MyPrinter.props(reaper))

reaper ! AddLastActorStanding(mySaver)
reaper ! AddLastActorStanding(myPrinter)

val workToDo = Seq(task1, task2, ... taskN)

val myWorkers =
  system.actorOf(MyWorkder.props(geocoder, mySaver, myPrinter, reaper)
   .withRouter(SmallestMailboxPool(nrOfInstances = 20)), "my-worker")
   
workToDo.foreach ( workItem => myWorkers ! workItem)
myWorkers ! Broadcast(PoisonPill)
```

All actors that the reaper should watch must extend `WatchedActor`.

After the workers have finished their tasks, `mySaver` and `myPrinter` will be terminated. After all actors watched actors are terminated, the reaper will shutdown the actor system.

What if you need to close a file handle, or execute some other function when all of the watched actors have finished their work? Pass a collection of these functions as an argument to `ReaperImpl` when creating the reaper:

```scala
val closeFile = cSVwriter.close _
val reaper = system.actorOf(ReaperImpl props Seq(closeFile), "reaper") // or val reaper = system.actorOf(ReaperImpl props closeFile, "reaper") 
``` 



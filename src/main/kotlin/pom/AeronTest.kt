package pom

import io.aeron.Aeron
import io.aeron.CommonContext
import io.aeron.driver.MediaDriver
import io.aeron.driver.ThreadingMode
import io.aeron.logbuffer.FragmentHandler
import org.agrona.CloseHelper
import org.agrona.ExpandableDirectByteBuffer
import org.agrona.concurrent.*

fun main(args: Array<String>) {
  val enumArgs: List<Args> = args.mapNotNull { arg ->
    Args.values().find { enum -> enum.name.equals(arg, true) }
  }
  println("Args: $enumArgs")
  println("Args available: " + Args.values())
  AeronTest(enumArgs).start()
}

class AeronTest(val args: List<Args>) {
  val idleStrategy: IdleStrategy = SleepingMillisIdleStrategy(100)
  val barrier = ShutdownSignalBarrier()
  val mediaDriverCtx = MediaDriver.Context()
    .aeronDirectoryName(CommonContext.getAeronDirectoryName() + "-${System.currentTimeMillis()}")
    .dirDeleteOnStart(true)
    .dirDeleteOnShutdown(true)
    .threadingMode(ThreadingMode.SHARED)
  val mediaDriver = MediaDriver.launchEmbedded(mediaDriverCtx)
  val aeronCtx = Aeron.Context().aeronDirectoryName(mediaDriver.aeronDirectoryName())
  val aeron = Aeron.connect(aeronCtx)

  fun start() {
    args.forEach { arg ->
      when (arg) {
        Args.PUB      -> pub("aeron:udp?endpoint=127.0.0.1:33506")
        Args.SUB      -> sub("aeron:udp?endpoint=127.0.0.1:0")
        Args.SUB_ALL  -> sub("aeron:udp?endpoint=0.0.0.0:0")
      }
    }
  }

  fun pub(pubStr: String) {
    println("Starting pub")
    val pubAgent = object : Agent {
      val pub = aeron.addExclusivePublication(pubStr, streamId)
      init {
        println("Created pub $pub")
      }
      override fun doWork(): Int {
        if (!pub.isConnected)
          println("not connected: $pub")
        else
          pub.offer(ExpandableDirectByteBuffer(512))
        return 0
      }
      override fun roleName(): String {
        return "sendAgent"
      }
    }
    val pubAgentRunner = AgentRunner(idleStrategy, {obj: Throwable -> obj.printStackTrace() }, null, pubAgent)
    AgentRunner.startOnThread(pubAgentRunner)
    waitNClose(listOf(pubAgentRunner))
  }

  fun sub(subStr: String) {
    println("Starting sub")
    val subAgent = object : Agent {
      val sub = aeron.addSubscription(subStr, streamId)
      val handler = FragmentHandler { buffer, offset, length, header -> println("Received") }
      init {
        println("Waiting, sub: $sub")
      }
      override fun doWork(): Int {
        return sub.poll(handler, 1)
      }
      override fun roleName(): String {
        return "SubAgent"
      }
    }
    val subAgentRunner = AgentRunner(idleStrategy, { obj: Throwable -> obj.printStackTrace() }, null, subAgent)
    AgentRunner.startOnThread(subAgentRunner)


    //Await shutdown signal
    waitNClose(listOf(subAgentRunner))
  }

  private fun waitNClose(runners: List<AgentRunner> = listOf()) {
    barrier.await()
    CloseHelper.quietCloseAll(runners)
    CloseHelper.quietClose(aeron)
    CloseHelper.quietClose(mediaDriver)
  }

  companion object {
    val streamId = 0
  }
}
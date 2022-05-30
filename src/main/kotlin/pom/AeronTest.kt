package pom

import generated.MessageHeaderDecoder
import generated.MessageHeaderEncoder
import generated.RpcConnectRequestDecoder
import generated.RpcConnectRequestEncoder
import io.aeron.Aeron
import io.aeron.CommonContext
import io.aeron.Publication
import io.aeron.driver.MediaDriver
import io.aeron.driver.SendChannelEndpointSupplier
import io.aeron.driver.ThreadingMode
import io.aeron.logbuffer.FragmentHandler
import org.agrona.CloseHelper
import org.agrona.ExpandableDirectByteBuffer
import org.agrona.MutableDirectBuffer
import org.agrona.concurrent.*
import java.util.Optional

fun main(args: Array<String>) {
  val enumArgs: List<Args> = args.mapNotNull { arg ->
    Args.values().find { enum -> enum.name.equals(arg, true) }
  }
  println("Args: $enumArgs")
  println("Args available: " + Args.values())
  AeronTest(enumArgs).start()
}

class AeronTest(val args: List<Args>) {
  val idleStrategy: IdleStrategy = SleepingMillisIdleStrategy(800)
  val barrier = ShutdownSignalBarrier()


  private fun sendChannelEndpointSupplier(): SendChannelEndpointSupplier {
//    UdpChannel udpChannel, AtomicCounter statusIndicator, MediaDriver.Context context)
    return sendChannelEndpointSupplier()
  }

  val mediaDriverCtx = MediaDriver.Context()
    .aeronDirectoryName(CommonContext.getAeronDirectoryName() + "-${System.currentTimeMillis()}")
    .dirDeleteOnStart(true)
    .dirDeleteOnShutdown(true)
    .threadingMode(ThreadingMode.SHARED)

  val originalSender = mediaDriverCtx.sendChannelEndpointSupplier()

  val mediaDriver = MediaDriver.launchEmbedded(mediaDriverCtx)
  val aeronCtx = Aeron.Context().aeronDirectoryName(mediaDriver.aeronDirectoryName())
  val aeron = Aeron.connect(aeronCtx)
  val headerDec = MessageHeaderDecoder()
  val headerEnc = MessageHeaderEncoder()
  val connectReqDec = RpcConnectRequestDecoder()
  val connectReqEnc = RpcConnectRequestEncoder()

  fun start() {
    args.forEach { arg ->
      when (arg) {
        Args.PUB              -> waitNClose(listOf(pub("aeron:udp?endpoint=192.168.1.66:54349")))
        Args.SUB_23456        -> waitNClose(listOf(sub("aeron:udp?endpoint=127.0.0.1:23456")))
        Args.SUB              -> waitNClose(listOf(sub("aeron:udp?endpoint=127.0.0.1:0")))
        Args.SUB_ALL          -> waitNClose(listOf(sub("aeron:udp?endpoint=0.0.0.0:0")))
        Args.PING_PONG_SERVER -> {
          val subRunner = sub("aeron:udp?endpoint=0.0.0.0:$remotePort") { buffer, offset, _, _ ->
            println("PING_PONG_SERVER RECEIVED")
            headerDec.wrap(buffer, offset)
            // assuming it's the correct message
            connectReqDec.wrap(buffer, offset + headerDec.encodedLength(), headerDec.blockLength(), headerDec.version())
            pub("aeron:udp?endpoint=${connectReqDec.returnConnectUri()}")
          }
          waitNClose(listOf(subRunner))
        }
        Args.PING_PONG_CLIENT -> {
          val subRunner = sub("aeron:udp?endpoint=0.0.0.0:$remotePort")
          val pubRunner = pub("aeron:udp?endpoint=192.168.1.66:$remotePort") {
            println("Trying to send a payload to 192.168.1.66:$remotePort")
            val buffer = ExpandableDirectByteBuffer(512)
            connectReqEnc.wrapAndApplyHeader(buffer, 0, headerEnc).returnConnectStream(streamId).returnConnectUri("192.168.1.70:$remotePort")
            buffer
          }
          waitNClose(listOf(subRunner, pubRunner))
        }
      }
    }
  }

  var openedAgent: Optional<Agent> = Optional.empty()
  fun pub(pubStr: String, payload: () -> MutableDirectBuffer = { ExpandableDirectByteBuffer(512) }): AgentRunner {
    println("Starting pub")

    if (openedAgent.isEmpty) {
      val pubAgent = object : Agent {
        val pub = aeron.addExclusivePublication(pubStr, streamId)

        init {
          println("Created pub $pub")
        }

        override fun doWork(): Int {
          if (!pub.isConnected)
            println("not connected: $pub")
          else
            pub.offer(payload.invoke())
          return 0
        }

        override fun roleName(): String {
          return "sendAgent"
        }
      }
      openedAgent = Optional.of(pubAgent)
    }
    val pubAgentRunner = AgentRunner(idleStrategy, {obj: Throwable -> obj.printStackTrace() }, null, openedAgent.get())
    AgentRunner.startOnThread(pubAgentRunner)
    return pubAgentRunner
  }

  fun sub(subStr: String, handler: FragmentHandler = FragmentHandler { buffer, offset, length, header -> println("Received") }): AgentRunner {
    println("Starting sub")
    val subAgent = object : Agent {
      val sub = aeron.addSubscription(subStr, streamId)
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
    return subAgentRunner
  }

  private fun waitNClose(runners: List<AgentRunner> = listOf()) {
    barrier.await()
    CloseHelper.quietCloseAll(runners)
    CloseHelper.quietClose(aeron)
    CloseHelper.quietClose(mediaDriver)
  }

  companion object {
    val streamId = 0
    val remotePort = 33506
  }
}
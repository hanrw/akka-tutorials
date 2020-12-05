package org.hanrw.akka.rengine

import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.apache.kafka.common.serialization.StringDeserializer
import org.hanrw.akka.rengine.actor.RengineManagerActor
import org.hanrw.akka.rengine.actor.utils.ActorSystemFactory

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object RengineApplication extends App {
  // actor syste and meterializer for streams
  implicit val system = ActorSystemFactory.getDefault()
  implicit val materializer = ActorMaterializer()

  // consumer setting
  val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
    .withGroupId("rule-engine-group")

  // init rule manager
  val ruleManager = system.actorOf(RengineManagerActor(), RengineManagerActor.NAME)

  // consumer
  val consumer = Consumer
    .committableSource(consumerSettings, Subscriptions.topics("rule-engine-test"))
    .map(msg => {
      // 消费kafka数据,发送给ruleManager
      ruleManager ! msg
      println(s"Got message - ${msg.record.value()}")
      msg
    })
    .runWith(Sink.ignore)

  // handle shutdown
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  consumer onComplete {
    case Success(_) =>
      println("Successfully terminated consumer")
      system.terminate()
    case Failure(err) =>
      println(err)
      system.terminate()
  }
}
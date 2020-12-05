package org.hanrw.akka.rengine.actor

import akka.actor.{ActorRef, Props, UntypedAbstractActor}
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.cep.scala.{CEP, PatternStream}
import org.apache.flink.runtime.akka.AkkaUtils
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}
import org.apache.flink.streaming.connectors.akka.AkkaSource
import org.apache.flink.streaming.connectors.akka.utils.SubscribeReceiver
import org.hanrw.akka.rengine.actor.utils.ActorSystemFactory

import scala.collection.Map

class RengineProcessorActor() extends UntypedAbstractActor {
  val env = StreamExecutionEnvironment.getExecutionEnvironment
  /**
   * flink cep所需事件源,每一个规则一个事件源
   */
  // init akka source
  val source = new AkkaSource("receiverActorName", AkkaUtils.getAkkaURL(context.system, self), ActorSystemFactory.config())
  var receiver: ActorRef = _

  /**
   * Init rule actor from rule
   */
  override def preStart(): Unit = {
    val data: DataStream[Object] = env.addSource(source)
    val pattern = Pattern.begin[Object]("simple") //指定名称
      .where(r => r != null)
    // 将创建好的Pattern 应用在流上面
    val patternStream: PatternStream[Object] = CEP.pattern(data, pattern)
    // 获取触发事件
    patternStream.select((pattern: Map[String, Iterable[Object]]) => {
      val next = pattern.getOrElse("simple", null).iterator.next()
      println("trigger alert")
      //TODO SEND ALERT TO KAFKA
    })
    env.executeAsync(s"job=simple")
  }

  override def onReceive(message: Any): Unit = {
    message match {
      case msg: SubscribeReceiver =>
        receiver = msg.getReceiverActor
      case msg: String =>
        receiver ! msg
    }
  }
}

object RengineProcessorActor {
  def props(): Props = Props(new RengineProcessorActor())
}

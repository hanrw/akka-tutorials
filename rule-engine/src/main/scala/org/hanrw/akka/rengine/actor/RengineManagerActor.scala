package org.hanrw.akka.rengine.actor

import akka.actor.{ActorLogging, Props, UntypedAbstractActor}
import akka.kafka.ConsumerMessage.CommittableMessage

/**
 * @author hanrw
 * @since 2020/12/5
 */
class RengineManagerActor extends UntypedAbstractActor with ActorLogging {
  /**
   * Init Rule actors when rule manager start
   */
  override def preStart(): Unit = {
    log.info("rule manager started...")
    context.actorOf(RengineProcessorActor.props())
  }

  override def onReceive(message: Any): Unit = {
    message match {
      case msg: CommittableMessage[String, String] => {
        context.children.foreach(child => child ! msg.record.value())
        println(s"get new event msg: $message")
      }
    }
  }
}

object RengineManagerActor {
  final val NAME = "rule-manager-actor"

  def apply(): Props = Props(new RengineManagerActor())
}

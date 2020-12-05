package org.hanrw.akka.rengine.actor.utils

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.flink.runtime.akka.AkkaUtils
import scala.collection.mutable

object ActorSystemFactory {

  private val systems: mutable.Map[String, ActorSystem] = mutable.Map[String, ActorSystem]()

  def getDefault(): ActorSystem = {
    get("default")
  }

  def get(name: String): ActorSystem = {
    systems.getOrElseUpdate(name, createActorSystem(name))
  }

  /**
   * Creates an actor system with default configurations for Receiver actor.
   *
   * @return Actor System instance with default configurations
   */
  private def createActorSystem(name: String): ActorSystem = {
    ActorSystem.create(name, config())
  }

  def config(): Config = {
    ConfigFactory.parseResources("application.conf").withFallback(AkkaUtils.getDefaultAkkaConfig)
  }

  def get(name: String, host: String = "127.0.0.1", port: Int = 0): ActorSystem = {

    val configs = ConfigFactory.parseString(
      s"""akka.remote.netty.tcp.hostname = $host
          akka.remote.netty.tcp.port = $port""".stripMargin).withFallback(config())
    systems.getOrElseUpdate(name, ActorSystem.create(name, configs))
  }

  def close(name: String): Unit = {
    systems.remove(name).foreach(_.terminate())
  }
}
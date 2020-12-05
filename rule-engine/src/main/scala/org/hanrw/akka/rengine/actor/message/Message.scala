package org.hanrw.akka.rengine.actor.message

trait Message {}

case class SimpleMessage() extends Message

/**
 * 启动消息
 */
case class Start() extends Message

/**
 * 停止消息
 */
case class Stop() extends Message

/**
 * 更新消息
 */
case class Update() extends Message

/**
 * 删除消息
 */
case class Delete() extends Message

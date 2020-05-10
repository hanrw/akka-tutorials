package org.hanrw.akka.seckill.actor.message

import org.hanrw.akka.seckill.actor.sku.{IPhone12, Sku}

trait Message

/**
 * 抢购成功消息
 * @param userId
 * @param sku
 */
case class SecKillSuccess(userId:Int, sku:Sku) extends Message

/**
 * 抢购失败消息
 */
case object SecKillFail extends Message
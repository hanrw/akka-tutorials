package org.hanrw.akka.seckill.actor.request

/**
 * @author hanrw
 **/
trait Request

/**
 * @author hanrw
 * @description 秒杀请求
 **/

case class SecKillRequest(userId: Int, skuId: String) extends Request
case class SecKillSkuNotAvailableRequest(userId: Int, skuId: String) extends Request

case class SecKillOutOfStockRequest(skuId: String) extends Request
case class SecKillCheckStockRequest(skuId: String) extends Request


package org.hanrw.akka.seckill.actor.sku

import akka.actor.{Actor, ActorLogging}
import org.hanrw.akka.seckill.actor.message.{SecKillFail, SecKillSuccess}
import org.hanrw.akka.seckill.actor.request.SecKillRequest

/**
 * @author hanrw
 *         sku的行为:
 *  1.还未被秒杀到-init
 *  2.正在秒杀中-secKilling
 *  3.已经秒杀到-seckilled
 * @date 2020/5/9 7:51 PM
 */
class SkuActor(skuId: String) extends Actor with ActorLogging {

  /**
   * @description
   * 初始化库存数量actor,每一个sku都对应一个库存数量
   * @date 10:59 AM 2020/5/10
   * @return void
   **/
  override def preStart(): Unit = {
    log.info("init sku stock")
  }


  override def receive: Receive = initReceive

  /**
   * 商品刚上架,初始化状态
   */
  private def initReceive: Receive = {
    case SecKillRequest(userId, skuId) =>
      log.info("开始抢购")
      context.become(secKillingReceive)
      self ! SecKillSuccess(userId, IPhone12(skuId))
  }

  /**
   * 正在抢购中
   *
   * @return
   */
  private def secKillingReceive: Receive = {
    case _: SecKillRequest =>
    //     log.info("正在抢购中,请等待")
    case secKillSuccess: SecKillSuccess =>
      context.become(seckilledReceive)
      self ! secKillSuccess
    case SecKillFail =>
      log.info(s"抢购失败")
  }

  /**
   * 秒杀成功
   */
  private def seckilledReceive: Receive = {
    case secKillSuccess@SecKillSuccess(userId, sku) =>
      log.info(s"抢购成功,userId id $userId")
      // 通过parent更新库存
      context.parent ! secKillSuccess
      // 抢购成功后停止当前actor
      context.stop(self)
    case otherMessage =>
      log.warning(s"商品已经被抢购 $otherMessage")
  }

}
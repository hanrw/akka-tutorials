package org.hanrw.akka.spring.mvc.dto

import scala.beans.BeanProperty

/**
 * @author hanrw
 * @date 2021/1/16 6:29 PM
 */
case class User(@BeanProperty userName: String = "")

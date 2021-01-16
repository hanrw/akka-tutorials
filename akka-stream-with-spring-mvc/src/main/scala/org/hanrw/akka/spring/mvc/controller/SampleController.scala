package org.hanrw.akka.spring.mvc.controller

import akka.stream.scaladsl.Source
import org.hanrw.akka.spring.mvc.dto.User
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
 * @author hanrw
 * @date 2021/1/16 6:10 PM
 */
@RestController
class SampleController {
  @RequestMapping(Array("/"))
  def index = Source(List(User("user-a"), User("user-b")))
}

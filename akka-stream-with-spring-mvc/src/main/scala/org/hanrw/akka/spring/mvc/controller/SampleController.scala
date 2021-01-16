package org.hanrw.akka.spring.mvc.controller

import akka.stream.javadsl.Source
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
 * @author hanrw
 * @date 2021/1/16 6:10 PM
 */
@RestController
class SampleController {
  @RequestMapping(Array("/"))
  def index = Source.repeat("Hello world!").intersperse("\n").take(10)
}

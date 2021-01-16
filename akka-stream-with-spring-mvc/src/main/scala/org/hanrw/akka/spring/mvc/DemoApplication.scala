package org.hanrw.akka.spring.mvc
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
/**
 * @author hanrw
 * @date 2021/1/16 5:35 PM
 */
@SpringBootApplication
class DemoApplication

object DemoApplication extends App {
  SpringApplication.run(classOf[DemoApplication], args: _*)
}
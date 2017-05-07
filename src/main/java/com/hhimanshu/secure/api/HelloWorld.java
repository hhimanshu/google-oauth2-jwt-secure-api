package com.hhimanshu.secure.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HelloWorld {

  @RequestMapping(method = RequestMethod.GET, value = "/rest/hello")
  public String sayHello() {
    return "Hello World!";
  }
}

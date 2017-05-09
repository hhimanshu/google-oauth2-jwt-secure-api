package com.hhimanshu.secure.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class HelloWorldService {

  @RequestMapping(method = RequestMethod.GET, path = "hello")
  public String sayHello() {
    return "Hello World!";
  }
}

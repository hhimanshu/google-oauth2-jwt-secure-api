package com.hhimanshu.secure.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class TweetsService {

  @RequestMapping(method = RequestMethod.GET, path = "tweets")
  public String tweets() {
    return "My Tweets!";
  }
}

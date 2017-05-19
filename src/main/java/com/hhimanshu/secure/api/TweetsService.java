package com.hhimanshu.secure.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class TweetsService {

  @RequestMapping(method = RequestMethod.GET, path = "tweets")
  public String tweets(@RequestAttribute String userId) {
    return "My Tweets! " + userId;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/tweets/{year}/{month}")
  public String tweets(@PathVariable int year, @PathVariable int month, @RequestAttribute String userId) {
    return "user:" + userId + ",year: " + year + ",month=" + month;
  }
}

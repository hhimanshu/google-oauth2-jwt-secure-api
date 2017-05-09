package com.hhimanshu.secure.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("login")
public class LoginService {

  @RequestMapping(method = POST)
  public void authenticate() {
  }
}
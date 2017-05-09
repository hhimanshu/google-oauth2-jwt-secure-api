package com.hhimanshu.secure.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginService {
  @RequestMapping(method = POST, value = "/login")
  public void authenticate() {
  }
}
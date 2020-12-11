package oit.is.beef_good.wine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/group_home")
public class GroupHomeController {
  @GetMapping("")
  public String page() {
    return "group_home.html";
  }
}

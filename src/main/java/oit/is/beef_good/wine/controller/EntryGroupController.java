package oit.is.beef_good.wine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entry_group")
public class EntryGroupController {

  @GetMapping("")
  public String page() {
    return "entry_group.html";
  }
}

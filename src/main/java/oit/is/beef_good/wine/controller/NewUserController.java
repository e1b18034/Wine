package oit.is.beef_good.wine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/new_user")
public class NewUserController {
  @GetMapping("/form")
  public String form() {
    return "new_user_form.html";
  }
}

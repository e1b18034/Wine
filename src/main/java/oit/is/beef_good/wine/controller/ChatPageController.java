package oit.is.beef_good.wine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat_page")
public class ChatPageController {
  @GetMapping("")
  public String chatPage() {
    return "chat_page.html";
  }
}

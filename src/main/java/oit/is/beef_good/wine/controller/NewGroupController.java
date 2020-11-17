package oit.is.beef_good.wine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.beef_good.wine.model.Group;
import oit.is.beef_good.wine.model.GroupMapper;

@Controller
@RequestMapping("/new_group")
public class NewGroupController {
  @Autowired
  private GroupMapper groupMapper;

  @GetMapping("")
  public String page(ModelMap model) {
    List<Group> groups = groupMapper.getAllGroups();

    model.addAttribute("groups", groups);

    return "new_group_page.html";
  }
}

package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.services.UserService;

@Controller
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/")
  public String getHomePage(Model model) {
    List<User> arrUser = this.userService.getAllUserByEmail("1@gmail.com");
    System.out.println(arrUser);
    model.addAttribute("eric", "test");
    model.addAttribute("hoidanit", "from controller with model");
    return "hello";
  }

  @RequestMapping("/admin/user")
  public String getUserPage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
  public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
    System.out.println(" run here " + hoidanit);
    this.userService.handleSaveUser(hoidanit);
    return "hello";
  }
}

package vn.hoidanit.laptopshop.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
  private final UserService userService;
  private final UploadService uploadService;
  private final PasswordEncoder passwordEncoder;

  public UserController(UserService userService, UploadService uploadService,
      PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.uploadService = uploadService;
    this.passwordEncoder = passwordEncoder;
  }

  // @RequestMapping("/")
  // public String getHomePage(Model model) {
  // List<User> arrUser = this.userService.getAllUserByEmail("1@gmail.com");
  // System.out.println(arrUser);
  // model.addAttribute("eric", "test");
  // model.addAttribute("hoidanit", "from controller with model");
  // return "hello";
  // }

  @RequestMapping("/admin/user")
  public String getUserPage(Model model) {
    List<User> users = this.userService.getAllUser();
    // System.out.println("check user " + users);
    model.addAttribute("user1", users);
    return "admin/user/show";
  }

  @RequestMapping("/admin/user/{id}")
  public String getUserDetailPage(Model model, @PathVariable long id) {
    User user = this.userService.getUserById(id);
    model.addAttribute("id", id);

    model.addAttribute("user", user);
    return "admin/user/detail";
  }

  @GetMapping("/admin/user/create")
  public String getCreateUserPage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @PostMapping("/admin/user/create")
  public String createUserPage(Model model,
      @ModelAttribute("newUser") User hoidanit,
      @RequestParam("hoidanitFile") MultipartFile file) {

    String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
    String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());
    hoidanit.setAvatar(avatar);
    hoidanit.setPassword(hashPassword);
    hoidanit.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));
    this.userService.handleSaveUser(hoidanit);
    return "redirect:/admin/user";
  }

  @RequestMapping("/admin/user/update/{id}")
  public String getUpdateUserPage(Model model, @PathVariable long id) {
    User currentUser = this.userService.getUserById(id);
    model.addAttribute("newUser", currentUser);
    return "admin/user/update";
  }

  @PostMapping("/admin/user/update")
  public String getUpdateUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
    User currentUser = this.userService.getUserById(hoidanit.getId());
    // model.addAttribute("newUser", currentUser);

    if (currentUser != null) {
      System.out.println("currentUser " + currentUser);
      currentUser.setAddress(hoidanit.getAddress());
      currentUser.setPhone(hoidanit.getPhone());
      currentUser.setFullName(hoidanit.getFullName());
      this.userService.handleSaveUser(currentUser);
    }
    return "redirect:/admin/user";
  }

  @GetMapping("/admin/user/delete/{id}")
  public String getDeleteUser(Model model, @PathVariable long id) {
    model.addAttribute("id", id);
    // User user = new User();
    // user.setId(id);
    model.addAttribute("newUser", new User());
    return "/admin/user/delete";
  }

  @PostMapping("/admin/user/delete")
  public String getDeleteUser(Model model, @ModelAttribute("newUser") User eric) {
    this.userService.deleteAUser(eric.getId());
    return "redirect:/admin/user";
  }
}

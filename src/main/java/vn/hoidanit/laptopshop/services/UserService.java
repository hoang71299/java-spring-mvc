package vn.hoidanit.laptopshop.services;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String handleHello() {
    return "Hello from service";
  }

  public List<User> getAllUser() {
    return this.userRepository.findAll();
  }

  public List<User> getAllUserByEmail(String email) {
    return this.userRepository.findOneByEmail(email);
  }

  public User getUserById(long id) {
    return this.userRepository.findById(id);
  }

  public User handleSaveUser(User user) {
    User eric = this.userRepository.save(user);
    System.out.println(eric);
    return eric;
  }
}

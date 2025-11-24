package com.example.blog.service;

import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
  }

  @Transactional
  public User updateUser(Long id, User userDetails) {
    User user = findUserById(id);

    if (userDetails.getUsername() != null) {
      user.setUsername(userDetails.getUsername());
    }
    if (userDetails.getEmail() != null) {
      user.setEmail(userDetails.getEmail());
    }

    return userRepository.save(user);
  }

  @Transactional
  public void deleteUser(Long id) {
    User user = findUserById(id);
    userRepository.delete(user);
  }

  @Transactional
  public void deleteUserById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
  }
}

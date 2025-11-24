package com.example.blog.config;

import com.example.blog.model.Role;
import com.example.blog.model.User;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

  @Value("${admin.username:admin}")
  private String adminUsername;

  @Value("${admin.email:admin@example.com}")
  private String adminEmail;

  @Value("${admin.password:admin123}")
  private String adminPassword;

  @Bean
  CommandLineRunner initDatabase(UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    return args -> {
      // 管理者ユーザーが存在しない場合のみ作成
      if (userRepository.findByUsername(adminUsername).isEmpty()) {

        Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(userRole);
        admin.setRoles(roles);

        userRepository.save(admin);

        System.out.println("=================================");
        System.out.println("管理者ユーザーを作成しました");
        System.out.println("ユーザー名: " + adminUsername);
        System.out.println("メール: " + adminEmail);
        System.out.println("=================================");
      }
    };
  }
}

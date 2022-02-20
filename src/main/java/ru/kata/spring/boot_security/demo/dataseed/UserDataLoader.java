package ru.kata.spring.boot_security.demo.dataseed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repo.RoleRepository;
import ru.kata.spring.boot_security.demo.repo.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (roleRepository.count() == 0) {
            Role role1 = new Role();
            role1.setName("ROLE_ADMIN");
            Role role2 = new Role();
            role2.setName("ROLE_USER");
            roleRepository.save(role1);
            roleRepository.save(role2);
        }

        if (userRepository.count() == 0) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = "admin";
            String encodePass = passwordEncoder.encode(password);
            System.out.println(encodePass);

            User user = new User("admin", encodePass, "Alexander", "Martynets", 18);
            Set<Role> roles = user.getRoles();//new HashSet<>();
            Role role1 = roleRepository.findByName("ROLE_ADMIN");
            roles.add(role1);
            Role role2 = roleRepository.findByName("ROLE_USER");
            roles.add(role2);
            user.setRoles(roles);
            userRepository.save(user);

            String password1 = "user";
            String encodePass1 = passwordEncoder.encode(password1);

            User user2 = new User("user", encodePass1, "Name", "Lastname", 100);
            Set<Role> roles2 = user2.getRoles();//new HashSet<>();
            Role role21 = roleRepository.findByName("ROLE_USER");
            roles2.add(role21);
            user2.setRoles(roles2);
            userRepository.save(user2);
        }

        System.out.println(userRepository.count());
    }
}

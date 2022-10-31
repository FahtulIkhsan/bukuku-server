package com.kelompok7.bukuku.user;

import com.kelompok7.bukuku.user.role.ERole;
import com.kelompok7.bukuku.user.role.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        else{
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = user.getAuthorities();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(encoder().encode(user.getPassword()));
//        user.setPassword(String.format("{bcrypt}%s",user.getPassword()));
        Set<Role> role = new HashSet<>();
        role.add(new Role(ERole.ROLE_USER));
        user.setRoles(role);
        return userRepo.save(user);
    }

    @Override
    public User getUser(Long userId) {
        log.info("Fetching user {}", userId);
        return userRepo.findByUserId(userId);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Deleting user {}", userId);
        userRepo.deleteById(userId);
    }

    @Override
    public List<User> getUser() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}

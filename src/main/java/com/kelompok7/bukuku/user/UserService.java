package com.kelompok7.bukuku.user;

import org.springframework.core.codec.EncodingException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(Long userId);
    User getUser(String username);
    void deleteUser(Long userId);
    List<User> getUser();
}

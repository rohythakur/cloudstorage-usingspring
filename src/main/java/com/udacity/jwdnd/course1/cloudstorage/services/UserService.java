package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.utils.KeyGenerator;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;
    private final KeyGenerator keyGenerator;

    public UserService(UserMapper userMapper, HashService hashService, KeyGenerator keyGenerator) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.keyGenerator = keyGenerator;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUserByName(username) == null;
    }

    public int createUser(User user) {
        final String salt = keyGenerator.generate();
        final String hashedPassword = hashService.getHashedValue(user.getPassword(), salt);
        final User newUser = new User(
                null,
                user.getUsername(),
                salt,
                hashedPassword,
                user.getFirstName(),
                user.getLastName()
        );
        return userMapper.insert(newUser);
    }

    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }
}

package com.example.javier.mot2;

/**
 * Created by Javier on 2/26/2018.
 */

public interface UserService {

    User getUserByUsername(String username);

    void insertAll(User... users);
}

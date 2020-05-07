package userservice.service;


import userservice.entity.User;

import java.util.Optional;

public interface UserService {

    public Optional<User> userByEmailAndPassword(String email,String password);



}

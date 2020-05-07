package userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import userservice.dao.custom.UserCustomDao;
import userservice.entity.User;
import userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCustomDao userCustomDao;

    @Override
    public Optional<User> userByEmailAndPassword(String email, String password) {
        return userCustomDao.userByEmailAndPassword(email,password);
    }
}

package com.example.game_test.services;

import com.example.game_test.enteties.User;
import com.example.game_test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByLogin(String login){
        return this.userRepository.findUserByLogin(login);
    }

    public User findById(Long id){return this.userRepository.findById(id).orElse(null);}


    //add user
    public void addUser(String login, String password){
        User user=new User.Builder(login, password).build();
        userRepository.save(user);
    }


    //check if user with login and password is presnted in db
    public boolean checkUserInDB(String login, String password){
        List<User> userList=userRepository.findAll();
        for (User u:userList){
            if (u.getLogin().compareTo(login) == 0 & u.getPassword().compareTo(password) == 0)
                return true;
        }
        return false;
    }


}

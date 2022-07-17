package com;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public Integer addUser(String username){
        System.out.println("user dao adduser [username="+username+"]");
        if(username == null){
            return 0;
        }
        return 1;
    }
}

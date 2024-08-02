package com.p2p_lending.p2p_backend.services;

import com.p2p_lending.p2p_backend.models.User;
import org.springframework.stereotype.Service;

@Service
public class LendingService {

    public String processUser(User user) {
        return user.getName() + ", your phone number is: " + user.getPhno();
    }

}

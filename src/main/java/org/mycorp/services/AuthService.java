package org.mycorp.services;
import org.mycorp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(BCryptPasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    public User authentication(User user, User findUser){
        if(passwordEncoder.matches(user.getPassword(), findUser.getPassword())){
            return findUser;
        }
        else
            return null;
    }

    public String passwordEncoding(String password){
        return passwordEncoder.encode(password);
    }
}

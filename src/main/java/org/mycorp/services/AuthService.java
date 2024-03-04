package org.mycorp.services;
import org.mycorp.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(BCryptPasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    public UserDao authorisation(UserDao user, UserDao findUser){
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

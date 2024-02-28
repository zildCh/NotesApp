package org.mycorp.services;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.mycorp.models_buisnes.UserDto;
import org.mycorp.models_dao.UserDao;
import org.mycorp.repository.RepositoryUser;
import org.mycorp.services.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService extends ServiceImpl<UserDao,UserDto>{

    @Autowired
    public UserService(RepositoryUser repositoryUser, UserConverter converter){
        super(repositoryUser, converter);
    }

}

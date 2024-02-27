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

    @Override
    protected UserDao convertToDAO(UserDto dtoObj) {
        return new UserDao(dtoObj.getId(), dtoObj.getNickname(), passwordToHash("saa"));
    }

    @Override
    protected UserDto convertToDTO(UserDao daoObj) {
        return new UserDto();
    }

    @Override
    protected UserDao updateDAO(UserDao daoObj, UserDto dtoObj) {
        daoObj.setNickname(dtoObj.getNickname());
        daoObj.setPassword(passwordToHash(dtoObj.getPassword()));
        return daoObj;
    }

    private byte[] passwordToHash(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        System.out.printf("salt: %s%n", enc.encodeToString(salt));
        System.out.printf("hash: %s%n", enc.encodeToString(hash));
        return hash;
    }
}

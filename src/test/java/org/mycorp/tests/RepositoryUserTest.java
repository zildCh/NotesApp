package org.mycorp.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycorp.models.UserDao;
import org.mycorp.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@DataJpaTest
public class RepositoryUserTest {

    private static UserDao user;

    @Autowired
    RepositoryUser repositoryUser;

    @Test
    public void saveTest(){
        byte[] myByteArray = {1, 2, 3, 4, 5};
        //user = new UserDao("egor", myByteArray);

        // Сохраняем пользователя в репозитории
        UserDao savedUser = repositoryUser.save(user);

        // Проверяем, что у сохраненного пользователя установлен идентификатор
        assertNotNull(savedUser.getId());
        System.out.println(user.getId());
    }
}

@Configuration
@EnableJpaRepositories(basePackages = "org.mycorp.repository")
class Config {

}


package org.mycorp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Main{

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}

@Configuration
@EnableJpaRepositories(basePackages = "org.mycorp.repository")
class Configur {
    // Дополнительные настройки, если необходимо
}
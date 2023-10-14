package me.mocadev.springtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SpringTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringTestApplication.class, args);
  }
}

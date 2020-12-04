package oit.is.beef_good.wine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WineApplication {

  public static void main(String[] args) {
    SpringApplication.run(WineApplication.class, args);
  }

}

package kuit.springbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BackendSpringBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendSpringBasicApplication.class, args);
    }

}

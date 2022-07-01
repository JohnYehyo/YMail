package com.rongji.rjsoft.email;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description: 启动类
 * @author: JohnYehyo
 * @create: 2021-04-25 19:09:03
 */
@EnableConfigurationProperties
@EnableEncryptableProperties
@SpringBootApplication(scanBasePackages = {"com.rongji.rjsoft.email"})
public class EmailApplicaiton {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EmailApplicaiton.class);
        springApplication.addListeners(new ApplicationPidFileWriter("./email.pid"));
        springApplication.run(args);
    }
}

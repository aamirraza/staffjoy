package com.phoebe.staffjoy.whoami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import com.phoebe.staffjoy.common.config.StaffjoyRestConfig;

@Import(value = StaffjoyRestConfig.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = {"com.phoebe.staffjoy.company", "com.phoebe.staffjoy.account"})
public class WhoAmIApplication {
    public static void main(String[] args) {
        SpringApplication.run(WhoAmIApplication.class, args);
    }
}

package com.knewton.planner.ical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import com.knewton.planner.common.config.StaffjoyWebConfig;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = {"com.knewton.planner.company"})
@Import(value = StaffjoyWebConfig.class)
public class ICalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ICalApplication.class, args);
    }
}

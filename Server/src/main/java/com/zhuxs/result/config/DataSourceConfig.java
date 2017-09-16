package com.zhuxs.result.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by shusesshou on 2017/9/16.
 */
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = {
        "com.zhuxs.result.domain"
})
@EntityScan("com.zhuxs.result.domain.entity")
public class DataSourceConfig {
}

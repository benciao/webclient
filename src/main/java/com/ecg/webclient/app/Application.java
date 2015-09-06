package com.ecg.webclient.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ImportResource(
{ "classpath:/com/ecg/beanconfigs/rabbitmq_config.xml",
        "classpath:/com/ecg/beanconfigs/webclient-rabbit_config.xml",
        "classpath:/com/ecg/beanconfigs/webclient-orientdb_config.xml" })
@ComponentScan("com.ecg")
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}

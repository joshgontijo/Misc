package com.sample;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Josue Gontijo <josue.gontijo@maersk.com>.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.sample")
public class Configurator {
}

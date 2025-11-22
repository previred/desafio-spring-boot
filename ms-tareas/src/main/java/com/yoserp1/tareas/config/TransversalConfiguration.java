package com.yoserp1.tareas.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.yoserp1.tareas"})
@EnableJpaRepositories(basePackages = {"com.yoserp1.tareas"})
@EntityScan(basePackages = {"com.yoserp1.tareas"})
public class TransversalConfiguration {}

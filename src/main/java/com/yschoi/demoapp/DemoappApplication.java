package com.yschoi.demoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
* @packageName   : com.yschoi.demoapp
* @fileName      : DemoappApplication.java
* @author        : YS CHOI
* @date          : 2025.11.04
* @description   : Application Starter
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.04        YS CHOI       최초 생성
*/

@SpringBootApplication
public class DemoappApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoappApplication.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoappApplication.class);
    }
}

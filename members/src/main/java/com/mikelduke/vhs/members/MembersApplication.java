package com.mikelduke.vhs.members;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.opentracing.Tracer;
import io.opentracing.contrib.spring.web.client.TracingRestTemplateInterceptor;

@SpringBootApplication
public class MembersApplication {

	@Autowired
	Tracer tracer;

	public static void main(String[] args) {
		SpringApplication.run(MembersApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate template = builder.build();
		template.setInterceptors(Collections.singletonList(new TracingRestTemplateInterceptor(tracer)));

		return template;
	}
}

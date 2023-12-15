package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.observation.DefaultServerRequestObservationConvention;
import org.springframework.http.server.reactive.observation.ServerRequestObservationConvention;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;

import static org.assertj.core.api.Assertions.assertThat;

class DemoApplicationTests {

	@Test
	void should_not_silently_use_DefaultServerRequestObservationConvention_when_other_ServerRequestObservation_beans_are_defined() {
		ConfigurableApplicationContext context = SpringApplication.run(ApplicationWithTwoCustomServerRequestObservationConventionBeans.class, "--server.port=0");
		HttpWebHandlerAdapter httpWebHandlerAdapter = context.getBean(HttpWebHandlerAdapter.class);
		assertThat(httpWebHandlerAdapter.getObservationConvention()).isNotInstanceOf(DefaultServerRequestObservationConvention.class);
		context.close();
	}

	@SpringBootApplication
	static class ApplicationWithTwoCustomServerRequestObservationConventionBeans {

		@Configuration
		static class Config {

			@Bean
			ServerRequestObservationConvention serverRequestObservationConvention1() {
				return new ServerRequestObservationConvention() {
					@Override
					public String getName() {
						return "Custom convention 1";
					}
				};
			}

			@Bean
			ServerRequestObservationConvention serverRequestObservationConvention2() {
				return new ServerRequestObservationConvention() {
					@Override
					public String getName() {
						return "Custom convention 2";
					}
				};
			}
		}
	}
}

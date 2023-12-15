package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.observation.ServerRequestObservationConvention;

import static org.assertj.core.api.Assertions.assertThatException;

class DemoApplicationTests {

	@Test
	void fails_on_startup_when_no_unique_ServerRequestObservationConvention_is_present() {
		assertThatException()
				.isThrownBy(() -> SpringApplication.run(ApplicationWithTwoCustomServerRequestObservationConventionBeans.class))
				.withRootCauseInstanceOf(NoUniqueBeanDefinitionException.class);
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

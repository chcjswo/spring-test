package me.mocadev.springtest;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Ignore
@Transactional
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestInit.class)
public class IntegrationTest {

	static DockerComposeContainer container;

	static {
		container = new DockerComposeContainer(
			new File("infra/test/docker-compose.yml")
		).withExposedService("local-db", 3306,
				Wait.forLogMessage(".*ready for connections.*", 1)
					.withStartupTimeout(Duration.ofSeconds(300))
		)
		.withExposedService("local-db-migrate", 0,
			Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
				.withStartupTimeout(Duration.ofSeconds(300))
		);
		container.start();
	}

	static class IntegrationTestInit implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			Map<String, String> properties = new HashMap<>();

			var serviceHost = container.getServiceHost("local-db", 3306);
			var servicePort = container.getServicePort("local-db", 3306);

			properties.put("spring.datasource.url", String.format("jdbc:mysql://%s:%s/spring_test?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8",
				serviceHost, servicePort));

			TestPropertyValues.of(properties).applyTo(applicationContext);
		}
	}
}

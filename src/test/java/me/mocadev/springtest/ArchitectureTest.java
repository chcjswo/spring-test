package me.mocadev.springtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

class ArchitectureTest {

	JavaClasses javaClasses;

	@BeforeEach
	void setUp() {
		javaClasses = new ClassFileImporter()
			.withImportOption(new ImportOption.DoNotIncludeTests())
			.importPackages("me.mocadev.springtest");
	}

	@Test
	@DisplayName("controller 패키지 안에 있는 클래스들은 Controller로 끝나야 한다.")
	void controllerTest() {
		ArchRule rule = classes()
			.that().resideInAPackage("..controller")
			.should().haveSimpleNameEndingWith("Controller");

		ArchRule annotationRule = classes()
			.that().resideInAPackage("..controller")
			.should().beAnnotatedWith(RestController.class)
			.orShould().beAnnotatedWith(Controller.class);

		rule.check(javaClasses);
		annotationRule.check(javaClasses);
	}

	@Test
	@DisplayName("request 패키지 안에 있는 클래스들은 Request로 끝나야 한다.")
	void request() {
		ArchRule rule = classes()
			.that().resideInAPackage("..request..")
			.should().haveSimpleNameEndingWith("Request");

		rule.check(javaClasses);
	}

	@Test
	@DisplayName("response 패키지 안에 있는 클래스들은 Response로 끝나야 한다.")
	void response() {
		ArchRule rule = classes()
			.that().resideInAPackage("..response..")
			.should().haveSimpleNameEndingWith("Response");

		rule.check(javaClasses);
	}

	@Test
	@DisplayName("repository 패키지 안에 있는 클래스들은 Repository로 끝나야 하고, Repository 인터페이스를 상속받아야 한다.")
	void repository() {
		ArchRule rule = classes()
			.that().resideInAPackage("..repository..")
			.should().haveSimpleNameEndingWith("Repository")
			.andShould().beInterfaces();

		rule.check(javaClasses);
	}

	@Test
	@DisplayName("service 패키지 안에 있는 클래스들은 Service로 끝나야 하고, @Service 어노테이션이 있어야 한다.")
	void service() {
		ArchRule rule = classes()
			.that().resideInAPackage("..service..")
			.should().haveSimpleNameEndingWith("Service")
			.andShould().beAnnotatedWith(Service.class);

		rule.check(javaClasses);
	}
}

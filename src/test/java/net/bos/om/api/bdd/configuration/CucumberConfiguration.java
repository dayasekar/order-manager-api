package net.bos.om.api.bdd.configuration;

import io.cucumber.spring.CucumberContextConfiguration;
import net.bos.om.api.Application;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class CucumberConfiguration {
}

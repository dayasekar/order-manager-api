package net.bos.om.api.bdd.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Status {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Given("The service is up and running")
    public void theServiceIsUpAndRunning() {
    }

    @When("the rest client invokes \\/ping endpoint")
    public void theRestClientInvokesPingEndpoint() {
        String url = "http://localhost:" + port + "/ping";
        this.response = restTemplate.getForEntity(url, String.class);
    }

    @Then("the api responds with status code of {int} and response string pong")
    public void theApiRespondsWithStatusCodeOfAndResponseStringPong(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(HttpStatus.OK.value()));
        assertThat(this.response.getBody(), equalTo("pong"));
    }
}

package net.bos.om.api.bdd.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;
import net.bos.om.api.io.input.execution.AddExecutionInput;
import net.bos.om.api.io.output.execution.ExecutionOutput;
import net.bos.om.api.persistence.InMemoryPersistenceImpl;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddExecution {

    public static final String ARBITRARY_INSTRUMENT_ID = "FX_SWAP_1";
    public static final String ARBITRARY_INSTRUMENT_TYPE = "FX_SWAP";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InMemoryPersistenceImpl inMemoryPersistence;

    private ResponseEntity<InvocationResponse<ExecutionOutput>> response;

    @Before
    public void cleanUp() {
        inMemoryPersistence.getStore().clear();
    }

    @When("the rest client invokes \\/execution\\/add POST endpoint")
    public void theRestClientInvokesExecutionAddPOSTEndpoint() {
        String url = "http://localhost:" + port + "/execution/add";
        AddExecutionInput input = new AddExecutionInput();
        input.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        input.setPrice(1.1);
        input.setQuantity(100);
        ParameterizedTypeReference<InvocationResponse<ExecutionOutput>> ref = new ParameterizedTypeReference<InvocationResponse<ExecutionOutput>>() {
        };
        HttpEntity<AddExecutionInput> entity = new HttpEntity<>(input);
        this.response = restTemplate.exchange(url, HttpMethod.POST, entity, ref);
    }

    @Given("A sample execution with price as {int} and existing instrument id as fx_swap and other mandatory fields")
    public void aSampleExecutionWithPriceAsAndExistingInstrumentIdAsFx_swapAndOtherMandatoryFields(int arg0) {
        OrderBook orderBook = new OrderBook();
        orderBook.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        orderBook.setStatus(OrderBookStatus.CLOSED);
        orderBook.setExecutions(new ArrayList<>());
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, orderBook);
    }

    @Then("the add execution api responds with status code of {int} and response of newly created execution")
    public void theAddExecutionApiRespondsWithStatusCodeOfAndResponseOfNewlyCreatedExecution(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getResponse().getExecution().getInstrumentID(), equalTo(ARBITRARY_INSTRUMENT_ID));
    }

    @Given("A sample order with price as {int} and existing instrument id as fx_swap, status open and other mandatory fields")
    public void aSampleOrderWithPriceAsAndExistingInstrumentIdAsFx_swapStatusOpenAndOtherMandatoryFields(int arg0) {
        OrderBook orderBook = new OrderBook();
        orderBook.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        orderBook.setStatus(OrderBookStatus.OPEN);
        orderBook.setOrders(new ArrayList<>());
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, orderBook);
    }

    @Then("the add execution api responds with status code of {int} and response of error message")
    public void theAddExecutionApiRespondsWithStatusCodeOfAndResponseOfErrorMessage(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getErrorMessages().get(0), equalTo("Unable to add Execution as book is OPEN"));
    }
}

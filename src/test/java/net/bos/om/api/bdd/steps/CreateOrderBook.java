package net.bos.om.api.bdd.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;
import net.bos.om.api.io.input.orderbook.CreateOrderBookInput;
import net.bos.om.api.io.output.orderbook.OrderBookOutput;
import net.bos.om.api.persistence.InMemoryPersistenceImpl;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderBook {
    public static final String ARBITRARY_INSTRUMENT_ID = "FX_SWAP_1";
    public static final String ARBITRARY_INSTRUMENT_TYPE = "FX_SWAP";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InMemoryPersistenceImpl inMemoryPersistence;

    private ResponseEntity<InvocationResponse<OrderBookOutput>> response;

    @Before
    public void cleanUp() {
        inMemoryPersistence.getStore().clear();
    }

    @Given("The instrument id of the order book is fx_swap")
    public void theInstrumentIdOfTheOrderBookIsFx_swap() {
    }

    @When("the rest client invokes \\/order-book\\/create POST endpoint")
    public void theRestClientInvokesOrderBookCreatePOSTEndpoint() {
        String url = "http://localhost:" + port + "/order-book/create";
        CreateOrderBookInput input = new CreateOrderBookInput();
        input.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        input.setInstrumentType(ARBITRARY_INSTRUMENT_TYPE);
        ParameterizedTypeReference<InvocationResponse<OrderBookOutput>> ref = new ParameterizedTypeReference<InvocationResponse<OrderBookOutput>>() {
        };
        HttpEntity<CreateOrderBookInput> entity = new HttpEntity<>(input);
        this.response = restTemplate.exchange(url, HttpMethod.POST, entity, ref);
    }

    @Then("the api responds with status code of {int} and response of newly created order book")
    public void theApiRespondsWithStatusCodeOfAndResponseOfNewlyCreatedOrderBook(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getResponse().getOrderBook().getInstrumentID(), equalTo(ARBITRARY_INSTRUMENT_ID));
        assertThat(this.response.getBody().getResponse().getOrderBook().getStatus(), equalTo(OrderBookStatus.OPEN));
    }

    @Given("The instrument id of the order book is fx_swap and instrument id of the existing order book is fx_swap")
    public void theInstrumentIdOfTheOrderBookIsFx_swapAndInstrumentIdOfTheExistingOrderBookIsFx_swap() {
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, new OrderBook());
    }

    @Then("the api responds with status code of {int} and response of error messages")
    public void theApiRespondsWithStatusCodeOfAndResponseOfErrorMessages(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getErrorMessages().get(0), equalTo("Unable to create requested order book as it already exists"));
    }
}

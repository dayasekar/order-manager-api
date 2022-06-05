package net.bos.om.api.bdd.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;
import net.bos.om.api.io.input.orderbook.CloseOrderBookInput;
import net.bos.om.api.io.input.orderbook.OpenOrderBookInput;
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

public class OpenOrderBook {
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

    @When("the rest client invokes \\/order-book\\/open PUT endpoint")
    public void theRestClientInvokesOrderBookOpenPUTEndpoint() {
        String url = "http://localhost:" + port + "/order-book/open";
        OpenOrderBookInput input = new OpenOrderBookInput();
        input.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        ParameterizedTypeReference<InvocationResponse<OrderBookOutput>> ref = new ParameterizedTypeReference<InvocationResponse<OrderBookOutput>>() {
        };
        HttpEntity<OpenOrderBookInput> entity = new HttpEntity<>(input);
        this.response = restTemplate.exchange(url, HttpMethod.POST, entity, ref);
    }

    @Given("There exists an existing closed order book with instrument id fx_swap")
    public void thereExistsAnExistingClosedOrderBookWithInstrumentIdFx_swap() {
        OrderBook orderBook = new OrderBook();
        orderBook.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        orderBook.setStatus(OrderBookStatus.CLOSED);
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, orderBook);
    }


    @Then("the open order api responds with status code of {int} and success response")
    public void theOpenOrderApiRespondsWithStatusCodeOfAndSuccessResponse(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getResponse().getOrderBook().getInstrumentID(), equalTo(ARBITRARY_INSTRUMENT_ID));
        assertThat(this.response.getBody().getResponse().getOrderBook().getStatus(), equalTo(OrderBookStatus.OPEN));
    }

    @Given("There exists an existing closed order book with status OPEN and instrument id fx_swap")
    public void thereExistsAnExistingClosedOrderBookWithStatusOPENAndInstrumentIdFx_swap() {
        OrderBook orderBook = new OrderBook();
        orderBook.setStatus(OrderBookStatus.OPEN);
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, orderBook);
    }

    @Then("the open order api responds with status code of {int} and response of error messages")
    public void theOpenOrderApiRespondsWithStatusCodeOfAndResponseOfErrorMessages(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getErrorMessages().get(0), equalTo("Order book is already OPEN"));
    }
}

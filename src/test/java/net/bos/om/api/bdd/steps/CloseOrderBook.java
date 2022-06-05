package net.bos.om.api.bdd.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;
import net.bos.om.api.io.input.orderbook.CloseOrderBookInput;
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

public class CloseOrderBook {

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

    @Given("There exists an existing order book with instrument id fx_swap")
    public void thereExistsAnExistingOrderBookWithInstrumentIdFx_swap() {
        OrderBook orderBook = new OrderBook();
        orderBook.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        orderBook.setStatus(OrderBookStatus.OPEN);
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, orderBook);
    }

    @When("the rest client invokes \\/order-book\\/close POST endpoint")
    public void theRestClientInvokesOrderBookClosePOSTEndpoint() {
        String url = "http://localhost:" + port + "/order-book/close";
        CloseOrderBookInput input = new CloseOrderBookInput();
        input.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        ParameterizedTypeReference<InvocationResponse<OrderBookOutput>> ref = new ParameterizedTypeReference<InvocationResponse<OrderBookOutput>>() {
        };
        HttpEntity<CloseOrderBookInput> entity = new HttpEntity<>(input);
        this.response = restTemplate.exchange(url, HttpMethod.POST, entity, ref);
    }

    @Then("the close order api responds with status code of {int} and success response")
    public void theCloseOrderApiRespondsWithStatusCodeOfAndSuccessResponse(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getResponse().getOrderBook().getInstrumentID(), equalTo(ARBITRARY_INSTRUMENT_ID));
        assertThat(this.response.getBody().getResponse().getOrderBook().getStatus(), equalTo(OrderBookStatus.CLOSED));
    }

    @Given("There exists an existing order book with status CLOSED and instrument id fx_swap")
    public void thereExistsAnExistingOrderBookWithStatusCLOSEDAndInstrumentIdFx_swap() {
        OrderBook orderBook = new OrderBook();
        orderBook.setStatus(OrderBookStatus.CLOSED);
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, orderBook);
    }

    @Then("the close order api responds with status code of {int} and response of error messages")
    public void theCloseOrderApiRespondsWithStatusCodeOfAndResponseOfErrorMessages(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getErrorMessages().get(0), equalTo("Order book is already CLOSED"));
    }
}

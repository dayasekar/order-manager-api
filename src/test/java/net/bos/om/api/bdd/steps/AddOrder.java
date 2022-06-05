package net.bos.om.api.bdd.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;
import net.bos.om.api.domain.OrderType;
import net.bos.om.api.io.input.order.AddOrderInput;
import net.bos.om.api.io.input.orderbook.OpenOrderBookInput;
import net.bos.om.api.io.output.order.OrderOutput;
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

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddOrder {

    public static final String ARBITRARY_INSTRUMENT_ID = "FX_SWAP_1";
    public static final String ARBITRARY_INSTRUMENT_TYPE = "FX_SWAP";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InMemoryPersistenceImpl inMemoryPersistence;

    private ResponseEntity<InvocationResponse<OrderOutput>> response;

    @Before
    public void cleanUp() {
        inMemoryPersistence.getStore().clear();
    }

    @When("the rest client invokes \\/order\\/add POST endpoint")
    public void theRestClientInvokesOrderAddPOSTEndpoint() {
        String url = "http://localhost:" + port + "/order/add";
        AddOrderInput input = new AddOrderInput();
        input.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        input.setOrderType(OrderType.LIMIT.name());
        input.setEntryDate(new Date());
        input.setPrice(1.1);
        input.setQuantity(100);
        ParameterizedTypeReference<InvocationResponse<OrderOutput>> ref = new ParameterizedTypeReference<InvocationResponse<OrderOutput>>() {
        };
        HttpEntity<AddOrderInput> entity = new HttpEntity<>(input);
        this.response = restTemplate.exchange(url, HttpMethod.POST, entity, ref);
    }

    @Given("A sample order with price as {int} and existing instrument id as fx_swap and other mandatory fields")
    public void aSampleOrderWithPriceAsAndExistingInstrumentIdAsFx_swapAndOtherMandatoryFields(int arg0) {
        OrderBook orderBook = new OrderBook();
        orderBook.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        orderBook.setStatus(OrderBookStatus.OPEN);
        orderBook.setOrders(new ArrayList<>());
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, orderBook);
    }


    @Then("the add order api responds with status code of {int} and response of newly created order")
    public void theAddOrderApiRespondsWithStatusCodeOfAndResponseOfNewlyCreatedOrder(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getResponse().getOrder().getInstrumentID(), equalTo(ARBITRARY_INSTRUMENT_ID));
    }

    @Given("A sample order with price as {int} and existing instrument id as fx_swap, status closed and other mandatory fields")
    public void aSampleOrderWithPriceAsAndExistingInstrumentIdAsFx_swapStatusClosedAndOtherMandatoryFields(int arg0) {
        OrderBook orderBook = new OrderBook();
        orderBook.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        orderBook.setStatus(OrderBookStatus.CLOSED);
        orderBook.setOrders(new ArrayList<>());
        inMemoryPersistence.getStore().put(ARBITRARY_INSTRUMENT_ID, orderBook);
    }

    @Then("the add order api responds with status code of {int} and response of error message")
    public void theAddOrderApiRespondsWithStatusCodeOfAndResponseOfErrorMessage(int arg0) {
        assertThat(this.response.getStatusCodeValue(), equalTo(arg0));
        assertThat(this.response.getBody().getErrorMessages().get(0), equalTo("Unable to add Order as book is CLOSED"));
    }
}

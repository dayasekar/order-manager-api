package net.bos.om.api.persistence;

import lombok.Data;
import net.bos.om.api.domain.Execution;
import net.bos.om.api.domain.Order;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Data
public class InMemoryPersistenceImpl implements BookingPersistence {

    private final Map<String, OrderBook> store = new ConcurrentHashMap<>();

    @Override
    public OrderBook createOrderBook(OrderBook orderBook) {
        store.put(orderBook.getInstrumentID(), orderBook);
        return orderBook;
    }

    @Override
    public OrderBook getOrderBook(String instrumentID) {
        return store.get(instrumentID);
    }

    @Override
    public Order addOrder(Order order) {
        String instrumentID = order.getInstrumentID();
        if (store.containsKey(instrumentID)) {
            store.get(instrumentID).getOrders().add(order);
            return order;
        }
        return null;
    }

    @Override
    public Execution addExecution(Execution execution) {
        String instrumentID = execution.getInstrumentID();
        if (store.containsKey(instrumentID)) {
            store.get(instrumentID).getExecutions().add(execution);
            return execution;
        }
        return null;
    }

    @Override
    public boolean doesOrderBookExist(String instrumentID) {
        return store.containsKey(instrumentID);
    }

    @Override
    public OrderBook updateOrderBookStatus(String instrumentID, OrderBookStatus orderBookStatus) {
        if (store.containsKey(instrumentID)) {
            store.get(instrumentID).setStatus(orderBookStatus);
            return store.get(instrumentID);
        }
        return null;
    }

    @Override
    public OrderBookStatus getOrderBookStatus(String instrumentID) {
        if (store.containsKey(instrumentID)) {
            return store.get(instrumentID).getStatus();
        }
        return null;
    }
}

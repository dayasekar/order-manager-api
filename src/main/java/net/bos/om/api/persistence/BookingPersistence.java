package net.bos.om.api.persistence;

import net.bos.om.api.domain.Execution;
import net.bos.om.api.domain.Order;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;

public interface BookingPersistence {

    OrderBook createOrderBook(OrderBook orderBook);

    OrderBook getOrderBook(String instrumentID);

    Order addOrder(Order order);

    Execution addExecution(Execution execution);

    boolean doesOrderBookExist(String instrumentID);

    OrderBook updateOrderBookStatus(String instrumentID, OrderBookStatus orderBookStatus);

    OrderBookStatus getOrderBookStatus(String instrumentID);
}

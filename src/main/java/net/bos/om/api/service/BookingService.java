package net.bos.om.api.service;

import net.bos.om.api.domain.Execution;
import net.bos.om.api.domain.Order;
import net.bos.om.api.domain.OrderBook;

public interface BookingService {

    OrderBook createOrderBook(OrderBook orderBook);

    OrderBook openOrderBook(OrderBook orderBook);

    OrderBook closeOrderBook(OrderBook orderBook);

    boolean doesOrderBookExist(String instrumentID);

    boolean isOrderBookOpen(String InstrumentID);

    boolean isOrderBookClosed(String InstrumentID);

    Order addOrder(Order order);

    Execution addExecution(Execution execution);
}

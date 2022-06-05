package net.bos.om.api.service;

import net.bos.om.api.domain.Execution;
import net.bos.om.api.domain.Order;
import net.bos.om.api.domain.OrderBook;

public class BookingServiceImpl implements BookingService{
    @Override
    public OrderBook createOrderBook(OrderBook orderBook) {
        return null;
    }

    @Override
    public OrderBook openOrderBook(OrderBook orderBook) {
        return null;
    }

    @Override
    public OrderBook closeOrderBook(OrderBook orderBook) {
        return null;
    }

    @Override
    public boolean doesOrderBookExist(String instrumentID) {
        return false;
    }

    @Override
    public boolean isOrderBookOpen(String InstrumentID) {
        return false;
    }

    @Override
    public boolean isOrderBookClosed(String InstrumentID) {
        return false;
    }

    @Override
    public Order addOrder(Order order) {
        return null;
    }

    @Override
    public Execution addExecution(Execution execution) {
        return null;
    }
}

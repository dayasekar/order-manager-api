package net.bos.om.api.service;

import net.bos.om.api.domain.Execution;
import net.bos.om.api.domain.Order;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;
import net.bos.om.api.exception.BookingServiceException;
import net.bos.om.api.persistence.BookingPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultBookingServiceImpl implements BookingService {

    @Autowired
    private BookingPersistence persistence;

    @Override
    public OrderBook createOrderBook(OrderBook orderBook) {
        if (doesOrderBookExist(orderBook.getInstrumentID())) {
            throw new BookingServiceException("service.error.orderBook.exists");
        }
        orderBook.setStatus(OrderBookStatus.OPEN);
        return persistence.createOrderBook(orderBook);
    }

    @Override
    public OrderBook openOrderBook(String instrumentID) {
        if (isOrderBookOpen(instrumentID)) {
            throw new BookingServiceException("service.error.orderBook.already.open");
        }
        return persistence.updateOrderBookStatus(instrumentID, OrderBookStatus.OPEN);
    }

    @Override
    public OrderBook closeOrderBook(String instrumentID) {
        if (isOrderBookClosed(instrumentID)) {
            throw new BookingServiceException("service.error.orderBook.already.closed");
        }
        return persistence.updateOrderBookStatus(instrumentID, OrderBookStatus.CLOSED);
    }

    @Override
    public boolean doesOrderBookExist(String instrumentID) {
        return Objects.nonNull(persistence.getOrderBook(instrumentID));
    }

    @Override
    public boolean isOrderBookOpen(String instrumentID) {
        if (doesOrderBookExist(instrumentID)) {
            return persistence.getOrderBookStatus(instrumentID).equals(OrderBookStatus.OPEN);
        }
        throw new BookingServiceException("service.error.order.book.absent");
    }

    @Override
    public boolean isOrderBookClosed(String instrumentID) {
        if (doesOrderBookExist(instrumentID)) {
            return persistence.getOrderBookStatus(instrumentID).equals(OrderBookStatus.CLOSED);
        }
        throw new BookingServiceException("service.error.order.book.absent");
    }

    @Override
    public Order addOrder(Order order) {
        if (isOrderBookClosed(order.getInstrumentID())) {
            throw new BookingServiceException("service.error.order.add.book.closed");
        }
        return persistence.addOrder(order);
    }

    @Override
    public Execution addExecution(Execution execution) {
        if (isOrderBookOpen(execution.getInstrumentID())) {
            throw new BookingServiceException("service.error.execution.add.open");
        }
        return persistence.addExecution(execution);
    }
}

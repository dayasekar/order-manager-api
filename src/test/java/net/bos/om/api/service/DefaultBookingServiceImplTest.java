package net.bos.om.api.service;

import net.bos.om.api.domain.Execution;
import net.bos.om.api.domain.Order;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.domain.OrderBookStatus;
import net.bos.om.api.exception.BookingServiceException;
import net.bos.om.api.persistence.BookingPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultBookingServiceImplTest {

    private static final String ARBITRARY_INSTRUMENT_ID = "FX_SWAP_1";
    private static final String ARBITRARY_INSTRUMENT_TYPE = "FX_SWAP";

    @Mock
    private BookingPersistence bookingPersistence;

    @InjectMocks
    private DefaultBookingServiceImpl testObject = new DefaultBookingServiceImpl();

    @Captor
    private ArgumentCaptor<OrderBook> argumentCaptor;

    @Test
    void createOrderBook_whenTypical() {
        OrderBook orderBook = getOrderBook();

        testObject.createOrderBook(orderBook);

        verify(bookingPersistence).createOrderBook(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getStatus(), equalTo(OrderBookStatus.OPEN));
    }

    @Test
    void createOrderBook_whenOrderBookExists() {
        OrderBook orderBook = getOrderBook();
        when(bookingPersistence.getOrderBook(any())).thenReturn(new OrderBook());

        BookingServiceException e = assertThrows(BookingServiceException.class, () -> {
            testObject.createOrderBook(orderBook);
        });

        assertThat(e.getMessage(), equalTo("service.error.orderBook.exists"));
    }

    @Test
    void openOrderBook_whenTypical() {
        OrderBook orderBook = getOrderBook();
        orderBook.setStatus(OrderBookStatus.CLOSED);
        when(bookingPersistence.getOrderBook(any())).thenReturn(orderBook);
        when(bookingPersistence.getOrderBookStatus(any())).thenReturn(OrderBookStatus.CLOSED);

        testObject.openOrderBook(orderBook.getInstrumentID());

        verify(bookingPersistence, times(1)).updateOrderBookStatus(any(), any());
    }

    @Test
    void openOrderBook_whenOrderBookIsAlreadyOpen() {
        OrderBook orderBook = getOrderBook();
        orderBook.setStatus(OrderBookStatus.OPEN);
        when(bookingPersistence.getOrderBook(any())).thenReturn(orderBook);
        when(bookingPersistence.getOrderBookStatus(any())).thenReturn(OrderBookStatus.OPEN);

        BookingServiceException e = assertThrows(BookingServiceException.class, () -> {
            testObject.openOrderBook(orderBook.getInstrumentID());
        });

        assertThat(e.getMessage(), equalTo("service.error.orderBook.already.open"));
    }

    @Test
    void closeOrderBook_whenTypical() {
        OrderBook orderBook = getOrderBook();
        orderBook.setStatus(OrderBookStatus.OPEN);
        when(bookingPersistence.getOrderBook(any())).thenReturn(orderBook);
        when(bookingPersistence.getOrderBookStatus(any())).thenReturn(OrderBookStatus.OPEN);

        testObject.closeOrderBook(orderBook.getInstrumentID());

        verify(bookingPersistence, times(1)).updateOrderBookStatus(any(), any());
    }

    @Test
    void closeOrderBook_whenOrderBookIsAlreadyClosed() {
        OrderBook orderBook = getOrderBook();
        orderBook.setStatus(OrderBookStatus.CLOSED);
        when(bookingPersistence.getOrderBook(any())).thenReturn(orderBook);
        when(bookingPersistence.getOrderBookStatus(any())).thenReturn(OrderBookStatus.CLOSED);

        BookingServiceException e = assertThrows(BookingServiceException.class, () -> {
            testObject.closeOrderBook(orderBook.getInstrumentID());
        });

        assertThat(e.getMessage(), equalTo("service.error.orderBook.already.closed"));
    }

    @Test
    void addOrder_whenTypical() {
        OrderBook orderBook = getOrderBook();
        orderBook.setStatus(OrderBookStatus.OPEN);
        when(bookingPersistence.getOrderBook(any())).thenReturn(orderBook);
        when(bookingPersistence.getOrderBookStatus(any())).thenReturn(OrderBookStatus.OPEN);

        testObject.addOrder(new Order());

        verify(bookingPersistence, times(1)).addOrder(any());
    }

    @Test
    void addOrder_whenOrderBookIsClosed() {
        OrderBook orderBook = getOrderBook();
        orderBook.setStatus(OrderBookStatus.CLOSED);
        when(bookingPersistence.getOrderBook(any())).thenReturn(orderBook);
        when(bookingPersistence.getOrderBookStatus(any())).thenReturn(OrderBookStatus.CLOSED);

        BookingServiceException e = assertThrows(BookingServiceException.class, () -> {
            testObject.addOrder(new Order());
        });

        assertThat(e.getMessage(), equalTo("service.error.order.add.book.closed"));
    }

    @Test
    void addExecution_whenTypical() {
        OrderBook orderBook = getOrderBook();
        orderBook.setStatus(OrderBookStatus.CLOSED);
        when(bookingPersistence.getOrderBook(any())).thenReturn(orderBook);
        when(bookingPersistence.getOrderBookStatus(any())).thenReturn(OrderBookStatus.CLOSED);

        testObject.addExecution(new Execution());

        verify(bookingPersistence, times(1)).addExecution(any());
    }

    @Test
    void addExecution_whenOrderBookIsOpen() {
        OrderBook orderBook = getOrderBook();
        orderBook.setStatus(OrderBookStatus.OPEN);
        when(bookingPersistence.getOrderBook(any())).thenReturn(orderBook);
        when(bookingPersistence.getOrderBookStatus(any())).thenReturn(OrderBookStatus.OPEN);

        BookingServiceException e = assertThrows(BookingServiceException.class, () -> {
            testObject.addExecution(new Execution());
        });

        assertThat(e.getMessage(), equalTo("service.error.execution.add.open"));
    }

    private OrderBook getOrderBook() {
        OrderBook orderBook = new OrderBook();
        orderBook.setInstrumentID(ARBITRARY_INSTRUMENT_ID);
        orderBook.setInstrumentType(ARBITRARY_INSTRUMENT_TYPE);
        return orderBook;
    }
}
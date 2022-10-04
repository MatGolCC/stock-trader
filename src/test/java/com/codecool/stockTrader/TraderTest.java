package com.codecool.stockTrader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraderTest {
    @Mock(stubOnly = true)
    private Logger logger;
    @Mock
    private StockAPIService stockAPIService;
    @InjectMocks
    private Trader trader;

    @Test
    void buy_bidLowerThanPrice_returnFalse() throws IOException {
        String symbol = "aapl";
        double price = 100;
        double bid = 50;

        when(stockAPIService.getPrice(symbol)).thenReturn(price);

        boolean result = trader.buy(symbol, bid);
        verify(stockAPIService, times(1)).getPrice(eq(symbol));
        assertFalse(result);
    }

    @Test
    void buy_bidHigherThanPrice_returnTrue() {

    }

    @Test
    void buy_nonExistingSymbol_throwsIllegalArgumentException() throws IOException {
        double price = 50;
        when(stockAPIService.getPrice(anyString())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> trader.buy("non-existing-symbol", price));
    }
}

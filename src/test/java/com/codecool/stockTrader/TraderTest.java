package com.codecool.stockTrader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraderTest {

    @Mock
    private StockAPIService stockService;

    @Mock(stubOnly = true)
    private FileLogger logger;

    @InjectMocks
    private Trader trader;

    private void mockStockServiceGetPrice(String exampleSymbol, double examplePrice) throws IOException {
        when(stockService.getPrice(exampleSymbol)).thenReturn(examplePrice);
    }

    @Test
    void buy_bidLowerThanPrice_returnFalse() throws IOException {
        String exampleSymbol = "aapl";
        double exampleBid = 50;
        double examplePrice = 100;

        mockStockServiceGetPrice(exampleSymbol, examplePrice);
        boolean buyResult = trader.buy(exampleSymbol, exampleBid);
        assertFalse(buyResult);
    }

    @Test
    void buy_bidHigherThanPrice_returnTrue() throws IOException {
        String exampleSymbol = "aapl";
        double exampleBid = 100;
        double examplePrice = 50;

        mockStockServiceGetPrice(exampleSymbol, examplePrice);
        boolean buyResult = trader.buy(exampleSymbol, exampleBid);
        assertTrue(buyResult);
    }

    @Test
    void buy_nonExistingSymbol_throwsIllegalArgumentException() throws IOException {
        double examplePrice = 50;
        when(stockService.getPrice(anyString())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> trader.buy("non-existing-symbol", examplePrice));
    }

}

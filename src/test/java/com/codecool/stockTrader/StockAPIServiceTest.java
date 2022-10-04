package com.codecool.stockTrader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockAPIServiceTest {
    @Mock
    private RemoteURLReader urlReaderMock;

    @InjectMocks
    private StockAPIService stockAPIService;

    @Test
    void getPrice_existingSymbol_returnsPriceAsDouble() throws IOException {
        String response = "{\"symbol\":\"AAPL\",\"price\":169.98}";
        double expected = 169.98;

        when(urlReaderMock.readFromUrl(anyString())).thenReturn(response);

        double actual = stockAPIService.getPrice("aapl");

        assertEquals(expected, actual);
    }

    @Test
    void getPrice_nonExistingSymbol_throwsIllegalArgumentException() throws IOException {
        String response = "{\"symbol\":\"AAPL\",\"price\":169.98}";

        when(urlReaderMock.readFromUrl(anyString())).thenReturn(response);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                    stockAPIService.getPrice("non-existing-symbol");
                }
        );
        assertEquals("Symbol does not exist!", exception.getMessage());
    }

    @Test
    void getPrice_serverDown_throwsIOException() {

    }

    @Test
    void getPrice_malformedResponseFromAPI_throwsJSONException() {

    }

}

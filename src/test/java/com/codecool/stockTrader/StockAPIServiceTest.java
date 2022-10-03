package com.codecool.stockTrader;

import org.json.JSONException;
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
    private RemoteURLReader remoteURLReader;

    @InjectMocks
    private StockAPIService stockService;

    @Test
    void getPrice_existingSymbol_returnsPriceAsDouble() throws IOException {
        String exampleResponse = "{\"symbol\":\"AAPL\",\"price\":169.98}";
        double expectedPrice = 169.98;

        when(remoteURLReader.readFromUrl(anyString())).thenReturn(exampleResponse);
        double price = stockService.getPrice("aapl");

        assertEquals(expectedPrice, price);
    }

    @Test
    void getPrice_nonExistingSymbol_throwsIllegalArgumentException() throws IOException {
        String exampleResponse = "{\"symbol\":\"AAPL\",\"price\":169.98}";

        when(remoteURLReader.readFromUrl(anyString())).thenReturn(exampleResponse);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            stockService.getPrice("non-existing-symbol");
        });

        assertEquals("Symbol does not exist!", exception.getMessage());
    }

    @Test
    void getPrice_serverDown_throwsIOException() throws IOException {
        when(remoteURLReader.readFromUrl(anyString())).thenThrow(IOException.class);
        assertThrows(IOException.class, () -> stockService.getPrice("anything"));
    }

    @Test
    void getPrice_malformedResponseFromAPI_throwsJSONException() throws IOException {
        String exampleResponse = "\"symbol\":\"AAPL\"}}}}}}";

        when(remoteURLReader.readFromUrl(anyString())).thenReturn(exampleResponse);

        assertThrows(JSONException.class, () -> {
            stockService.getPrice("anything");
        });
    }

}

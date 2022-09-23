package com.codecool.stockTrader;

import org.junit.jupiter.api.Test;

class StockAPIServiceTest {

    @Test
    void getPrice_existingSymbol_returnsPriceAsDouble() {

    }

    @Test
    void getPrice_nonExistingSymbol_throwsIllegalArgumentException() {

    }

    @Test
    void getPrice_serverDown_throwsIOException() {

    }

    @Test
    void getPrice_malformedResponseFromAPI_throwsJSONException() {

    }

}
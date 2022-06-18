package com.example.edpprojekt2.currencyapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface CurrencyAPI {

    @GET("/latest")
    Call<CurrencyDTO> listCurrencies(@Query("base") String base, @Query(value = "symbols", encoded = true) String symbols);
}

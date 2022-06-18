package com.example.edpprojekt2.currencyapi;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyService {
    private OkHttpClient client;
    private static final List<String> CURRENCIES = List.of("PLN", "EUR", "USD", "GBP");
    Gson gson = new Gson();

    public CurrencyService() {
        this.client = new OkHttpClient();
    }

    public Map<String, String> getTranslatedBet(String bet) throws IOException {
        System.out.println(bet);
        List<String> splitted = Arrays.stream(bet.split(" ")).collect(Collectors.toList());
        String base = splitted.get(1);
        List<String> symbolsList = new ArrayList<>(CURRENCIES);
        symbolsList.remove(base);

        String symbols = String.join(",", symbolsList);
        System.out.println(symbols);

        Response response = getCurrencies();
        String json = response.body().string();
        CurrencyDTO currencyDTO = gson.fromJson(json, CurrencyDTO.class);
        return prepareResult(currencyDTO, splitted.get(0), symbolsList);
    }

    private Map<String, String> prepareResult(CurrencyDTO response, String usersBet, List<String> symbols) {
        Map<String, String> result = new HashMap<>();
        Float bet = Float.parseFloat(usersBet);
        for (String symb : symbols) {
            Float curr = response.getRates().get(symb);
            Float calc = curr * bet;
            result.put(symb, calc.toString());
        }

        return result;
    }

    private String getUrl(String path) {
        return "https://api.apilayer.com/exchangerates_data" + path;
    }

    private Response getCurrencies() throws IOException {
        Request request = new Request.Builder()
                .url(getUrl("/latest?base=PLN&symbols=GBP,USD,EUR"))
                .header("apikey", "QHjqeOwYFrG7I9BcLpx0MIv2N5PVIZ41")
                .get()
                .build();
        return client.newCall(request).execute();
    }

}

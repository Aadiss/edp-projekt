package com.example.edpprojekt2.couriermail;


import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class CourierMail {
    private static final String SEND_URL = "X";
    private static final String TEMPLATE_ID = "X";
    private static final String BEARER = "X";

    public static boolean sendMail(String toEmail, String recipientName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String json = prepareRequestBody(toEmail, recipientName);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(SEND_URL)
                .header("Authorization", BEARER)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();

        return response.isSuccessful();
    }

    private static String prepareRequestBody(String toEmail, String recipientName) {
        MailBodyDTO mailBodyDTO = new MailBodyDTO(
                new MessageDTO(
                        TEMPLATE_ID,
                        new ToDTO(
                                toEmail
                        ),
                        new DataDTO(
                                recipientName
                        )
                )
        );

        return new Gson().toJson(mailBodyDTO);
    }
}

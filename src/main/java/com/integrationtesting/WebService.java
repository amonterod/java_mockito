package com.integrationtesting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {

    public StringBuffer get(String webserviceUrl, String token, String id) {
        HttpURLConnection conn = null;
        URL url;
        BufferedReader in = null;
        String output = null;
        StringBuffer response = new StringBuffer();
        try {
            // Sending get request
            url = new URL(webserviceUrl + id);
            conn = (HttpURLConnection) url.openConnection();
            System.out.println("Connection established to : " + webserviceUrl + id);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");
            System.out.println("Method : GET");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            in.close();
        } catch (Exception e) {
            System.out.println("EXCEPTION:" + e.toString());
        } finally {
            conn.disconnect();
            System.out.println("Connection released!");
        }
        return response;
    }
    
    public StringBuffer post(String webserviceUrl, String token, String name, String gender, String email) {
        HttpURLConnection conn = null;
        URL url;
        BufferedReader in = null;
        String output = null;
        StringBuffer response = new StringBuffer();
        try {
            // Sending get request
            url = new URL(webserviceUrl);
            conn = (HttpURLConnection) url.openConnection();
            System.out.println("Connection established to : " + webserviceUrl);
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            System.out.println("Method : POST");

            String jsonMessage = "{\"name\":\"" + name + "\", \"gender\": \"" + gender + "\", \"email\": \"" + email
                    + "\", \"status\":\"Active\"}";

            System.out.println("new user :" + jsonMessage);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonMessage.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            in.close();
        } catch (Exception e) {
            System.out.println("EXCEPTION:" + e.toString());
        } finally {
            conn.disconnect();
            System.out.println("Connection released!");
        }
        return response;
    }

    public StringBuffer delete(String webserviceUrl, String token, String id) {
        HttpURLConnection conn = null;
        URL url;
        BufferedReader in = null;
        String output = null;
        StringBuffer response = new StringBuffer();
        try {
            // Sending get request
            url = new URL(webserviceUrl + id);
            conn = (HttpURLConnection) url.openConnection();
            System.out.println("Connection established to : " + webserviceUrl + id);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("DELETE");
            System.out.println("Method : DELETE");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            in.close();
        } catch (Exception e) {
            System.out.println("EXCEPTION:" + e.toString());
        } finally {
            conn.disconnect();
            System.out.println("Connection released!");
        }
        return response;
    }
}

package com.integrationtesting.old;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

import org.json.JSONObject;

public class RequestHandlerOld {

    HttpURLConnection conn = null;

    // Check this URL : https://gorest.co.in/
    public static String webserviceUrl = "https://gorest.co.in/public-api/users/";
    // Visit this URL to get the daily token: https://gorest.co.in/access-token
    public static String token = "c07356fc077894f803eebe782b8d863a9cbd4b52d65375f875d186f78b49cd41";

    public RequestHandlerOld() {
        
    }

    public String requestWithId(boolean isGet, boolean isDelete, String id) {
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
            if (isGet) {
                conn.setRequestMethod("GET");
                System.out.println("Method : GET");
            } else {
                if (isDelete) {
                    conn.setRequestMethod("DELETE");
                    System.out.println("Method : DELETE");
                }
            }

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            System.out.println(response.toString());

            in.close();
        } catch (Exception e) {
            System.out.println("EXCEPTION:" + e.toString());
        } finally {
            conn.disconnect();
            System.out.println("Connection released!");
        }
        return response.toString();
    }

    public int postRequest(String name, String gender, String email) {
        
        URL url;
        BufferedReader in = null;
        String output = null;
        StringBuffer response = new StringBuffer();
        int id = -1;
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

            JSONObject json = new JSONObject(response.toString()); // Convert text to object
            System.out.println("Response :");
            System.out.println(json.toString(4)); // Print it with specified indentation
            id = json.getJSONObject("data").getInt("id"); //Get the id element of json

            in.close();
        } catch (Exception e) {
            System.out.println("EXCEPTION:" + e.toString());
        } finally {
            conn.disconnect();
            System.out.println("Connection released!");
        }
        return id;
    }

    public static void main(String[] args) {
        RequestHandlerOld req = new RequestHandlerOld();
        // POST request
        int newObjectId = req.postRequest("josemanuel", "Male", "josemanuel@hotmail.com");
        // DELETE request
        req.requestWithId(false, true, ""+newObjectId);
        // GET request
        req.requestWithId(true, false, ""+newObjectId);
    }
}

package com.integrationtesting;

import java.net.SocketException;

import org.json.JSONObject;

public class RequestHandler {

    private WebService webservice;

    // Check this URL : https://gorest.co.in/
    public static String webserviceUrl = "https://gorest.co.in/public-api/users/";
    // Visit this URL to get the daily token: https://gorest.co.in/access-token
    public static String token = "c07356fc077894f803eebe782b8d863a9cbd4b52d65375f875d186f78b49cd41";

    public RequestHandler(WebService webserv) {
        webservice = webserv;
    }

    public String requestWithId(boolean isGet, boolean isDelete, String id) {
        StringBuffer response = new StringBuffer();
        JSONObject json = null;
        try {
            if (isGet) {
                response = webservice.get(webserviceUrl, token, id);
            } else {
                response = webservice.delete(webserviceUrl, token, id);
            }

            // printing result from response
            json = new JSONObject(response.toString()); // Convert text to object
            System.out.println("Response for user '" + id + "' is:");
            System.out.println(json.toString(4)); // Print it with specified indentation
        } catch (Exception e) {
            System.out.println("EXCEPTION:" + e.toString());
            json = new JSONObject("{\"error\": \"" + e.toString() + "\"}");
        }
        return json.toString(4);
    }

    public int postRequest(String name, String gender, String email) {
        int id = -1;
        StringBuffer response = new StringBuffer();
        try {
            response = webservice.post(webserviceUrl, token, name, gender, email);

            // printing result from response
            JSONObject json = new JSONObject(response.toString()); // Convert text to object
            System.out.println("Response :");
            System.out.println(json.toString(4)); // Print it with specified indentation
            id = json.getJSONObject("data").getInt("id"); //Get the id element of json
        } catch (Exception e) {
            System.out.println("EXCEPTION:" + e.toString());
        }
        return id;
    }

    public static void main(String[] args) {
        WebService webserv = new WebService();
        RequestHandler req = new RequestHandler(webserv);
        // POST request
        int newObjectId = req.postRequest("josemanuel", "Male", "josemanuel@hotmail.com");
        // DELETE request
        req.requestWithId(false, true, ""+newObjectId);
        // GET request
        req.requestWithId(true, false, ""+newObjectId);
    }
}

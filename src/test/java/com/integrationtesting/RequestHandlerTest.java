package com.integrationtesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.SocketException;

import javax.management.RuntimeErrorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestHandlerTest {

    @Mock
    WebService webserviceMock;

    @Test
    public void givenGetThenResultOK() {
        assertNotNull(webserviceMock);
        StringBuffer mockResponse = new StringBuffer("{\"message\": \"Hello World\"}");
        Mockito.when(webserviceMock.get(RequestHandler.webserviceUrl, RequestHandler.token, "111")).thenReturn(mockResponse);

        RequestHandler req = new RequestHandler(webserviceMock);
        String result = req.requestWithId(true, false, "111");
        assertEquals(mockResponse.toString(), result);
    }
    
    @Test
    public void givenGetThenSocketException() {
        assertNotNull(webserviceMock);
        Mockito.when(webserviceMock.get(RequestHandler.webserviceUrl, RequestHandler.token, "112")).
            thenThrow(SocketException.class);
        RequestHandler req = new RequestHandler(webserviceMock);
        String result = req.requestWithId(true, false, "112");
        assertEquals("{\"error\": \"java.net.SocketException\"}", result);
    }
    
    @Test
    public void givenPostThenResultKO() {
        assertNotNull(webserviceMock);
        StringBuffer mockResponse = new StringBuffer("{\"message\": \"Hello World\"}");
        Mockito.when(webserviceMock.post(RequestHandler.webserviceUrl, RequestHandler.token, "Pepe", "Male", "pepe@hotmail.com")).
            thenReturn(mockResponse);

        RequestHandler req = new RequestHandler(webserviceMock);
        int result = req.postRequest("Pepe", "Male", "pepe@hotmail.com");
        assertEquals(-1, result);
    }

    @Test
    public void givenPostThenResultOK() {
        assertNotNull(webserviceMock);
        StringBuffer mockResponse = new StringBuffer("{\"data\": {\"id\": \"1234\"}}");
        Mockito.when(webserviceMock.post(RequestHandler.webserviceUrl, RequestHandler.token, "Pepe", "Male", "pepe@hotmail.com")).
            thenReturn(mockResponse);

        RequestHandler req = new RequestHandler(webserviceMock);
        int result = req.postRequest("Pepe", "Male", "pepe@hotmail.com");
        assertEquals(1234, result);
    }

    @Test
    public void givenPostThenRuntimeException() {
        assertNotNull(webserviceMock);
        Mockito.when(webserviceMock.post(RequestHandler.webserviceUrl, RequestHandler.token, "Pepe", "Masculino", "pepe@hotmail.com")).
            thenThrow(new RuntimeErrorException(new Error(),"Exception"));

        RequestHandler req = new RequestHandler(webserviceMock);
        int result = req.postRequest("Pepe", "Masculino", "pepe@hotmail.com");
        assertEquals(-1, result);
    }

    @Test
    public void givenPostThenSocketTimeoutException() {
        assertNotNull(webserviceMock);
        Mockito.when(webserviceMock.post(RequestHandler.webserviceUrl, RequestHandler.token, "Pepe", "Male", "pepe@hotmail.com")).
            thenThrow(SocketException.class);

        RequestHandler req = new RequestHandler(webserviceMock);
        int result = req.postRequest("Pepe", "Male", "pepe@hotmail.com");
        assertEquals(-1, result);
    }

    @Test
    public void givenPostThenNullPointerException() {
        assertNotNull(webserviceMock);
        Mockito.when(webserviceMock.post(RequestHandler.webserviceUrl, RequestHandler.token, "Jose", "Masculino", "jose@hotmail.com")).
            thenThrow(NullPointerException.class);

        RequestHandler req = new RequestHandler(webserviceMock);
        int result = req.postRequest("Jose", "Masculino", "jose@hotmail.com");
        assertEquals(-1, result);
    }


    @Test
    public void givenPostThenIndexOutOfBoundsException() {
        assertNotNull(webserviceMock);
        Mockito.when(webserviceMock.post(RequestHandler.webserviceUrl, RequestHandler.token, "Jose", "Masculino", "jose@hotmail.com")).
            thenThrow(IndexOutOfBoundsException.class);

        RequestHandler req = new RequestHandler(webserviceMock);
        int result = req.postRequest("Jose", "Masculino", "jose@hotmail.com");
        assertEquals(-1, result);
    }


    
}

package com.integrationtesting.old;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestHandlerTestOld {

    @Test
    public void givenGetThenResultOK() {
        RequestHandlerOld req = new RequestHandlerOld();
        String result = req.requestWithId(true, false, "111");
        assertTrue(result.contains("\"code\":404"));
    }
    
    @Test
    public void givenPostThenResultOK() {
        RequestHandlerOld req = new RequestHandlerOld();
        int result = req.postRequest("Pepe", "Male", "pepe@hotmail.com");
        assertNotEquals(-1, result);
    }

    @Test
    public void givenPostThenResultKO() {
        RequestHandlerOld req = new RequestHandlerOld();
        // Post request using same post of previous test to create a duplicate user
        int result = req.postRequest("Pepe", "Male", "pepe@hotmail.com");
        assertEquals(-1, result);
    }

    @Test
    public void givenPostThenRuntimeException() {
        RequestHandlerOld req = new RequestHandlerOld();
        int result = req.postRequest("Pepe", "Masculino", "pepe@hotmail.com");
        assertEquals(-1, result);
    }
    
    // @Test
    // public void givenGetThenSocketException() {
    //
    // }

    // @Test
    // public void givenPostThenSocketTimeoutException() {
        
    // }

    // @Test
    // public void givenPostThenNullPointerException() {

    // }
    
}

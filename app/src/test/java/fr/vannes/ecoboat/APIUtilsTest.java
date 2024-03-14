package fr.vannes.ecoboat;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import fr.vannes.ecoboat.utils.APIUtils;

public class APIUtilsTest {
    @Test
    public void testGetTokenWithMock() {
        // Create a mock APIUtils object
        APIUtils apiUtils = mock(APIUtils.class);

        // Define the behavior of getToken method
        try {
            when(apiUtils.getToken()).thenReturn("mockToken");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call the getToken method
        try {
            String token = apiUtils.getToken();
            assertNotNull("Token should not be null", token);
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }
        @Test
        public void testGetToken() {
            APIUtils apiUtils = new APIUtils();
            try {
                String token = apiUtils.getToken();
                assertNotNull("Token should not be null", token);
            } catch (IOException e) {
                fail("Exception should not be thrown");

            }
        }

        @Test
        public void testGetTemperature(){
            APIUtils apiUtils = new APIUtils();
            try {
                List<Map<String, String>> temperature = apiUtils.getTemperature();
                for (Map<String, String> temp : temperature) {
                    assertNotNull("Temperature should not be null", temp);
                    System.out.println(temp);
                }

                assertNotNull("Temperature should not be null", temperature);
            } catch (IOException e) {
                fail("Exception should not be thrown");
            }
        }
    }

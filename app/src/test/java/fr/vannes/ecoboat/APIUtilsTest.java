package fr.vannes.ecoboat;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

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
            System.out.println("Token: " + token);
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
                System.out.println("Token: " + token);
                assertNotNull("Token should not be null", token);
            } catch (IOException e) {
                fail("Exception should not be thrown");

            }
        }
    }

package com.computer.socialcampus;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.computer.socialcampus.ui.login.LoggedInUserView;
import com.computer.socialcampus.ui.login.LoginResult;

public class LoginResultTest {
    private LoginResult loginResult;

    @Before
    public void setUp() {
        // Örnek bir başarılı sonuç oluştur
        LoggedInUserView success = new LoggedInUserView("123", "John Doe");
        loginResult = new LoginResult(success);
    }

    @Test
    public void testGetSuccess() {
        assertNotNull(loginResult.getSuccess());
        assertEquals("123", loginResult.getSuccess().getUserId());
        assertEquals("John Doe", loginResult.getSuccess().getDisplayName());
    }

    @Test
    public void testGetError() {
        assertNull(loginResult.getError());

        // Hata durumu oluştur
        loginResult = new LoginResult(404);
        assertEquals(404, (int) loginResult.getError());
    }
}

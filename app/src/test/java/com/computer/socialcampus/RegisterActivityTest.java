package com.computer.socialcampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.computer.socialcampus.R;
import com.computer.socialcampus.data.model.LoggedInUser;
import com.computer.socialcampus.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Mock
    private FirebaseAuth mAuthMock;

    @Mock
    private DatabaseReference usersDatabaseRefMock;

    @Mock
    private Intent intentMock;

    @Mock
    private Bundle bundleMock;

    @Mock
    private OnCompleteListener<AuthResult> onCompleteListenerMock;

    private RegisterActivity registerActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class);
        scenario.onActivity(activity -> registerActivity = activity);
        registerActivity.mAuth = mAuthMock;
        registerActivity.usersDatabaseRef = usersDatabaseRefMock;
    }

    @Test
    public void testRegisterButtonClick_Success() {
        when(mAuthMock.getCurrentUser()).thenReturn(mock(FirebaseUser.class));
        when(mAuthMock.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mock(Task.class));
        when(usersDatabaseRefMock.child(anyString())).thenReturn(usersDatabaseRefMock);

        registerActivity.firstNameEditText.setText("John");
        registerActivity.lastNameEditText.setText("Doe");
        registerActivity.usernameEditText.setText("johndoe");
        registerActivity.emailEditText.setText("john.doe@example.com");
        registerActivity.passwordEditText.setText("password");
        registerActivity.collageEditText.setText("Computer Science");

        registerActivity.binding.register.performClick();

        verify(usersDatabaseRefMock).child(anyString()).setValue(any(LoggedInUser.class));
        verify(registerActivity).startActivity(intentMock);
        verify(registerActivity).finish();
    }

    @Test
    public void testRegisterButtonClick_Failure() {
        when(mAuthMock.getCurrentUser()).thenReturn(null);
        when(mAuthMock.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mock(Task.class));

        registerActivity.firstNameEditText.setText("John");
        registerActivity.lastNameEditText.setText("Doe");
        registerActivity.usernameEditText.setText("johndoe");
        registerActivity.emailEditText.setText("john.doe@example.com");
        registerActivity.passwordEditText.setText("password");
        registerActivity.collageEditText.setText("Computer Science");

        registerActivity.binding.register.performClick();

        verify(registerActivity).showToast("Kayıt Başarısız! Error message here");
    }

    @Test
    public void testAcceptTermsCheckBox_Checked() {
        CheckBox acceptTermsCheckBox = mock(CheckBox.class);
        when(acceptTermsCheckBox.isChecked()).thenReturn(true);
        registerActivity.acceptTermsCheckBox = acceptTermsCheckBox;

        registerActivity.acceptTermsCheckBoxListener.onCheckedChanged(acceptTermsCheckBox, true);

        verify(registerActivity).startActivity(intentMock);
    }

    @Test
    public void testAcceptTermsCheckBox_Unchecked() {
        CheckBox acceptTermsCheckBox = mock(CheckBox.class);
        when(acceptTermsCheckBox.isChecked()).thenReturn(false);
        registerActivity.acceptTermsCheckBox = acceptTermsCheckBox;

        registerActivity.acceptTermsCheckBoxListener.onCheckedChanged(acceptTermsCheckBox, false);

        verify(registerActivity).showToast("Lütfen kaydolmak için kullanım koşullarını kabul edin.");
    }
}

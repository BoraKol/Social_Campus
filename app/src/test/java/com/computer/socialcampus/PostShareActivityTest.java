package com.computer.socialcampus;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.computer.socialcampus.R;
import com.computer.socialcampus.ui.postShare.PostShareActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class PostShareActivityTest {


    @Mock
    private Intent intentMock;

    @Mock
    private Bundle bundleMock;

    @Mock
    private OnCompleteListener<Uri> onCompleteListenerMock;

    private PostShareActivity postShareActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ActivityScenario<PostShareActivity> scenario = ActivityScenario.launch(PostShareActivity.class);
        scenario.onActivity(activity -> postShareActivity = activity);
    }

    @Test
    public void testSelectImage() {
        ImageView ivImagePreview = postShareActivity.findViewById(R.id.ivImagePreview);
        ivImagePreview.setImageURI(null); // Clear the image preview

        when(intentMock.setAction(Intent.ACTION_PICK)).thenReturn(intentMock);
        when(intentMock.setType("image/*")).thenReturn(intentMock);
        when(postShareActivity.startActivityForResult(intentMock, 100)).thenReturn(null);

        postShareActivity.selectImage();

        verify(postShareActivity).startActivityForResult(intentMock, 100);
    }

    @Test
    public void testOnActivityResult() {
        Uri imageUri = mock(Uri.class);
        Intent dataMock = mock(Intent.class);
        when(dataMock.getData()).thenReturn(imageUri);

        postShareActivity.onActivityResult(100, RESULT_OK, dataMock);

        ImageView ivImagePreview = postShareActivity.findViewById(R.id.ivImagePreview);
        verify(ivImagePreview).setImageURI(imageUri);
    }

    @Test
    public void testUploadImageToFirebaseStorage() {
        Uri imageUri = mock(Uri.class);
        UploadTask uploadTaskMock = mock(UploadTask.class);
        when(uploadTaskMock.continueWithTask(any())).thenReturn(uploadTaskMock);

        postShareActivity.uploadImageToFirebaseStorage(imageUri, onCompleteListenerMock);

        verify(uploadTaskMock).continueWithTask(any());
    }
}

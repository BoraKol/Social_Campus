package com.computer.socialcampus.group;

import android.net.Uri;

import com.computer.socialcampus.ui.postShare.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.computer.socialcampus.ui.postShare.Comment;

import java.util.List;
public class GroupManager {
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    public GroupManager() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    // Grup oluşturma metodu
    public void createGroup(String groupName, String groupDescription, List<String> groupMembers) {
        String groupId = db.collection("groups").document().getId();
        Group group = new Group(groupId, groupName, groupDescription, groupMembers);

        db.collection("groups").document(groupId).set(group)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Başarıyla grup oluşturuldu
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Grup oluşturulurken hata oluştu
                    }
                });
    }

    // Gönderi oluşturma metodu
    public void createPost(String groupId, Post post) {
        if (post.getImageUri() != null && post.getVideoUri() == null) {
            uploadMedia(post.getImageUri(), "images", new OnMediaUploadListener() {
                @Override
                public void onUploadSuccess(String downloadUrl) {
                    post.setImageUri(downloadUrl);
                    savePost(groupId, post);
                }

                @Override
                public void onUploadFailed(Exception e) {
                    // Hata durumunda işlem
                }
            });
        } else if (post.getImageUri() == null && post.getVideoUri() != null) {
            uploadMedia(post.getVideoUri(), "videos", new OnMediaUploadListener() {
                @Override
                public void onUploadSuccess(String downloadUrl) {
                    post.setVideoUri(downloadUrl);
                    savePost(groupId, post);
                }

                @Override
                public void onUploadFailed(Exception e) {
                    // Hata durumunda işlem
                }
            });
        } else {
            savePost(groupId, post);
        }
    }
    private void savePost(String groupId, Post post) {
        if (groupId == null) {
            db.collection("posts").add(post);
        } else {
            db.collection("groups").document(groupId).collection("posts").add(post);
        }
    }

    private void uploadMedia(String mediaUri, String mediaType, OnMediaUploadListener listener) {
        Uri fileUri = Uri.parse(mediaUri);
        StorageReference storageRef = storage.getReference().child(mediaType).child(fileUri.getLastPathSegment());
        storageRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    if (mediaType.equals("images")) {
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> listener.onUploadSuccess(uri.toString()));
                    } else if (mediaType.equals("videos")) {
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> listener.onUploadSuccess(uri.toString()));
                    }
                })
                .addOnFailureListener(listener::onUploadFailed);
    }

    public interface OnMediaUploadListener {
        void onUploadSuccess(String downloadUrl);
        void onUploadFailed(Exception e);
    }

    // Grupları listeleme metodu
    public void getGroups(final OnGroupsRetrievedListener listener) {
        db.collection("groups").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Group group = document.toObject(Group.class);
                                listener.onGroupRetrieved(group);
                            }
                        } else {
                            listener.onError(task.getException());
                        }
                    }
                });
    }

    // Gönderileri listeleme metodu
    public void getPosts(String groupId, final OnPostsRetrievedListener listener) {
        db.collection("posts").whereEqualTo("groupId", groupId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Post post = document.toObject(Post.class);
                                listener.onPostRetrieved(post);
                            }
                        } else {
                            listener.onError(task.getException());
                        }
                    }
                });
    }

    public void getAllPosts(final OnPostsRetrievedListener listener) {
        db.collection("posts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Post post = document.toObject(Post.class);
                                listener.onPostRetrieved(post);
                            }
                        } else {
                            listener.onError(task.getException());
                        }
                    }
                });
    }

    public void addComment(String postId, String commentText, String username) {
        Comment comment = new Comment(commentText, "Kullanıcı 1"); // Kullanıcı adı örnek olarak verilmiştir
        db.collection("posts").document(postId).collection("comments").add(comment)
                .addOnSuccessListener(documentReference -> {
                    // Yorum başarıyla eklendi
                })
                .addOnFailureListener(e -> {
                    // Yorum ekleme hatası
                });
    }

    public void addLike(String postId) {
        db.collection("posts").document(postId).update("likes", FieldValue.increment(1))
                .addOnSuccessListener(aVoid -> {
                    // Beğeni başarıyla eklendi
                })
                .addOnFailureListener(e -> {
                    // Beğeni ekleme hatası
                });
    }



    // Gruplar çekildiğinde çağrılacak listener arayüzü
    public interface OnGroupsRetrievedListener {
        void onGroupRetrieved(Group group);
        void onError(Exception e);
    }

    // Gönderiler çekildiğinde çağrılacak listener arayüzü
    public interface OnPostsRetrievedListener {
        void onPostRetrieved(Post post);
        void onError(Exception e);
    }
}

package com.geektach.newsapp42.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geektach.newsapp42.Prefs;
import com.geektach.newsapp42.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Prefs prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireStoreDelete();
            }
        });
        binding.tvProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireStoreInfo();
            }
        });
        prefs = new Prefs(requireContext());
        binding.imageView.setOnClickListener(view1 -> mGetContext.launch("image/*"));
        Prefs prefs = new Prefs(requireContext());
        binding.etName.setText(prefs.isEditText());
        if (prefs.isImageView() != null) {
            Glide.with(binding.imageView)
                    .load(prefs.isImageView())
                    .circleCrop()
                    .into(binding.imageView);
        }
    }

    private void fireStoreDelete() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();
        StorageReference ref = reference.child("avatar.jpg");
        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(requireContext(), "is success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fireStoreInfo() {
        binding.tvProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = binding.etName.getText().toString().trim();
                String getImage = binding.imageView.toString().trim();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", getName);
                hashMap.put("image", getImage);
                FirebaseFirestore.getInstance()
                        .collection("User")
                        .document("UserData")
                        .set(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(requireContext(), "saved", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(), "failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    ActivityResultLauncher<String> mGetContext = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    Prefs prefs = new Prefs(requireContext());
                    if (result != null) {
                        Glide.with(binding.imageView)
                                .load(result)
                                .circleCrop()
                                .into(binding.imageView);
                        prefs.saveImageView(String.valueOf(result));
                        upload(result);
                    }
                }
            });

    private void upload(Uri result) {
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseStorage
                .getInstance()
                .getReference()
                .child(userId + ".jpg")
                .putFile(result)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "uploaded", Toast.LENGTH_SHORT).show();
                            Log.e("Profile", "Uploaded");
                        } else {
                            Toast.makeText(requireContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onPause() {
        prefs.saveEditText(binding.etName.getText().toString());
        super.onPause();
    }
}
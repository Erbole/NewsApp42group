package com.geektach.newsapp42.News;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektach.newsapp42.App;
import com.geektach.newsapp42.R;
import com.geektach.newsapp42.databinding.FragmentNewsBinding;
import com.geektach.newsapp42.models.Article;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewsFragment extends Fragment {

    private ProgressDialog progressDialog;
    private FragmentNewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Ждите");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        Bundle bundle = new Bundle();
        String text = binding.editText.getText().toString().trim();
        if (text.isEmpty()) {
            boolean invalid = true;
            if (invalid) {
                Animation snake = AnimationUtils.loadAnimation(requireContext(), R.anim.snake);
                binding.editText.startAnimation(snake);
            }
            Toast.makeText(requireContext(), "Введите новость", Toast.LENGTH_SHORT).show();
            return;
        }
        Article article = new Article(text, System.currentTimeMillis());
        saveToFireStore(article);

        App.getDataBase().articleDao().insert(article);
        bundle.putSerializable("article", article);
        getParentFragmentManager().setFragmentResult("rk_news", bundle);
    }

    private void saveToFireStore(Article article) {
        progressDialog.setMessage("Добавление");
        progressDialog.show();
        FirebaseFirestore.getInstance()
                .collection("news")
                .add(article)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                            close();
                        } else {
                            Toast.makeText(requireContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}
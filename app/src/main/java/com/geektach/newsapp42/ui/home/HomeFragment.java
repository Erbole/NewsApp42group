package com.geektach.newsapp42.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektach.newsapp42.App;
import com.geektach.newsapp42.News.NewsAdapter;
import com.geektach.newsapp42.OnItemClickListener;
import com.geektach.newsapp42.R;
import com.geektach.newsapp42.databinding.FragmentHomeBinding;
import com.geektach.newsapp42.models.Article;

import java.util.List;

public class HomeFragment extends Fragment {

    private List<Article> list;
    private FragmentHomeBinding binding;
    private NewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
        initListener();
        initTextListener();
        getItemNews();
    }

    private void setupAdapter() {
        adapter = new NewsAdapter();
        binding.recyclerView.setAdapter(adapter);
        adapter.addItems(App.getDataBase().articleDao().getAll());
    }

    private void getItemNews() {
        getParentFragmentManager().setFragmentResultListener(
                "rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        adapter.addItems(App.getDataBase().articleDao().getAll());
                    }
                });
    }

    private void initTextListener() {
        binding.Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                list = App.getDataBase().articleDao().getSearch(editable.toString());
                adapter.addItems(list);
            }
        });

    }

    private void initListener() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Article article = adapter.getItem(position);
                Toast.makeText(requireContext(), article.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
    }

    private void openFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.newsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
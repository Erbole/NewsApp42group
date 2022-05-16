package com.geektach.newsapp42.ui.Board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.geektach.newsapp42.Prefs;
import com.geektach.newsapp42.R;
import com.geektach.newsapp42.databinding.FragmentBoardBinding;
import com.geektach.newsapp42.ui.Board.BoardAdapter;

public class BoardFragment extends Fragment {

    private FragmentBoardBinding binding;
    private Button btnStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BoardAdapter adapter = new BoardAdapter();
        binding.viewPager.setAdapter(adapter);
        binding.dotsIndicator.setViewPager2(binding.viewPager);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    binding.textSkip.setVisibility(View.INVISIBLE);
                } else {
                    binding.textSkip.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                }
            }
        });
    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.SaveState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}
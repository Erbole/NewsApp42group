package com.geektach.newsapp42;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AvatarProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setSharedElementEnterTransition(new ChangeBounds());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avatar_profile, container, false);
    }
}
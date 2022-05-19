package com.ridemgmtsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ridemgmtsystem.R;

public class AnimFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  TextView textView;


       //   textView =(TextView)findViewById(R.id.textView);

     //   Animation flowAnim=;

        ViewGroup rootview =(ViewGroup) inflater.inflate(R.layout.fragmentanim,container,false);

        return rootview;
    }
}

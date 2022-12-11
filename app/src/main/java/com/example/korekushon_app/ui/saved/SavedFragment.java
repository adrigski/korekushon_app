package com.example.korekushon_app.ui.saved;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.korekushon_app.databinding.FragmentSavedBinding;
import com.example.korekushon_app.ui.browse.ProductView;

import java.net.URISyntaxException;

public class SavedFragment extends Fragment {

    private FragmentSavedBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSavedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        System.out.println("listviewTitle");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}


}

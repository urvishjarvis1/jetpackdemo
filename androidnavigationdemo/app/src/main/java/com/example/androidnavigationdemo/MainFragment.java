package com.example.androidnavigationdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button navButton = view.findViewById(R.id.button_frag1);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) view.findViewById(R.id.username)).getText().toString();
                String pass = ((EditText) view.findViewById(R.id.password)).getText().toString();
                if (!username.isEmpty()&& !pass.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("testObj", "Hello world!!!");
                    bundle.putString("username",username);
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    if (((CheckBox) view.findViewById(R.id.checkbox)).isChecked())
                        navController.navigate(R.id.action_mainFragment_to_secondFragment, bundle);
                    else navController.navigate(R.id.action_mainFragment_to_userFragment, bundle);
                } else {
                    if (username.isEmpty()) {
                        ((EditText) view.findViewById(R.id.username)).setError("Please enter username");
                    } else {
                        ((EditText) view.findViewById(R.id.password)).setError("Please enter password");
                    }
                }
            }
        });
    }
}

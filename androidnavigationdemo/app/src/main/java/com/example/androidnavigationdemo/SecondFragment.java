package com.example.androidnavigationdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;


public class SecondFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), MainFragmentArgs.fromBundle(getArguments()).getTest(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), getArguments().getString("testObj", "default"), Toast.LENGTH_SHORT).show();
        final Button navButton = view.findViewById(R.id.button_frag2);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = ((EditText) view.findViewById(R.id.otp)).getText().toString();
                if (!otp.isEmpty()) {
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    Bundle bundle=new Bundle();
                    bundle.putString("username",getArguments().getString("username"));
                    navController.navigate(R.id.action_secondFragment_to_userFragment,bundle);
                    navController.addOnNavigatedListener(new NavController.OnNavigatedListener() {
                        @Override
                        public void onNavigated(@NonNull NavController controller, @NonNull NavDestination destination) {
                            Log.d("TAG", "onNavigated: " + destination.getLabel());
                        }
                    });
                }else{
                    ((EditText) view.findViewById(R.id.otp)).setError("Enter otp");
                }
            }
        });
    }
}

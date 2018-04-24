package bleizing.riva.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bleizing.riva.R;
import bleizing.riva.activity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private EditText editUsername;
    private EditText editPassword;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
        editUsername = (EditText) getActivity().findViewById(R.id.editUsername);
        editPassword = (EditText) getActivity().findViewById(R.id.editPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                if (!username.equals("") && !password.equals("")) {
                    prosesLogin(username, password);
                } else {
                    Toast.makeText(getContext(), "Masukkan Username dan Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void prosesLogin(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "Username Atau Password Salah!", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.bca.notiz;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        TextInputEditText etEmail = findViewById(R.id.etEmail);
        btnLogin.setOnClickListener(v -> {
            // Show demo activity using explicit intent.
            // Also pass email data to demo activity.
            String email = "";
            if (etEmail.getText() != null)
                email = etEmail.getText().toString();
            Intent intent = new Intent(LoginActivity.this, DemoActivity.class);
            intent.putExtra("user_email", email);
            startActivity(intent);
            finish();
        });
    }

}
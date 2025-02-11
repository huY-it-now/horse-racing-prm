package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.horse_racing_client.DAO.UserDAO;
import com.theanimegroup.horse_racing_client.R;
import com.theanimegroup.horse_racing_client.entity.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsername, txtPassword;
    UserDAO userDAO = UserDAO.getInstance();
    ImageView gifBackground;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_layout);

        RefElement();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

//        Glide.with(this)
//                .asGif()
//                .load(R.drawable.game_background)
//                .into(gifBackground);

        mediaPlayer = MediaPlayer.create(this, R.raw.doodle);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    protected void RefElement(){
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
//        gifBackground = (ImageView)findViewById(R.id.gifBackground);
    }

    // Function to handle login
    protected void performLogin() {
        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = userDAO.Login(username, password);

        if (user != null) {
            // Credentials are correct, navigate to MainActivity
            Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(myIntent);
        } else {
            // Invalid credentials, show error message
            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            //mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}

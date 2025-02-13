package com.theanimegroup.horse_racing_client.controller;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.theanimegroup.horse_racing_client.R;

public class ResultActivity extends AppCompatActivity {

    private TextView winnerTextView, winningsTextView, backTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        winnerTextView = findViewById(R.id.tvWinner);
        winningsTextView = findViewById(R.id.tvWinnings);
        backTextView = findViewById(R.id.tvBack);

        double betAmount = getIntent().getDoubleExtra("betAmount", 0);
        int selectedHorse = getIntent().getIntExtra("selectedHorse", 1);
        int winningHorse = getIntent().getIntExtra("winningHorse", -1);
        boolean userWon = getIntent().getBooleanExtra("won", false);

        winnerTextView.setText("Winner: Horse " + winningHorse);

        if (userWon) {
            double winnings = betAmount * 2;
            winningsTextView.setText("You won: $" + winnings);
        } else {
            winningsTextView.setText("You lost: $" + betAmount);
        }

        backTextView.setOnClickListener(v -> finish());
    }
}

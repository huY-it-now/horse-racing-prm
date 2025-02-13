package com.theanimegroup.horse_racing_client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.horse_racing_client.R;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private CheckBox horse1, horse2, horse3;
    private EditText betAmountEditText;
    private TextView cashTextView, goTextView;
    private double userBalance = 0;
    private EditText editTextNumber3;
    private SeekBar seekBar1, seekBar2, seekBar3;
    private Handler handler = new Handler();
    private Random random = new Random();
    private boolean raceFinished = false;
    private int winningHorse = -1; // Lưu con ngựa chiến thắng duy nhất

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        horse1 = findViewById(R.id.horse1);
        horse2 = findViewById(R.id.horse2);
        horse3 = findViewById(R.id.horse3);
        betAmountEditText = findViewById(R.id.betAmount);
        cashTextView = findViewById(R.id.tvCash);
        goTextView = findViewById(R.id.tvGo);
        editTextNumber3 = findViewById(R.id.editTextNumber3);
        seekBar1 = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);

        userBalance = getIntent().getDoubleExtra("money", 0);
        editTextNumber3.setText(String.valueOf(userBalance));

        cashTextView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CashActivity.class);
            startActivityForResult(intent, 1);
        });

        goTextView.setOnClickListener(v -> {
            String betText = betAmountEditText.getText().toString();
            if (betText.isEmpty()) {
                Toast.makeText(this, "Please enter a valid bet amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double betAmount = Double.parseDouble(betText);
            if (betAmount > 0 && (horse1.isChecked() || horse2.isChecked() || horse3.isChecked())) {
                int selectedHorse = getSelectedHorse();
                startRace(selectedHorse, betAmount);
            } else {
                Toast.makeText(this, "Please select a horse and enter a valid bet amount", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getSelectedHorse() {
        if (horse1.isChecked()) return 1;
        if (horse2.isChecked()) return 2;
        return 3;
    }

    private void startRace(int selectedHorse, double betAmount) {
        seekBar1.setProgress(0);
        seekBar2.setProgress(0);
        seekBar3.setProgress(0);
        raceFinished = false;
        winningHorse = -1;

        moveSeekBar(seekBar1, 1, selectedHorse, betAmount);
        moveSeekBar(seekBar2, 2, selectedHorse, betAmount);
        moveSeekBar(seekBar3, 3, selectedHorse, betAmount);
    }

    private void moveSeekBar(SeekBar seekBar, int horseNumber, int selectedHorse, double betAmount) {
        final int[] progress = {0};

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (raceFinished) return; // Dừng nếu đã có ngựa thắng

                if (progress[0] < 100) {
                    progress[0] += random.nextInt(5) + 1; // Tiến lên từ 1 đến 5
                    seekBar.setProgress(progress[0]);
                    handler.postDelayed(this, 100);
                } else if (!raceFinished) {
                    // Khi ngựa đầu tiên đạt 100, nó sẽ là ngựa thắng duy nhất
                    raceFinished = true;
                    winningHorse = horseNumber;

                    boolean userWon = (selectedHorse == winningHorse);
                    if (userWon) {
                        userBalance += betAmount * 2;
                    } else {
                        userBalance -= betAmount;
                    }

                    editTextNumber3.setText(String.valueOf(userBalance));

                    // Chuyển sang màn hình kết quả
                    Intent intent = new Intent(HomeActivity.this, ResultActivity.class);
                    intent.putExtra("won", userWon);
                    intent.putExtra("balance", userBalance);
                    intent.putExtra("betAmount", betAmount);
                    intent.putExtra("selectedHorse", selectedHorse);
                    intent.putExtra("winningHorse", winningHorse);
                    startActivity(intent);
                    finish();
                }
            }
        }, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            userBalance += data.getDoubleExtra("depositAmount", 0);
            editTextNumber3.setText(String.valueOf(userBalance));
        }
    }
}

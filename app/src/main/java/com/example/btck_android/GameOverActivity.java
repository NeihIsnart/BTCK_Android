package com.example.btck_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {
    private Button BtnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        setContentView(R.layout.activity_game_over);
        BtnPlayAgain = (Button) findViewById(R.id.btn_play_again);
        BtnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
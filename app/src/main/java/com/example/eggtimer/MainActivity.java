package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView time_text;
    SeekBar timer_seekbar;
    Button timer_button;
    boolean timer_run;
    CountDownTimer countdown;

    public void set_timetext(int current_time){
        int minutes = current_time/60;
        int seconds = current_time - (minutes * 60);
        String str_second = String.valueOf(seconds);
        if(seconds < 10){
            str_second = "0" + str_second;
        }
        time_text.setText(String.valueOf(minutes)+ ":" + str_second);
    }

    public void reset_timer(){
        timer_run = false;
        countdown.cancel();
        timer_seekbar.setEnabled(true);
        timer_seekbar.setProgress(30);
        time_text.setText("0:30");
        timer_button.setText("GO!");
    }

    public void button_action(View view){
        if(timer_run){
            reset_timer();
        }
        else{
            timer_run = true;
            timer_seekbar.setEnabled(false);
            timer_button.setText("STOP!");
            countdown = new CountDownTimer(timer_seekbar.getProgress() * 1000 + 100, 1000){
                @Override
                public void onTick(long l) {
                    set_timetext((int)l/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer media = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    media.start();
                    reset_timer();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer_seekbar = findViewById(R.id.timer_seekBar);
        time_text = findViewById(R.id.time_textView);
        timer_button = findViewById(R.id.time_button);

        timer_run = false;
        timer_seekbar.setMax(600);
        timer_seekbar.setProgress(30);

        timer_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                set_timetext(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
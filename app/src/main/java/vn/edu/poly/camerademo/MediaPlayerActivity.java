package vn.edu.poly.camerademo;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MediaPlayerActivity extends AppCompatActivity {



    private MediaPlayer mediaPlayer;
    private TextView tvTen,tvTime1,tvTime2;
    private ImageButton btnBack,btnNext,btnPause,btnStop;
    private SeekBar seMusic;
    private ImageView imgCD;
    private ArrayList<Song> songs;
    int posion = 0;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        // khoi tao mediaPlayer

        anhXa();
        addSong();
       khoitao();
       animation = AnimationUtils.loadAnimation(this,R.anim.zoom);

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPause.setImageResource(R.drawable.pause);
                }else {
                    mediaPlayer.start();
                    btnPause.setImageResource(R.drawable.start);
                }
                setTime2();
                updateTime();
                imgCD.startAnimation(animation);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPause.setImageResource(R.drawable.pause);
                khoitao();
                setTime2();
                imgCD.clearAnimation();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posion++;
                if (posion>songs.size()-1){
                    posion = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoitao();
                mediaPlayer.start();
                btnPause.setImageResource(R.drawable.start);
                setTime2();
                updateTime();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posion--;
                if (posion<0){
                    posion = songs.size()-1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoitao();
                mediaPlayer.start();
                btnPause.setImageResource(R.drawable.start);
                setTime2();
                updateTime();
            }
        });

        seMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seMusic.getProgress());
            }
        });

    }

    private void khoitao() {
        mediaPlayer = MediaPlayer.create(MediaPlayerActivity.this,songs.get(posion).getFile());
        tvTen.setText(songs.get(posion).getTen());
    }

    private void addSong() {
        songs = new ArrayList<>();
        songs.add(new Song("Tình yêu đẹp nhất",R.raw.tinhyeudepnhat));
        songs.add(new Song("Gặp anh đúng lúc",R.raw.gapanhdungluc));
        songs.add(new Song("Gương mặp lạ lẫm",R.raw.guongmatlalam));
        songs.add(new Song("Vô tình",R.raw.votinh));
    }

    private void anhXa() {
        tvTen = findViewById(R.id.tvTenbaihat);
        tvTime1 = findViewById(R.id.tvTime1);
        tvTime2 = findViewById(R.id.tvTime2);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        seMusic = findViewById(R.id.seMedia);
        imgCD = findViewById(R.id.imgCD);
    }
    private void setTime2(){
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        tvTime2.setText(time.format(mediaPlayer.getDuration()));
        seMusic.setMax(mediaPlayer.getDuration());
    }
    private void updateTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
                tvTime1.setText(time.format(mediaPlayer.getCurrentPosition()));
                seMusic.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        posion++;
                        if (posion>songs.size()-1){
                            posion = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        khoitao();
                        mediaPlayer.start();
                        btnPause.setImageResource(R.drawable.start);
                        setTime2();
                        updateTime();

                    }
                });
                handler.postDelayed(this,500);
            }
        },10);
    }


    public void danhsach(View view) {
        Intent intent = new Intent(MediaPlayerActivity.this,CameraPreviewActivity.class);
        startActivity(intent);
    }
}

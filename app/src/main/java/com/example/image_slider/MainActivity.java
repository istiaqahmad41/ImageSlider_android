package com.example.image_slider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ImageSliderAdapter adapter;
    private List<String> imageUrlList;
    private int currentPage = 0;
    private Timer timer;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      Intent i = new Intent(MainActivity.this, MainActivity2.class);
                      startActivity(i);
            }
        });
        // Initialize views
        viewPager = findViewById(R.id.viewPager);

        // Prepare image URLs
        imageUrlList = new ArrayList<>();
        imageUrlList.add("https://img.youtube.com/vi/LrEMzfehsyM/hqdefault.jpg");
        imageUrlList.add("https://img.youtube.com/vi/He8ziF8Bwec/hqdefault.jpg");
        imageUrlList.add("https://img.youtube.com/vi/6oxmI7lESYw/hqdefault.jpg");

        // Initialize adapter
        adapter = new ImageSliderAdapter(this, imageUrlList);
        viewPager.setAdapter(adapter);


        // Auto-slide images
        startAutoSlider();




    }
    private void startAutoSlider() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == imageUrlList.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // Create a new Timer
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 3000); // Delay 1 second, repeat every 3 seconds
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
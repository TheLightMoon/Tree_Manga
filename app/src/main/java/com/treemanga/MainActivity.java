package com.treemanga;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.ForBannerSliderAdapter;
import interfaces.InterfaceBannerLoad;
import service.PicassoLoadingService;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements InterfaceBannerLoad {
    //Slider banner (SB)
    Slider banner_slider;
    //Swipe Refresh Layout (SRL)
    SwipeRefreshLayout swipeRefreshLayout;
    //Realtime Database: Banners, Manga (RB: B, M)
    DatabaseReference banners;

    //Call interface Banner, Manga (CI: B, M)
    InterfaceBannerLoad bannerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RB: B, M
        banners = FirebaseDatabase.getInstance().getReference(" Banners");
        //CI: B, M
        bannerListener = this;
        //SB
        banner_slider = findViewById(R.id.banner_slider);
        Slider.init(new PicassoLoadingService());
        //SRL, load banner, load manga
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.Red,R.color.Yellow , R.color.Lime);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadBanners();
            loadMangas();
        });

        swipeRefreshLayout.post(() -> {
            loadBanners();
            loadMangas();
        });
    }

    private void loadBanners() {
        banners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> bannerList = new ArrayList<>();
                for (DataSnapshot bannerSnapshot:snapshot.getChildren())
                {
                    String image = bannerSnapshot.getValue(String.class);
                    bannerList.add(image);
                }
                //CI: B
                bannerListener.onBannerLoadDoneListener(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Out"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMangas() {
    }

    @Override
    public void onBannerLoadDoneListener(List<String> banners) {
        banner_slider.setAdapter(new ForBannerSliderAdapter(banners));
    }
}
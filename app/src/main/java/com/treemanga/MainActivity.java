package com.treemanga;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.ForBannerSliderAdapter;
import adapter.ForMangaAdapter;
import common.Common;
import dmax.dialog.SpotsDialog;
import interfaces.InterfaceBannerLoad;
import interfaces.InterfaceMangaLoad;
import model.Manga;
import service.PicassoLoadingService;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements InterfaceBannerLoad, InterfaceMangaLoad {
    //Slider Banner (SB)
    Slider slider;
    //Swipe Refresh Layout (SRL)
    SwipeRefreshLayout swipeRefreshLayout;
    //Recycler View (RV)
    RecyclerView manga_recycler;
    //Text View (TV)
    TextView manga_txt;
    //Realtime Database: Banners, Manga (RB: B, M)
    DatabaseReference banners, mangas;

    //Call interface Banner, Manga (CI: B, M)
    InterfaceBannerLoad bannerListener;
    InterfaceMangaLoad mangaListener;

    //Alert Dialog (AD)
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RB: B, M
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        mangas = FirebaseDatabase.getInstance().getReference("Mangas");
        //CI: B, M
        bannerListener = this;
        mangaListener = this;
        //SB
        slider = findViewById(R.id.banner_slider);
        Slider.init(new PicassoLoadingService());
        //SRL, load banner, load manga (LB, LM)
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
        //RV
        manga_recycler = findViewById(R.id.recycler_manga);
        manga_recycler.setHasFixedSize(true);
        manga_recycler.setLayoutManager(new GridLayoutManager(this, 3));
        //TV
        manga_txt = findViewById(R.id.text_manga);
    }

    private void loadBanners() {
        banners.addListenerForSingleValueEvent(new ValueEventListener() {

            final List<String> bannerList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot bannerSnapshot:snapshot.getChildren())
                {
                    String image = bannerSnapshot.getValue(String.class);
                    bannerList.add(image);
                }
                //CI: B
                bannerListener.onBannerLoadListener(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Out"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMangas() {

        //Show Dialog
        alertDialog = new SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setMessage("Please wait...")
                .build();
        if (!swipeRefreshLayout.isRefreshing())
            alertDialog.show();
        //LB
        mangas.addListenerForSingleValueEvent(new ValueEventListener() {

            final List<Manga> mangas_load = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mangaSnapShot:snapshot.getChildren())
                {
                    Manga manga = mangaSnapShot.getValue(Manga.class);
                    mangas_load.add(manga);
                }
                //CI: M
                mangaListener.onMangaLoadListener(mangas_load);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Out"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBannerLoadListener(List<String> banners) {
        slider.setAdapter(new ForBannerSliderAdapter(banners));
    }

    @Override
    public void onMangaLoadListener(List<Manga> mangaList) {
        Common.mangaList = mangaList;

        manga_recycler.setAdapter(new ForMangaAdapter(getBaseContext(), mangaList));

        manga_txt.setText(new StringBuilder("NEW MANGA (").append(mangaList.size()).append(")"));

        if (!swipeRefreshLayout.isRefreshing())
            alertDialog.dismiss();
    }
}
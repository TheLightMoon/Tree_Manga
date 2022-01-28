package com.treemanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import adapter.ForChapterAdapter;
import common.Common;
import model.Manga;

public class ChapterActivity extends AppCompatActivity {

    TextView txt_chapter_name;
    RecyclerView chapter_recycler;

    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        //Text Name Chapter
        txt_chapter_name = findViewById(R.id.text_name_chapter);
        //Recycler Chapter
        chapter_recycler = findViewById(R.id.recycler_chapter);
        chapter_recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        chapter_recycler.setLayoutManager(layoutManager);
        chapter_recycler.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        //Toolbar, set icon
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.mangaSelected.Name);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //
        findChapter(Common.mangaSelected);
    }

    private void findChapter(Manga mangaSelected) {
        Common.chapterList = mangaSelected.Chapters;
        chapter_recycler.setAdapter(new ForChapterAdapter(this, mangaSelected.Chapters));
        txt_chapter_name.setText(new StringBuilder("CHAPTERS (")
                .append(mangaSelected.Chapters.size())
                .append(")"));
    }
}
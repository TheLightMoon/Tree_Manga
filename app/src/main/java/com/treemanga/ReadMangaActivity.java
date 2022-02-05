package com.treemanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import adapter.ForReadMangaAdapter;
import common.Common;
import model.Chapter;

public class ReadMangaActivity extends AppCompatActivity {

    ViewPager viewPager;
    TextView txt_chapter_name;
    View back_pager, next_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_manga);

        viewPager = findViewById(R.id.pager_view);
        txt_chapter_name = findViewById(R.id.text_name_chapter);
        back_pager = findViewById(R.id.chapter_back);
        next_pager = findViewById(R.id.chapter_next);
        //Set
        back_pager.setOnClickListener(v -> {
            if (Common.chapterIndex == 0)
                Toast.makeText(ReadMangaActivity.this, "Reading First Chapter", Toast.LENGTH_SHORT).show();
            else {
                Common.chapterIndex--;
                findLinks(Common.chapterList.get(Common.chapterIndex));
            }
        });

        next_pager.setOnClickListener(v -> {
            if (Common.chapterIndex == Common.chapterList.size()-1)
                Toast.makeText(ReadMangaActivity.this, "Reading Last Chapter", Toast.LENGTH_SHORT).show();
            else {
                Common.chapterIndex++;
                findLinks(Common.chapterList.get(Common.chapterIndex));
            }
        });

        findLinks(Common.chapterSelected);
    }

    private void findLinks(Chapter chapter) {
        if (chapter.Links != null) {
            if (chapter.Links.size() > 0) {
                ForReadMangaAdapter adapter = new ForReadMangaAdapter(getBaseContext(), chapter.Links);
                viewPager.setAdapter(adapter);
                txt_chapter_name.setText(Common.formatString(Common.chapterSelected.Name));
                //Animation Up and Down
            }
            else {
                Toast.makeText(this, "Wait next time update!!!!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Chapter is loading...", Toast.LENGTH_SHORT).show();
        }
    }
}
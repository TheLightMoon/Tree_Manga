package com.treemanga;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.ForMangaAdapter;
import common.Common;
import model.Manga;

public class FilterSearchActivity extends AppCompatActivity {

    NavigationBarView bottomNavigationView;
    RecyclerView recycler_filter_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);
        //Recycler Filter Search (RFS)
        recycler_filter_search = findViewById(R.id.recycler_filter_search);
        recycler_filter_search.setHasFixedSize(true);
        recycler_filter_search.setLayoutManager(new GridLayoutManager(this, 3));
        //Bottom Navigation View (BNV)
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.inflateMenu(R.menu.main_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_filter:
                        showFilterDialog();
                        break;
                    case R.id.action_search:
                        showSearchDialog();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void showFilterDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        alertDialog.setTitle("Filter category");

        final LayoutInflater inflater = this.getLayoutInflater();
        View filter_layout = inflater.inflate(R.layout.dialog_option, null);

        final AutoCompleteTextView txt_category = filter_layout.findViewById(R.id.text_category);
        final ChipGroup chipGroup = filter_layout.findViewById(R.id.chipGroup);
        //Create Auto Complete
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, Common.categories);
        txt_category.setAdapter(adapter);
        txt_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Clear category
                txt_category.setText("");
                //Create tags category
                Chip chip = (Chip) inflater.inflate(R.layout.chip_tags, null, false);
                chip.setText(((TextView)view).getText());
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipGroup.removeView(v);
                    }
                });
                chipGroup.addView(chip);
            }
        });
        //Cancel Alert Dialog
        alertDialog.setView(filter_layout);
        alertDialog.setNegativeButton("CANCEL", (dialogInterface, which) -> dialogInterface.dismiss());
        //Filter Alert Dialog
        alertDialog.setPositiveButton("FILTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> filter_key = new ArrayList<>();
                StringBuilder filter_query = new StringBuilder();
                for (int i = 0; i<chipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) chipGroup.getChildAt(i);
                    filter_key.add(chip.getText().toString());
                    //Category sort A>Z
                    //Sort filter_key
                    Collections.sort(filter_key);
                    //Covert list to string
                    for (String key:filter_key) {
                        filter_query.append(key).append(", ");
                    }
                    //Remove ", "
                    filter_query.setLength(filter_query.length()-1);
                    //Filter by filter_query
                    findFilterCategory(filter_query.toString());
                }
            }
        });
        alertDialog.show();
    }

    private void showSearchDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        alertDialog.setTitle("Search category");

        final LayoutInflater inflater = this.getLayoutInflater();
        View search_layout = inflater.inflate(R.layout.dialog_search, null);

        EditText edt_search = search_layout.findViewById(R.id.edit_search);
        //CAD
        alertDialog.setView(search_layout);
        alertDialog.setNegativeButton("CANCEL", (dialogInterface, which) -> {
            dialogInterface.dismiss();
        });
        //SAD
        alertDialog.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                findSearchManga(edt_search.getText().toString());
            }
        });
        alertDialog.show();
    }

    private void findFilterCategory(String query) {
        List<Manga> mangaFilter = new ArrayList<>();
        for (Manga manga:Common.mangaList) {
            if (manga.Category != null) {
                if (manga.Category.contains(query)) {
                    mangaFilter.add(manga);
                }
            }
        }
        if (mangaFilter.size() > 0) {
            recycler_filter_search.setAdapter(new ForMangaAdapter(getBaseContext(), mangaFilter));
        } else {
            Toast.makeText(this,"No find", Toast.LENGTH_SHORT).show();
        }
    }

    private void findSearchManga(String query) {
        List<Manga> mangaSearch = new ArrayList<>();
        for (Manga manga:Common.mangaList) {
            if (manga.Name.contains(query))
                mangaSearch.add(manga);
        }
        if (mangaSearch.size() > 0) {
            recycler_filter_search.setAdapter(new ForMangaAdapter(getBaseContext(), mangaSearch));
        } else {
            Toast.makeText(this,"No find manga", Toast.LENGTH_SHORT).show();
        }
    }
}
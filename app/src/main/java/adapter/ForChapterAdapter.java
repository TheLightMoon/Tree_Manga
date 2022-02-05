package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.treemanga.R;
import com.treemanga.ReadMangaActivity;

import java.util.List;

import common.Common;
import interfaces.InterfaceRecyclerItemClickListener;
import model.Chapter;

public class ForChapterAdapter extends RecyclerView.Adapter<ForChapterAdapter.TheViewHolder> {

    Context context;
    List<Chapter> chapterList;
    LayoutInflater inflater;

    public ForChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.chapter_book, parent, false);
        return new TheViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TheViewHolder holder, int position) {
        //Event Click Manga Image
        holder.txt_number_chapter.setText(chapterList.get(position).Name);
        //Event Click Chapter Manga
        holder.setRecyclerItemClickListener((view, position1) -> {
            Common.chapterSelected = chapterList.get(position1);
            Common.chapterIndex = position1;
            context.startActivity(new Intent(context, ReadMangaActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class TheViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Click Image Manga
        TextView txt_number_chapter;
        //Click Chapter Manga
        InterfaceRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(InterfaceRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public TheViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_number_chapter = itemView.findViewById(R.id.text_number_chapter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerItemClickListener.onClick(v, getAdapterPosition());
        }
    }
}

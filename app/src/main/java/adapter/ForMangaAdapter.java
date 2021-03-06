package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.treemanga.ChapterActivity;
import com.treemanga.R;

import java.util.List;

import common.Common;
import interfaces.InterfaceRecyclerItemClickListener;
import model.Manga;

public class ForMangaAdapter extends RecyclerView.Adapter<ForMangaAdapter.TheViewHolder> {

    Context context;
    List<Manga> mangaList;

    //Layout Inflater (LI)
    LayoutInflater inflater;

    public ForMangaAdapter(Context context, List<Manga> mangaList) {
        this.context = context;
        this.mangaList = mangaList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.manga_book, parent, false);

        return new TheViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TheViewHolder holder, int position) {
        Picasso.get().load(mangaList.get(position).Image).into(holder.manga_image);
        holder.manga_name.setText(mangaList.get(position).Name);
        //Event onclick
        holder.setRecyclerItemClickListener((view, position1) -> {
            //Save mangaSelected
            Common.mangaSelected = mangaList.get(position1);
            Intent intent = new Intent(context, ChapterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    public static class TheViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView manga_name;
        ImageView manga_image;

        InterfaceRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(InterfaceRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public TheViewHolder(@NonNull View itemView) {
            super(itemView);

            manga_image = itemView.findViewById(R.id.image_manga);
            manga_name = itemView.findViewById(R.id.name_manga);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}

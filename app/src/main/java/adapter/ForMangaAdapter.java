package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.treemanga.R;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    public static class TheViewHolder extends RecyclerView.ViewHolder {

        TextView manga_name;
        ImageView manga_image;

        public TheViewHolder(@NonNull View itemView) {
            super(itemView);

            manga_image = itemView.findViewById(R.id.image_manga);
            manga_name = itemView.findViewById(R.id.name_manga);
        }
    }
}

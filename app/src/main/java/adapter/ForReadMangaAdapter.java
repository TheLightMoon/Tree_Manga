package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.treemanga.R;

import java.util.List;

public class ForReadMangaAdapter extends PagerAdapter {

    Context context;
    List<String> imageLinks;
    LayoutInflater inflater;
    PhotoView pager_image;

    public ForReadMangaAdapter(Context context, List<String> imageLinks) {
        this.context = context;
        this.imageLinks = imageLinks;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageLinks.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View image_layout = inflater.inflate(R.layout.pager_book, container, false);
        pager_image = image_layout.findViewById(R.id.image_pager);
        Picasso.get().load(imageLinks.get(position)).into(pager_image);
        container.addView(image_layout);
        return image_layout;
    }
}

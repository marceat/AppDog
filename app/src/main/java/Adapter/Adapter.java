package Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdog.R;

import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;


public class Adapter extends ArrayAdapter<String> {

    private final List<String> images;
    private final List<String> names;
    boolean textNameOn;
    private Activity context;
    ImageLoader imageLoader = Coil.imageLoader(this.context);

    //Constructor
    public Adapter(Activity context, List<String> images, List<String> names, boolean textNameOn){
        super(context, R.layout.elements_row, images);
        this.context = context;
        this.images = images;
        this.names = names;
        this.textNameOn = textNameOn;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public String getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.elements_row, null);
        ImageView newImage = row.findViewById(R.id.imageRow);
        TextView newText = row.findViewById(R.id.textRow);

        if(textNameOn) {
            newText.setText(this.names.get(i));
        } else {
            newText.setAlpha(0);  //Desaparezco el texto, aumentando la transparencia.
        }

        putImagesOnHome(imageLoader, newImage, images.get(i));
        return row;
    }

    public void putImagesOnHome(ImageLoader imageLoader, ImageView imageView, String imageName){
        ImageRequest request = new ImageRequest.Builder(this.context)
                .data(imageName)
                .crossfade(true)
                .crossfade(800)
                .target(imageView)
                .build();
        imageLoader.enqueue(request);
    }
}

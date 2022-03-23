package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdog.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import IO.ApiService;
import Models.Dog;
import Models.PostBreeds;
import Models.PostImages;
import Adapter.Adapter;
import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewHome extends AppCompatActivity {

    PostBreeds postApiBreeds = new PostBreeds();
    List<Dog> myDogs = new LinkedList<>();
    List<String> images = new LinkedList<>();
    List<String> names = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_home);
        ImageLoader imageLoader = Coil.imageLoader(getApplicationContext());
        ListView dogListLayout = findViewById(R.id.dogList);

        myDogs = (List<Dog>) getIntent().getSerializableExtra("List of dogs");
        images = (List<String>) getIntent().getSerializableExtra("List of images");
        names = (List<String>) getIntent().getSerializableExtra("List of names");

        //loadDogs(postApiBreeds, myDogs);   // Cargo una lista de perros con sus razas, subrazas e imagenes.
        //Adapter adapter = new Adapter(this, myDogs, true);    //Instancio adaptador del layout.
        Adapter adapter = new Adapter(this, images, names, true);


        //Espero unos segundos mientras la call de la api termina de recolectar la información, luego, seteo el adaptador al
        //Runnable r = new Runnable() {
        //    @Override
        //    public void run(){
        //        Log.e("TEST", String.valueOf("Esperando"));
        //        for(Dog dog: myDogs){
        //            images.add(dog.getFirstImage());
        //            names.add(dog.getBreedName());
        //        }
                dogListLayout.setAdapter(adapter);
        //    }
        //};
        //Handler h = new Handler();
        //h.postDelayed(r, 5000); // <-- the "1000" is the delay time in miliseconds.


        //Establezco los clickeables para cada elemento de la lista
        dogListLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Intent viewSpecificDog = new Intent (ViewHome.this, ViewSpecifigDog.class);
                viewSpecificDog.putExtra("actualDog", myDogs.get(position));
                startActivity(viewSpecificDog);
            }
        });
    }


}
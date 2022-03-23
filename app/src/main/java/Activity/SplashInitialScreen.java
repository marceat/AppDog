package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdog.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import IO.ApiService;
import Models.Dog;
import Models.PostBreeds;
import Models.PostImages;
import coil.ImageLoader;
import coil.request.ImageRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashInitialScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        PostBreeds postApiBreeds = new PostBreeds();
        final List<Dog> myDogs = new LinkedList<>();
        List<String> images = new LinkedList<>();
        List<String> names = new LinkedList<>();

        loadDogs(postApiBreeds, myDogs);

        //Espero unos segundos mientras la call de la api termina de recolectar la información, luego, seteo el adaptador al
        Runnable r = new Runnable() {
            @Override
            public void run(){
                Log.e("TEST", String.valueOf("Carga de datos desde la api completa."));
                for(Dog dog: myDogs){
                    images.add(dog.getFirstImage());
                    names.add(dog.getBreedName());
                }
                Intent activityViewHome = new Intent(SplashInitialScreen.this, ViewHome.class);
                activityViewHome.putExtra("List of dogs", (Serializable) myDogs);
                activityViewHome.putExtra("List of images", (Serializable) images);
                activityViewHome.putExtra("List of names", (Serializable) names);
                startActivity(activityViewHome);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 7000); // <-- the "1000" is the delay time in miliseconds.


    }

    public void loadDogs(PostBreeds postApiBreeds, List<Dog> myDogs){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/breeds/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<PostBreeds> call = service.getDogs();

        call.enqueue(new Callback<PostBreeds>() {
            @Override
            public void onResponse(Call<PostBreeds> call, Response<PostBreeds> response){

                Log.e("TEST", "ONRESPONSE EXITOSO.");

                postApiBreeds.setMessage( response.body().getMessage() );

                //Log.e("TEST", String.valueOf(postApiBreeds.getMessage().entrySet()));

                //Obtengo en una colección, todos los items del JsonObject con el .entrySet()
                Set<Map.Entry<String, JsonElement>> BreedList = response.body().getMessage().entrySet();

                //Recorro la coleccion de Razas (JsonElements)
                for(Map.Entry<String, JsonElement> BreedApi: BreedList){

                    Dog newDog = new Dog();
                    newDog.setBreedName(BreedApi.getKey());

                    //Log.e("TEST", String.valueOf(Breed.getKey()));  //Nombre raza
                    //breed.getKey() para obtener el nombre de la raza y .getValue() para obtener la lista de subrazas,

                    JsonArray subBreedsApi = BreedApi.getValue().getAsJsonArray();

                    //Si la raza actual tiene subrazas
                    // tamaño de la lista de subrazas Log.e("TEST", String.valueOf(subBreedsApi.size()));
                    if(subBreedsApi.size() > 0){
                        for(JsonElement subBreedApi: subBreedsApi) {
                            Dog newDogSubBreed = new Dog();
                            newDogSubBreed.setBreedName(subBreedApi.getAsString());
                            //Agregar imagen al perro
                            loadSubBreedImages(newDog, newDogSubBreed);

                            newDog.addSubBreed(newDogSubBreed);
                            //Log.e("TEST", String.valueOf(newDog.getBreedName()+" - ")+String.valueOf(newDog.getSubBreeds()));

                        }
                    } else {
                        //Cargo imagenes para el perro actual, sin subrazas.
                        loadBreedImages(newDog);
                    }

                    //TEST -> Log.e("TEST", String.valueOf(newDog.getBreedName()+" - ")+String.valueOf(newDog.getSubBreeds()));

                    //Agrego el perro a la lista
                    myDogs.add(newDog);
                    Log.e("TEST", String.valueOf(myDogs.size()));
                }

            }

            @Override
            public void onFailure(Call<PostBreeds> call, Throwable t) {
                Log.e("TEST", "ONFAILURE. METODO GETDOGS FALLIDO.");
            }
        });
    }

    public void loadBreedImages(Dog newDog) {
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://dog.ceo/api/breed/" + breedName +"/")
                .baseUrl("https://dog.ceo/api/breed/"+newDog.getBreedName()+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<PostImages> call = service.getBreedImages();


        call.enqueue(new Callback<PostImages>() {
            @Override
            public void onResponse(Call<PostImages> call, Response<PostImages> response) {

                //Log.e("TEST", String.valueOf(response.body().getMessage()));

                //Obtengo la lista de imagenes para esa raza, la recorro y voy agregando a la lista propia del perro.
                List<String> listOfImages = response.body().getMessage();
                for(String image: listOfImages){
                    //Log.e("TEST", String.valueOf(image));
                    newDog.addImage(image);
                }

            }

            @Override
            public void onFailure(Call<PostImages> call, Throwable t) {
                Log.e("TEST", "ONFAILURE. METODO loadBreedImages FALLIDO.");
            }
        });
    }

    public void loadSubBreedImages(Dog newDog, Dog newDogSubBreed) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/breed/"+newDog.getBreedName()+"/"+newDogSubBreed.getBreedName()+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<PostImages> call = service.getBreedImages();


        call.enqueue(new Callback<PostImages>() {
            @Override
            public void onResponse(Call<PostImages> call, Response<PostImages> response) {

                //Log.e("TEST", String.valueOf(response.body().getMessage()));

                //Obtengo la lista de imagenes para esa raza, la recorro y voy agregando a la lista propia del perro.
                List<String> listOfImages = response.body().getMessage();
                for(String image: listOfImages){
                    //Log.e("TEST", String.valueOf(image));
                    newDogSubBreed.addImage(image);
                }

            }

            @Override
            public void onFailure(Call<PostImages> call, Throwable t) {
                Log.e("TEST", "ONFAILURE. METODO loadBreedImages FALLIDO.");
            }
        });
    }

    public void putImagesOnHome(ImageLoader imageLoader, ImageView imageView){
        ImageRequest request = new ImageRequest.Builder(getApplicationContext())
                .data("https://images.dog.ceo/breeds/hound-basset/n02088238_13608.jpg")
                .crossfade(true)
                .target(imageView)
                .build();
        imageLoader.enqueue(request);
    }
}

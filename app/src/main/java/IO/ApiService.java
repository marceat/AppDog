package IO;

import java.util.List;

import Models.PostImages;
import retrofit2.http.GET;
import retrofit2.Call;
import Models.PostBreeds;

public interface ApiService {
    @GET("list/all")
    public Call<PostBreeds> getDogs();

    @GET("images/random/5")
    public Call<PostImages> getBreedImages();

}

package Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.appdog.R;

import Models.Dog;
import Adapter.Adapter;
import coil.Coil;
import coil.ImageLoader;

public class ViewSpecifigDog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_specific_dog);
        ImageLoader imageLoader = Coil.imageLoader(getApplicationContext());
        ListView dogListLayout = findViewById(R.id.specificDogList);
        TextView tittle = findViewById(R.id.tittle);
        ConstraintLayout background = findViewById(R.id.background);
        background.getBackground().setColorFilter(Color.parseColor("#E8BD59"), PorterDuff.Mode.DARKEN);


        Adapter adapter;

        Dog actualDog = (Dog) getIntent().getSerializableExtra("actualDog");
        tittle.setText(actualDog.getBreedName());

        if(actualDog.haveSubBreeds()) {
            adapter = new Adapter(this, actualDog.getImages(), actualDog.getSubBreedNames(), true);    //Instancio adaptador del layout.
        } else {
            adapter = new Adapter(this, actualDog.getImages(), null, false);
        }

        dogListLayout.setAdapter(adapter);


        //Establezco los clickeables para cada elemento de la lista SOLO SI TIENE SUBRAZAS.
        if(actualDog.haveSubBreeds()) {
            dogListLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                    Intent viewSpecificDog = new Intent(ViewSpecifigDog.this, ViewSpecifigDog.class);
                    viewSpecificDog.putExtra("actualDog", actualDog.getSubBreeds().get(position));
                    startActivity(viewSpecificDog);
                }
            });
        }
    }
}

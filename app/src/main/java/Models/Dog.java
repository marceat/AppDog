package Models;

import android.util.Log;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Dog implements Serializable {
    private String breedName;
    private List<String> images = new LinkedList<>();
    private List<Dog> subBreeds = new LinkedList<>();

    public List<Dog> getSubBreeds() {
        return subBreeds;
    }

    public List<String> getImages() {
        if(images.size() > 0) {
            return images;
        } else {
            List<String> firstImagesFromAllSubrBreeds = new LinkedList<>();
            for(Dog subBreed: subBreeds){
                firstImagesFromAllSubrBreeds.add(subBreed.getFirstImage());
            }
            return firstImagesFromAllSubrBreeds;
        }
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setSubBreeds(List<Dog> subBreeds) {
        this.subBreeds = subBreeds;
    }

    public String getFirstImage(){
        if(subBreeds.size() > 0){
            return subBreeds.get(0).getFirstImage();
        } else {
            return images.get(0);
        }
    }

    public String getFirstSubBreedFirstImage(){
        return subBreeds.get(0).getFirstImage();
    }

    public void addSubBreed (Dog newDog){
        subBreeds.add(newDog);
    }

    public void addImage(String imageName){
        images.add(imageName);
    }

    public boolean haveSubBreeds(){
        return (subBreeds.size() > 0);
    }

    public List<String> getSubBreedNames(){
        List<String> names = new LinkedList<>();
        for (Dog dogSubBreed: subBreeds){
            names.add(dogSubBreed.getBreedName());
        }
        return names;
    }
}

package Models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class PostBreeds {
    private JsonObject message;
    private String status;

    public JsonObject getMessage() {
        return message;
    }

    public void setMessage(JsonObject aMessage) {
        this.message = aMessage;
    }
}

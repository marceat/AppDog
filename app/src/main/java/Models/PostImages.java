package Models;

import com.google.gson.JsonObject;

import java.util.List;

public class PostImages {
    private List message;
    private String status;

    public List getMessage() {
        return message;
    }

    public void setMessage(List aMessage) {
        this.message = aMessage;
    }
}

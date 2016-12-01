package com.parentoff.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by pooja on 20/10/16.
 */
public class JsonReader {
    public static void main(String[] args) {

        Gson gson = new Gson();

        try (Reader reader = new FileReader("/home/pooja/Desktop/model.json")) {

            // Convert JSON to Java Object
            List<JsonModel> jsonModels = gson.fromJson(reader, new TypeToken<List<JsonModel>>(){}.getType());
            System.out.println(jsonModels);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JsonReader() {
    }
}

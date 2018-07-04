package com.gmail.encryptdev.morecrafting.json;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.util.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by EncryptDev
 */
public class JsonFile {

    private File file;
    private JSONParser parser;
    private HashMap<String, JSONObject> jsonObjects;
    private HashMap<String, JSONArray> jsonArrays;
    private HashMap<String, String> jsonStrings;

    public JsonFile(String jsonFile) {
        this.file = new File(MoreCrafting.getInstance().getDataFolder(), jsonFile);
        if (!this.file.exists())
            throw new RuntimeException("JsonFile " + jsonFile + " doesn't exist, please contact me");

        this.parser = new JSONParser();
        this.jsonObjects = new HashMap<>();
        this.jsonStrings = new HashMap<>();
        this.jsonArrays = new HashMap<>();
    }

    /**
     * Read the json file
     */
    public void read() {
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader(file));

            Iterator<String> keys = object.keySet().iterator();

            while (keys.hasNext()) {
                String key = keys.next();

                if (object.get(key) instanceof JSONObject) {
                    jsonObjects.put(key, (JSONObject) object.get(key));
                    continue;
                }
                if(object.get(key) instanceof JSONArray) {
                    jsonArrays.put(key, (JSONArray) object.get(key));
                    continue;
                }
                jsonStrings.put(key, (String) object.get(key));
            }
        } catch (IOException | ParseException e) {
            Log.throwError("Can not read json file: " + file.getName(), e);
        }
    }

    /**
     * @param key - the key
     * @return - the json object, or throw a {@link NullPointerException} when the result is null
     */
    public JSONObject getJsonObject(String key) {
        if (jsonObjects.get(key) == null)
            throw new NullPointerException("JsonObject with key " + key + " not found");
        return jsonObjects.get(key);
    }

    /**
     * @param key - the key
     * @return - the json array, or throw a {@link NullPointerException} when the result is null
     */
    public JSONArray getJsonArray(String key) {
        if (jsonArrays.get(key) == null)
            throw new NullPointerException("JsonArray with key " + key + " not found");
        return jsonArrays.get(key);
    }

    /**
     *
     * @param key - the key
     * @return - the json string, or throw a {@link NullPointerException} when the result is null
     */
    public String getJsonString(String key) {
        if (jsonStrings.get(key) == null)
            throw new NullPointerException("JsonString with key " + key + " not found");
        return jsonStrings.get(key);
    }

}

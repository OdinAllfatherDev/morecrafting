package com.gmail.encryptdev.morecrafting.json;

/**
 * Created by EncryptDev
 */
public class JsonLoader {

    private JsonFile messageFile;
    private JsonFile recipeSettingsFile;

    public JsonLoader() {
        this.messageFile = new JsonFile("messages.json");
        this.recipeSettingsFile = new JsonFile("recipe-settings.json");
    }

    /**
     * Read all the {@link JsonFile}'s
     */
    public void load() {
        this.messageFile.read();
        this.recipeSettingsFile.read();
    }

    public JsonFile getRecipeSettingsFile() {
        return recipeSettingsFile;
    }

    public JsonFile getMessageFile() {
        return messageFile;
    }
}

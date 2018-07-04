package com.gmail.encryptdev.morecrafting.util;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import com.gmail.encryptdev.morecrafting.json.JsonLoader;
import org.bukkit.ChatColor;

/**
 * Created by EncryptDev
 */
public class MessageTranslator {

    private static final JsonLoader JSON_LOADER = MoreCrafting.getInstance().getJsonLoader();

    /**
     * Get a translated message
     *
     * @param key - the key. (The first thing, in the json file)
     * @return
     */
    public static String getTranslatedMessage(String key) {
        String prefix = ChatColor.translateAlternateColorCodes('&', JSON_LOADER.getMessageFile().getJsonString("prefix"));

        String jKey = JSON_LOADER.getMessageFile().getJsonString(key);
        if (jKey.equalsIgnoreCase("-"))
            return "";

        return prefix + ChatColor.translateAlternateColorCodes('&', jKey);
    }

    public static String getTranslatedMessageWithoutPrefix(String key) {
        String jKey = JSON_LOADER.getMessageFile().getJsonString(key);
        if (jKey.equalsIgnoreCase("-"))
            return "";
        return ChatColor.translateAlternateColorCodes('&', jKey);
    }

    public static String getTranslatedLoreString(String key) {
        return ChatColor.translateAlternateColorCodes('&', (String) JSON_LOADER.getMessageFile().getJsonObject("item-lore").get(key));
    }

    /**
     * Get a translated inventory name
     *
     * @param key - the key. (The first thing, in the json file)
     * @return
     */
    public static String getTranslatedInventoryName(String key) {
        return ChatColor.translateAlternateColorCodes('&', (String) JSON_LOADER.getMessageFile().getJsonObject("inventory-names").get(key));
    }

    public static String getTranslatedItemName(String key) {
        return ChatColor.translateAlternateColorCodes('&', (String) JSON_LOADER.getMessageFile().getJsonObject("item-names").get(key));
    }

}

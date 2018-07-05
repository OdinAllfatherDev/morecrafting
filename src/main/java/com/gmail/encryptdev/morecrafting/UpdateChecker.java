package com.gmail.encryptdev.morecrafting;

import com.gmail.encryptdev.morecrafting.util.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by EncryptDev
 */
public class UpdateChecker implements Listener {

    private String url = "https://api.spigotmc.org/legacy/update.php?resource=";
    private String id = "58367";

    private boolean isAvailable;

    public UpdateChecker() {

    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        if(event.getPlayer().isOp())
            if(isAvailable)
                event.getPlayer().sendMessage("§5MoreCrafting >> §4A update is available");
    }

    public void check() {
        isAvailable = checkUpdate();
    }

    private boolean checkUpdate() {
        Log.info("Check for updates...");
        try {
            String localVersion = MoreCrafting.getInstance().getDescription().getVersion();
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url + id).openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            String remoteVersion;
            if(raw.contains("-")) {
                remoteVersion = raw.split("-")[0].trim();
            } else {
                remoteVersion = raw;
            }

            if(!localVersion.equalsIgnoreCase(remoteVersion))
                return true;

        } catch (IOException e) {
            return false;
        }
        return false;
    }

}

package com.gmail.encryptdev.morecrafting.util;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EncryptDev
 */
public class BlockListFile {

    private File file;
    private FileConfiguration configuration;

    public BlockListFile() {
        this.file = new File(MoreCrafting.getInstance().getDataFolder(), "block-list.yml");
        if (!this.file.exists())
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void loadAll() {
        List<String> list = configuration.getStringList("locations");
        if(list == null)
            list = new ArrayList<>();
        for (String str : list) {
            String[] data = str.split(":");
            World world = Bukkit.getWorld(data[0]);
            double x = Double.parseDouble(data[1]);
            double y = Double.parseDouble(data[2]);
            double z = Double.parseDouble(data[3]);
            String owner = "";
            if(data.length == 5)
                owner = data[5];
            if (world.getBlockAt((int) x, (int) y, (int) z).getType() == Material.WORKBENCH) {
                world.getBlockAt((int) x, (int) y, (int) z).setMetadata(MoreCrafting.CRAFTING_META_DATA,
                        new FixedMetadataValue(MoreCrafting.getInstance(), "CUSTOM-CRAFTER"));
                if(MoreCrafting.getInstance().getConfig().getBoolean("block-owner") && !owner.equals("")) {
                    world.getBlockAt((int) x, (int) y, (int) z).setMetadata(MoreCrafting.BLOCK_OWNER_META_DATA,
                            new FixedMetadataValue(MoreCrafting.getInstance(), owner));
                }
            }
        }
    }

    public void addBlock(Location location, Player player) {
        List<String> list = configuration.getStringList("locations");
        if(list == null)
            list = new ArrayList<>();
        String dataStr = location.getWorld().getName() + ":" + location.getX() + ":" +
                location.getY() + ":" + location.getZ() + (player != null ? ":" + player.getUniqueId().toString() : "");
        list.add(dataStr);
        configuration.set("locations", list);
        save();
    }

    public void removeBlock(Location location) {
        List<String> list = configuration.getStringList("locations");
        List<String> copy = new ArrayList<>(list);
        for(String str : copy) {
            int x = (int) location.getX();
            int y = (int) location.getY();
            int z = (int) location.getZ();
            String[] data = str.split(":");
            if(Integer.parseInt(data[0]) == x && Integer.parseInt(data[1]) == y && Integer.parseInt(data[2]) == z) {
                list.remove(str);
            }
        }
        configuration.set("locations", list);
        this.save();
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

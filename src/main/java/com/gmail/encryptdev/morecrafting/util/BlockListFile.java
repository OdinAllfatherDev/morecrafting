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
 * <p>
 * This class represents a file, and manage the workbench blocks
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

    /**
     * This Method, load all blocks from the file.
     */
    public void loadAll() {
        List<String> list = configuration.getStringList("locations");
        if (list == null)
            list = new ArrayList<>();
        for (String str : list) {
            String[] data = str.split(":");
            World world = Bukkit.getWorld(data[0]);
            int x = Integer.parseInt(data[1]);
            int y = Integer.parseInt(data[2]);
            int z = Integer.parseInt(data[3]);
            String owner = "";
            if (data.length == 5)
                owner = data[5];
            if (world.getBlockAt(x, y, z).getType() == Material.WORKBENCH) {
                world.getBlockAt(x, y, z).setMetadata(MoreCrafting.CRAFTING_META_DATA,
                        new FixedMetadataValue(MoreCrafting.getInstance(), "CUSTOM-CRAFTER"));
                if (MoreCrafting.getInstance().getConfig().getBoolean("block-owner") && !owner.equals("")) {
                    world.getBlockAt(x, y, z).setMetadata(MoreCrafting.BLOCK_OWNER_META_DATA,
                            new FixedMetadataValue(MoreCrafting.getInstance(), owner));
                }
            }
        }
    }

    /**
     * This Method, add only one block to the list.
     *
     * @param location
     * @param player
     */
    public void addBlock(Location location, Player player) {
        List<String> list = configuration.getStringList("locations");
        if (list == null)
            list = new ArrayList<>();
        String dataStr = locationString(location) + (player != null ? ":" + player.getUniqueId().toString() : "");
        list.add(dataStr);
        configuration.set("locations", list);
        save();
    }

    /**
     * This method remove the block
     *
     * @param location
     */
    public void removeBlock(Location location) {
        List<String> list = configuration.getStringList("locations");
        List<String> copy = new ArrayList<>(list);
        for (String str : copy) {
            int x = (int) location.getX();
            int y = (int) location.getY();
            int z = (int) location.getZ();
            String[] data = str.split(":");
            if (Integer.parseInt(data[0]) == x && Integer.parseInt(data[1]) == y && Integer.parseInt(data[2]) == z) {
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

    private String locationString(Location location) {
        return location.getWorld().getName() + ":" + (int) location.getX() + ":" + (int) location.getY() + ":" + (int) location.getZ();
    }

}

package com.gmail.encryptdev.morecrafting.util;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

/**
 * Created by EncryptDev
 */
public class ItemCreator {

    public static ItemStack getItem(Material material, String name, int amount, byte data, List<String> lore, ItemFlag... itemFlags) {
        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null)
            meta.setLore(lore);
        if (itemFlags != null)
            meta.addItemFlags(itemFlags);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getItem(Material material, String name, int amount, byte data, List<String> lore) {
        return getItem(material, name, amount, data, lore, null);
    }

    public static ItemStack getItem(Material material, String name, int amount, byte data) {
        return getItem(material, name, amount, data, null, null);
    }

    public static ItemStack getItem(Material material, String name, int amount) {
        return getItem(material, name, amount, (byte) 0, null, null);
    }

    public static ItemStack getItem(Material material, String name) {
        return getItem(material, name, 1, (byte) 0, null, null);
    }

    public static ItemStack getSkull(String name, OfflinePlayer ownerPlayer, List<String> lore, ItemFlag... itemFlags) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setOwner(ownerPlayer.getName());
        if (lore != null)
            meta.setLore(lore);
        if (itemFlags != null)
            meta.addItemFlags(itemFlags);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack copyItemWithNewAmount(ItemStack itemStack, int newAmount) {
        ItemStack newStack = new ItemStack(itemStack.getType(), newAmount, itemStack.getDurability());
        ItemMeta meta = itemStack.getItemMeta();
        newStack.setItemMeta(meta);
        return newStack;
    }

    public static ItemStack getSkull(String name, OfflinePlayer ownerPlayer, List<String> lore) {
        return getSkull(name, ownerPlayer, lore, null);
    }

    public static ItemStack getSkull(String name, OfflinePlayer ownerPlayer) {
        return getSkull(name, ownerPlayer, null, null);
    }

    public static ItemStack getBanner(String name, DyeColor color, PatternType type, List<String> lore) {
        ItemStack itemStack = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) itemStack.getItemMeta();
        bannerMeta.setDisplayName(name);
        bannerMeta.addPattern(new Pattern(color, type));
        if (lore != null)
            bannerMeta.setLore(lore);
        itemStack.setItemMeta(bannerMeta);
        return itemStack;
    }

    public static ItemStack getLeatherArmor(Material material, Color color, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        LeatherArmorMeta lam = (LeatherArmorMeta) itemStack.getItemMeta();
        lam.setDisplayName(name);
        lam.setColor(color);
        if (lore != null)
            lam.setLore(lore);
        itemStack.setItemMeta(lam);
        return itemStack;
    }

    public static ItemStack getLeatherArmor(Material material, Color color, String name) {
        return getLeatherArmor(material, color, name, null);
    }

    public static ItemStack unbreakable(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setUnbreakable(true);
        itemStack.setItemMeta(meta);
        return itemStack.clone();
    }

    public static boolean isItemStackSame(ItemStack itemStack, ItemStack other) {
        if (itemStack.getType() != other.getType())
            return false;

        if (itemStack.hasItemMeta() && !other.hasItemMeta())
            return false;

        return true;
    }

}

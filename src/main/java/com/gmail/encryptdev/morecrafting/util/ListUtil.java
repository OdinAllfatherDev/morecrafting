package com.gmail.encryptdev.morecrafting.util;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by EncryptDev
 *
 * Little util, for sort a {@link ItemStack} list
 */
public class ListUtil {


    public static List<ItemStack> sort(List<ItemStack> other) {
        List<ItemStack> result = new ArrayList<>();
        List<String> stringList = itemListToStringNameList(other);
        Collections.sort(stringList);

        for (String matStr : stringList) {
            for (ItemStack itemStack : other) {
                if (itemStack.getType().toString().equals(matStr)) {
                    result.add(itemStack);
                }
            }

        }

        return result;
    }

    private static List<String> itemListToStringNameList(List<ItemStack> list) {
        List<String> result = new ArrayList<>();

        for (ItemStack item : list) {
            result.add(item.getType().toString());
        }

        return result;
    }


}

package com.gmail.encryptdev.morecrafting.nbt;

import com.gmail.encryptdev.morecrafting.MoreCrafting;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

/**
 * Created by EncryptDev
 */
public class NBTItemStack extends Reflection {

    private Class<?> craftbukkitCraftItemStack = getCraftBukkitClass("CraftItemStack", "inventory");
    private Object nmsStack;

    public NBTItemStack(ItemStack bukkitItemStack) {
        super(MoreCrafting.getNmsVersion());
        Validate.notNull(bukkitItemStack);
        if (!bukkitItemStack.getClass().equals(getNMSClass("ItemStack")))
            this.nmsStack = invokeMethod(craftbukkitCraftItemStack, "asNMSCopy", null, new Class<?>[]{ItemStack.class},
                    new Object[]{bukkitItemStack});
        else
            this.nmsStack = bukkitItemStack;
    }

    public boolean isSimilar(NBTItemStack other) {
        if (other == null || other.getNmsStack() == null)
            return false;
        if (!other.getNmsStack().getClass().equals(getNMSClass("ItemStack")))
            return false;

        boolean result = (boolean) invokeMethod(nmsStack.getClass(), "b", nmsStack, new Class<?>[]{nmsStack.getClass()},
                new Object[]{other.getNmsStack()});

        Object nbtTag = invokeMethod(nmsStack.getClass(), "getTag", nmsStack, new Class<?>[0], new Object[0]);
        if (nbtTag == null)
            return false;
        Object otherNbtTag = invokeMethod(other.getNmsStack().getClass(), "getTag", other.getNmsStack(), new Class<?>[0], new Object[0]);
        if (otherNbtTag == null)
            return false;

        return result && (boolean) invokeMethod(nbtTag.getClass(), "equals", nbtTag, new Class<?>[]{Object.class},
                new Object[]{otherNbtTag});
    }

    public Object getNmsStack() {
        return nmsStack;
    }

    public static boolean hasNBTTag(ItemStack itemStack) {
        Reflection ref = new Reflection(MoreCrafting.getNmsVersion());
        Object nmsStack = ref.invokeMethod(ref.getCraftBukkitClass("CraftItemStack", "inventory"),
                "asNMSCopy", null, new Class<?>[]{ItemStack.class},
                new Object[]{itemStack});
        Object nbtTag = ref.invokeMethod(nmsStack.getClass(), "getTag", nmsStack, new Class<?>[0], new Object[0]);
        return nbtTag != null;
    }

}

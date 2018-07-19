package com.gmail.encryptdev.morecrafting.nbt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.*;

/**
 * Created by EncryptDev
 */
public class Reflection {

    /**
     * List of NMS Verions
     *
     * @author EncryptDev
     */
    public enum NMSVersion {

        V1_12_R1("v1_12_R1"), V1_11_R1("v1_11_R1"), V1_10_R1("v1_10_R1"), V1_9_R2("v1_9_R2"), V1_9_R1(
                "v1_9_R1"), V1_8_R3("v1_8_R3"), V1_8_R2("v1_8_R2"), V1_8_R1("v1_8_R1"), NONE("none");

        private String version;

        NMSVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }

        public static NMSVersion getNMSVersion(String version) {
            for (NMSVersion nmsVersion : values())
                if (nmsVersion.getVersion().equals(version))
                    return nmsVersion;
            return NONE;
        }

    }

    // NMS Version
    private NMSVersion version;

    // The package for the path version
    private final String packageNMS = "net.minecraft.server.%s";
    private final String packageCraftbukkit = "org.bukkit.craftbukkit.%s.%s";

    /**
     * Default constructor
     *
     * @param version - The path version for the reflection class
     */
    public Reflection(NMSVersion version) {
        this.version = version;
    }

    public void sendPacket(Object packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, packet);
        }
    }

    public void sendPacket(Player player, Object packet) {
        Object nmsp = invokeMethod(player.getClass(), "getHandle", player, new Class<?>[0], new Object[0]);
        Object pcon = getField(nmsp, "playerConnection");
        invokeMethod(pcon.getClass(), "sendPacket", pcon, new Class<?>[]{getNMSClass("Packet")}, new Object[]{packet});
    }

    /**
     * Get a class from a package
     *
     * @param packageName - the package name
     * @param className   - the class name
     * @return - null when the class not found, and throw a
     * {@link ClassNotFoundException}
     */
    public Class<?> getClass(String packageName, String className) {
        try {
            return Class.forName(packageName + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a NMS class
     *
     * @param className - the class name
     * @return - null when the class not found, and throw a
     * {@link ClassNotFoundException}
     */
    public Class<?> getNMSClass(String className) {
        return getClass(String.format(packageNMS, version.getVersion()), className);
    }

    public Class<?> getCraftBukkitClass(String className, String packageName) {
        return getClass(String.format(packageCraftbukkit, version.getVersion(), packageName), className);
    }

    /**
     * Create a new array, and fill it
     *
     * @param type
     * @param size
     * @param values
     * @param <T>
     * @return
     */
    public <T> T[] createNewAndFillArray(Class<T> type, int size, T[] values) {
        T[] array = (T[]) array(type.getClass(), size);
        fillArray(array, values);
        return array;
    }

    /**
     * Create a new array via reflections
     *
     * @param clazz
     * @param size
     * @return
     */
    public Object[] array(Class<?> clazz, int size) {
        return (Object[]) Array.newInstance(clazz, size);
    }

    /**
     * Set a array index
     *
     * @param array
     * @param index
     * @param value
     */
    public void arraySet(Object[] array, int index, Object value) {
        Array.set(array, index, value);
    }

    /**
     * Fill the array
     *
     * @param array
     * @param values
     */
    public void fillArray(Object[] array, Object[] values) {
        if (array.length != values.length)
            throw new RuntimeException("array and values array not the same length");
        for (int i = 0; i < array.length; i++) {
            arraySet(array, i, values[i]);
        }
    }

    /**
     * Get a field value from a class
     *
     * @param clazz     - the class, for the field
     * @param fieldName - the field name
     * @return - the field value
     */
    public Object getField(Class<?> clazz, Object instance, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param object
     * @param fieldName
     * @return
     */
    public Object getField(Object object, String fieldName) {
        return getField(object.getClass(), object, fieldName);
    }

    /**
     * Set a field in a class
     *
     * @param clazz     - the class who set the field
     * @param instance  - the instance from the class
     * @param fieldName - the field name
     * @param value     - the value
     * @return
     */
    public void setField(Class<?> clazz, Object instance, String fieldName, Object value) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get a new instance from a class
     *
     * @param clazz - the class
     * @return
     */
    public Object getNewInstanceClass(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get a new instance from a constructor
     *
     * @param clazz
     * @param parameterTypes
     * @param parameterObjects
     * @return
     */
    public Object getNewInstanceConstructor(Class<?> clazz, Class<?>[] parameterTypes, Object[] parameterObjects) {
        try {
            Constructor<?> constructor = clazz.getConstructor(parameterTypes);
            return constructor.newInstance(parameterObjects);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getNewInstanceConstructorStatic(Class<?> clazz, Class<?>[] parameterTypes, Object[] parameterObjects) {
        try {
            Constructor<?> constructor = clazz.getConstructor(parameterTypes);
            return constructor.newInstance(parameterObjects);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get the super class
     *
     * @param clazz
     * @param superClass
     * @return
     */
    public Class<?> getSuperClass(Class<?> clazz, Class<?> superClass) {
        Class<?> sc = clazz.getSuperclass();

        while (sc != null && !sc.equals(superClass)) {
            sc = sc.getSuperclass();
        }

        return sc;
    }

    /**
     * Get a new instance from a class, with the specific constructor
     *
     * @param clazz
     * @param parameterTypes
     * @return
     */
    public Constructor<?> getConstructor(Class<?> clazz, Class<?>[] parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a method from the clazz
     *
     * @param clazz
     * @param methodeName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class<?> clazz, String methodeName, Class<?>[] parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodeName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * use (invoke) the mode from the class
     *
     * @param clazz
     * @param methodeName
     * @param invokeObject
     * @param parameterTypes
     * @param parameterObjects
     * @return
     */
    public Object invokeMethod(Class<?> clazz, String methodeName, Object invokeObject, Class<?>[] parameterTypes,
                               Object[] parameterObjects) {
        try {
            Method method = getMethod(clazz, methodeName, parameterTypes);
            return method.invoke(invokeObject, parameterObjects);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object invokeMethodStatic(Class<?> clazz, String methodeName, Object invokeObject, Class<?>[] parameterTypes,
                                            Object[] parameterObjects) {
        try {
            Method method = getMethod(clazz, methodeName, parameterTypes);
            return method.invoke(invokeObject, parameterObjects);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get all enum values from the enum class if the class is not a enum it
     * return null
     *
     * @param enumClass
     * @return
     */
    public Object[] getEnumValues(Class<?> enumClass) {
        if (enumClass.isEnum()) {
            return enumClass.getEnumConstants();
        } else {
            return null;
        }
    }

    /**
     * Get a specific value from the enum calss
     *
     * @param enumClass - the enum class
     * @param valueName - NOTE: valueName must in TO_UPPER_CASE
     * @return
     */
    public Object getEnumValue(Class<?> enumClass, String valueName) {
        Object[] enumVals = getEnumValues(enumClass);
        if (enumVals == null)
            return null;

        for (Object enumVal : enumVals)
            if (enumVal.toString().equals(valueName))
                return enumVal;

        return null;
    }

    /**
     * Get a value from the enum value.
     * <p>
     * if you have a enum: <br>
     * <p>
     * <code>
     * public class TestEnum { <br>
     * <br>
     * VALUE1(2), <br>
     * VALUE2(4),  <br>
     * VALUE3(70);  <br>
     * <br>
     * private int theValue; <br>
     * <br>
     * private TestEnum(int theValue) {  <br>
     * this.theValue = theValue; <br>
     * }  <br>
     * <br>
     * public int getValue() { <br>
     * return this.theValue; <br>
     * }  <br>
     * <br>
     * } <br>
     * </code>
     * <p>
     * <p>
     * <p>
     * <p>
     * its return the "theValue" value, if you set the "enumValueName" to
     * 'VALUE1' then return "2"
     *
     * @param enumClass     - the enum class
     * @param enumValueName - the enum value
     * @param fieldName     - the field, that returned
     * @return
     */
    public Object getValueFromEnum(Class<?> enumClass, String enumValueName, String fieldName) {
        Object[] enumVals = getEnumValues(enumClass);
        if (enumVals == null)
            return null;

        try {
            for (Object enumVal : enumVals) {
                if (enumVal.toString().equals(enumValueName)) {
                    Field field = enumVal.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field.get(enumVal);
                }
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get the NMSVersion
     *
     * @return
     */
    public NMSVersion getVersion() {
        return version;
    }

    /**
     * Static method for the enum classes
     * <p>
     * Get a NMS Class
     *
     * @param className
     * @return
     */
    public static Class<?> getNMSClassStatic(String className) {
        try {
            return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Static Method for the enum classes
     * <p>
     * Get a normal field from a class
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getFieldStatic(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object[] getEnumValuesStatic(Class<?> enumClass) {
        if (enumClass.isEnum()) {
            return enumClass.getEnumConstants();
        } else {
            return null;
        }
    }

    public static Object getEnumValueStatic(String enumClass, String valueName) {
        Object[] enumVals = getEnumValuesStatic(getNMSClassStatic(enumClass));
        if (enumVals == null)
            return null;

        for (Object enumVal : enumVals)
            if (enumVal.toString().equals(valueName))
                return enumVal;

        return null;
    }

    /**
     * Change the bitmask
     *
     * @param bitMask
     * @param bit
     * @param state
     * @return
     */
    public static byte changeMask(byte bitMask, MaskBitState bit, boolean state) {
        if (state)
            return bitMask |= 1 << bit.getBit();
        else
            return bitMask &= ~(1 << bit.getBit());
    }


    /**
     * This values are the states for the npc.
     * <p>
     * NOTE: Set befor the npc spawn
     *
     * @author tobia
     */
    public enum MaskBitState {

        FIRE(0), SNEAK(1), NOT_SET(2), SPRINT(3), USE_ITEM(4), INVISIBLE(5);

        private int bit;

        private MaskBitState(int bit) {
            this.bit = bit;
        }

        public int getBit() {
            return bit;
        }

    }

}

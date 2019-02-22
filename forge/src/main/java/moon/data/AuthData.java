package moon.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nonnull;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static moon.AntiExtortion.MODID;

public class AuthData extends WorldSavedData {
    private static final String DATA_NAME = MODID + "_credentialData";

    private Map<String, String> passwordMap = new HashMap<>();

    public AuthData() {
        super(DATA_NAME);
    }

    public AuthData(String s) {
        super(s);
    }

    public void setPassword(String username, String password) {
        passwordMap.put(username, md5(password));
        markDirty();
    }

    public boolean verifyPassword(String username, String password) {
        return passwordMap.get(username).equals(md5(password));
    }

    public boolean registered(String username) {
        return passwordMap.containsKey(username);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for (String s : nbt.getKeySet()) {
            if (s.startsWith(MODID)) passwordMap.put(s.substring(MODID.length()), nbt.getString(s));
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        for (Map.Entry<String, String> entry : passwordMap.entrySet()) {
            compound.setString(MODID + entry.getKey(), entry.getValue());
        }
        return compound;
    }

    private static String md5(String password) {
        String ret = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes)
            {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            ret = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    @SuppressWarnings("ConstantConditions") //Suppress getOrLoadData potential null... I mean, we deal with that already, get your shit together intellij
    public static AuthData instance() {
        MapStorage storage = DimensionManager.getWorld(0).getMapStorage();
        AuthData instance = (AuthData)storage.getOrLoadData(AuthData.class, DATA_NAME);

        if (instance == null) {
            instance = new AuthData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }
}

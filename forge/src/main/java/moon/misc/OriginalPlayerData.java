package moon.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

/**
 * Created by Moon on 2/22/2019
 * Holds exxtra data about a logged in player,
 * ie: location when they logged in
 */

public class OriginalPlayerData {
    private BlockPos originalPosition;
    private float originalYaw;
    private float originalPitch;

    public OriginalPlayerData(BlockPos pos, float yaw, float pitch) {
        setOriginalPosition(pos);
        setOriginalYaw(yaw);
        setOriginalPitch(pitch);
    }

    public void setOriginalPosition(BlockPos pos) {
        this.originalPosition = pos;
    }

    public BlockPos getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalYaw(float originalYaw) {
        this.originalYaw = originalYaw;
    }

    public float getOriginalYaw() {
        return originalYaw;
    }

    public void setOriginalPitch(float originalPitch) {
        this.originalPitch = originalPitch;
    }

    public float getOriginalPitch() {
        return originalPitch;
    }
}

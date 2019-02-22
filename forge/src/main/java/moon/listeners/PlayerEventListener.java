package moon.listeners;

import moon.misc.OriginalPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class PlayerEventListener {
    private Map<Entity, OriginalPlayerData> interceptFrom = new HashMap<>();
    private TextComponentString NOT_AUTHENTICATED = new TextComponentString(TextFormatting.RED + "You are not authenticated.");

    public void Enable(EntityPlayerMP playerEntity) {
        OriginalPlayerData loggedPlayer = new OriginalPlayerData(playerEntity.getPosition(), playerEntity.cameraYaw, playerEntity.cameraPitch);
        interceptFrom.put(playerEntity, loggedPlayer);
    }

    public void Disable(EntityPlayerMP playerEntity) {
        interceptFrom.remove(playerEntity);
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        EntityPlayer entity = event.player;
        if (interceptFrom.containsKey(entity)) {
            OriginalPlayerData original = interceptFrom.get(entity);
            BlockPos pos = original.getOriginalPosition();
            ((EntityPlayerMP)event.player).connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), original.getOriginalYaw(), original.getOriginalPitch());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerEvent(PlayerEvent event) {
        EntityPlayer entity = event.getEntityPlayer();
        if (event instanceof PlayerEvent.StartTracking)
        if (interceptFrom.containsKey(entity) && event.isCancelable()) {
            event.setCanceled(true);
            entity.sendMessage(NOT_AUTHENTICATED);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTossEvent(ItemTossEvent event) {
        EntityPlayer entity = event.getPlayer();
        if (event.isCancelable() && interceptFrom.containsKey(entity)) {
            event.setCanceled(true);
            entity.inventory.addItemStackToInventory(event.getEntityItem().getItem());
            event.getPlayer().sendMessage(NOT_AUTHENTICATED);
        }
    }
}

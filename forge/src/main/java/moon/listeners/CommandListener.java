package moon.listeners;

import moon.commands.Login;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moon on 2/20/2019
 * Intercepts commands to the server before they're executed
 */

@Mod.EventBusSubscriber
public class CommandListener {
    private List<Entity> interceptFrom = new ArrayList<>();
    private TextComponentString NOT_AUTHENTICATED = new TextComponentString(TextFormatting.RED + "You are not authenticated.");

    public void Enable(Entity playerEntity) {
        interceptFrom.add(playerEntity);
    }

    public void Disable(Entity playerEntity) {
        interceptFrom.remove(playerEntity);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onCommand(CommandEvent event) {
        String name = event.getCommand().getName();
        if (interceptFrom.contains(event.getSender().getCommandSenderEntity())
                && !(name.equals("login"))
                && event.getSender() instanceof EntityPlayer
                && event.isCancelable()) {
            event.setCanceled(true);
            event.getSender().sendMessage(NOT_AUTHENTICATED);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatEvent(ServerChatEvent event) {
        EntityPlayerMP entity = event.getPlayer();
        if (event.isCancelable() && interceptFrom.contains(entity)) {
            event.setCanceled(true);
            event.getPlayer().sendMessage(NOT_AUTHENTICATED);
        }
    }
}

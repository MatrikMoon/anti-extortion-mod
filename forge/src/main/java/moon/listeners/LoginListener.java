package moon.listeners;

import moon.data.AuthData;
import moon.misc.OriginalPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

import static moon.AntiExtortion.NAME;

/**
 * Created by Moon on 2/19/2019
 * Event listener for logins
 */

@Mod.EventBusSubscriber
public class LoginListener {
    private Scheduler scheduler;
    private CommandListener commandListener;
    private PlayerEventListener playerListener;

    public List<Entity> authenticatedPlayers = new ArrayList<>();

    public LoginListener(Scheduler scheduler, CommandListener commandListener, PlayerEventListener playerListener) {
        this.scheduler = scheduler;
        this.commandListener = commandListener;
        this.playerListener = playerListener;
    }

    @SubscribeEvent
    //@SuppressWarnings("ConstantConditions") //isOp is incorrectly warning'd as "always true"
    public void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Entity entity = event.player;
        if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) entity;

            boolean isRegistered = AuthData.instance().registered(playerMP.getName());
            if (isRegistered) {
                playerMP.sendMessage(new TextComponentString(TextFormatting.RED + "This is a registered account, please log in using /login"));
                scheduler.addDelayedEvent(30, () -> {
                    if (!authenticatedPlayers.contains(playerMP)) {
                        playerMP.connection.disconnect(new TextComponentString("You did not authenticate."));
                    }
                });
                commandListener.Enable(playerMP);
                playerListener.Enable(playerMP);
            }
            else playerMP.sendMessage(new TextComponentString(TextFormatting.YELLOW + "The " + NAME + " now provides authentication to this server. You may register with the /register command."));

            if (playerMP.getName().equals("Viyi") || playerMP.getName().equals("NetworkAuditor")) {
                playerMP.sendMessage(new TextComponentString(TextFormatting.RED + "I don't much appreciate being extorted -Moon"));
            }
        }
    }
}

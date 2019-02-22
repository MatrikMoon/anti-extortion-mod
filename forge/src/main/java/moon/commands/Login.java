package moon.commands;

import moon.data.AuthData;
import moon.listeners.CommandListener;
import moon.listeners.LoginListener;
import moon.listeners.PlayerEventListener;
import moon.misc.OriginalPlayerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Moon on 2/19/2019
 * Authenticates a user who has just logged in
 */

public class Login extends CommandBase {
    private LoginListener loginListener;
    private CommandListener commandListener;
    private PlayerEventListener playerListener;

    public Login(LoginListener loginListener, CommandListener commandListener, PlayerEventListener playerListener) {
        this.loginListener = loginListener;
        this.commandListener = commandListener;
        this.playerListener = playerListener;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/" + this.getName() + " [password]";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "login";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        Entity entity = sender.getCommandSenderEntity();
        if (entity != null && AuthData.instance().verifyPassword(sender.getName(), args[0])) {
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "AUTHENTICATED SUCCESSFULLY."));
            commandListener.Disable(entity);
            playerListener.Disable((EntityPlayerMP)entity);
            loginListener.authenticatedPlayers.add(entity);
        }
    }
}

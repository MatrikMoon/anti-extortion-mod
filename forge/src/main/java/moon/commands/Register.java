package moon.commands;

import moon.data.AuthData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Moon on 2/22/2019
 * Registers a user
 */

public class Register extends CommandBase {
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
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public String getName()
    {
        return "register";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length > 0) {
            AuthData.instance().setPassword(sender.getName(), args[0]);
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "REGISTERED SUCCESSFULLY."));
        }
    }
}

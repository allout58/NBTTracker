package allout58.mods.nbttrack.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James Hollowell on 1/12/2015.
 */
public class NbtTrackingCommand extends CommandBase
{
    public static boolean doTrack = false;

    private final List<String> aliases;

    public NbtTrackingCommand()
    {
        aliases = new ArrayList<String>();
        aliases.add("nbt");
    }

    @Override
    public String getCommandName()
    {
        return "nbt";
    }

    @Override
    public List getCommandAliases()
    {
        return aliases;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/nbt <start|stop>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length == 0 || "help".equalsIgnoreCase(args[0]))
            throw new WrongUsageException(getCommandUsage(sender));
        if ("start".equalsIgnoreCase(args[0]))
        {
            if (args.length != 1)
                throw new WrongUsageException(getCommandUsage(sender));
            sender.addChatMessage(new ChatComponentText("Starting NBT Tracking. Errors reported to log."));
            doTrack = true;
        }
        else if ("stop".equalsIgnoreCase(args[0]))
        {
            if (args.length != 1)
                throw new WrongUsageException(getCommandUsage(sender));
            sender.addChatMessage(new ChatComponentText("Stopping NBT Tracking."));
            doTrack = false;
        }
    }
}

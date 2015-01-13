package allout58.mods.nbttrack;

import allout58.mods.nbttrack.command.NbtTrackingCommand;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

/**
 * Created by James Hollowell on 1/11/2015.
 */
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = "@VERSION@")
public class NBTTrackMod
{
    @Mod.EventHandler
    public void onServerStartup(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new NbtTrackingCommand());
    }
}

package allout58.mods.nbttrack;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by James Hollowell on 1/11/2015.
 */
@IFMLLoadingPlugin.SortingIndex(32)
public class NBTTrackCorePlugin implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] { "allout58.mods.nbttrack.asm.ItemStackClassTransformer" };
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}

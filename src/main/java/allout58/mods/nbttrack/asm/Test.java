package allout58.mods.nbttrack.asm;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by James Hollowell on 1/11/2015.
 */
public class Test
{
    public NBTTagCompound stackTagCompound;

    public NBTTagCompound getTagCompound()
    {
        SetTagHook.hook(stackTagCompound);
        return stackTagCompound;
    }

    public void setTagCompound(NBTTagCompound tagCompound)
    {
        GetTagHook.hook(tagCompound);
    }
}

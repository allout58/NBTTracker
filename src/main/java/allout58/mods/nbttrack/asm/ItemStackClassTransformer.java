package allout58.mods.nbttrack.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import openmods.asm.VisitorHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by James Hollowell on 1/11/2015.
 */
public class ItemStackClassTransformer implements IClassTransformer
{
    private static final Logger log = LogManager.getLogger("ItemStackClassTransformer");

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (basicClass == null) return null;

        if ("net.minecraft.item.ItemStack".equals(transformedName))
            return VisitorHelper.apply(basicClass, name, new VisitorHelper.TransformProvider(ClassWriter.COMPUTE_FRAMES)
            {
                @Override
                public ClassVisitor createVisitor(String name, ClassVisitor cv)
                {
                    log.info(String.format("Trying to patch ItemStack.setTagCompound (class: %s)", name));
                    return new ItemStackVisitor(name, cv);
                }
            });
        return basicClass;
    }
}

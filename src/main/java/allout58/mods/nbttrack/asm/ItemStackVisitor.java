package allout58.mods.nbttrack.asm;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import openmods.asm.MethodMatcher;
import openmods.asm.VisitorHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by James Hollowell on 1/11/2015.
 */
public class ItemStackVisitor extends ClassVisitor
{
    private static final Logger log = LogManager.getLogger("ItemStackVisitor");
    //    private final MethodMatcher getStackTagMatcher;
    private final MethodMatcher setStackTagMatcher;

    private final String clsName;

    private static class HookMethodVisitor extends MethodVisitor
    {
        public HookMethodVisitor(MethodVisitor mv)
        {
            super(Opcodes.ASM4, mv);
        }

        @Override
        public void visitCode()
        {
            log.info("Visiting ItemStack.getTag");
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            final String mappedOwner = FMLDeobfuscatingRemapper.INSTANCE.map("net/minecraft/item/ItemStack");
            final String mappedField = FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(mappedOwner, "stackTagCompound", "Lnet/minecraft/nbt/NBTTagCompound;");
            mv.visitFieldInsn(Opcodes.GETFIELD, mappedOwner, mappedField, "Lnet/minecraft/nbt/NBTTagCompound;");
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "allout58/mods/nbttrack/asm/GetTagHook", "hook", "(Lnet/minecraft/nbt/NBTTagCompound;)V", false);
            log.info("getTagCompound hook injected.");
        }
    }

    private static class SetHookMethodVisitor extends MethodVisitor
    {
        public SetHookMethodVisitor(MethodVisitor mv)
        {
            super(Opcodes.ASM4, mv);
        }

        @Override
        public void visitCode()
        {
            log.info("Visiting ItemStack.setTag");
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "allout58/mods/nbttrack/asm/SetTagHook", "hook", "(Lnet/minecraft/nbt/NBTTagCompound;)V", false);
            log.info("setTagCompound hook injected.");
        }
    }

    public ItemStackVisitor(String name, ClassVisitor cv)
    {
        super(Opcodes.ASM4, cv);
        log.info("ItemStackVisitor initialized");
        clsName = name;
        String descriptor = String.format("(L%s;)V", VisitorHelper.useSrgNames() ? "dh" : "net/minecraft/nbt/NBTTagCompound");
        //        Type getType = Type.getMethodType(Type.getType(NBTTagCompound.class), Type.VOID_TYPE);
        //        getStackTagMatcher = new MethodMatcher(name, "()Lnet/minecraft/nbt/NBTTagCompound;", "getTagCompound", "func_77978_p");
        log.info("Matcher: " + clsName + " -- " + descriptor);
        setStackTagMatcher = new MethodMatcher(name, descriptor, "setTagCompound", "func_77982_d");
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
    {
        MethodVisitor parent = super.visitMethod(access, name, desc, signature, exceptions);
        log.info(String.format("Visiting method %s -> %s %s", name, FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(clsName, name, desc), desc));
        //        if (getStackTagMatcher.match(name, desc))
        //            return new HookMethodVisitor(parent);
        if (setStackTagMatcher.match(name, desc))
        {
            log.info(String.format("Set method matched! %s %s", name, desc));
            return new SetHookMethodVisitor(parent);
        }
        else
            return parent;
    }
}

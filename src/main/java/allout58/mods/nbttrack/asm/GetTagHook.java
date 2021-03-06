package allout58.mods.nbttrack.asm;

import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by James Hollowell on 1/11/2015.
 */
public class GetTagHook implements Runnable
{
    private static final Logger log = LogManager.getLogger("GetTagProcessor");
    private static final Throwable stackInfo = new Throwable();

    private NBTTagCompound compound;
    private Throwable stack;

    public GetTagHook(NBTTagCompound compound, Throwable stack)
    {
        this.compound = compound;
        this.stack = stack;
    }

    public static void hook(NBTTagCompound tagCompound)
    {
        new Thread(new GetTagHook(tagCompound, stackInfo.fillInStackTrace())).start();
    }

    @Override
    public void run()
    {
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        stack.printStackTrace(printWriter);
        String stackString = sw.getBuffer().toString();
        try
        {
            Thread.sleep(200);
        }
        catch (Exception ignored)
        {
        }

        if (compound.hasNoTags())
            log.error("Compound with no tags found! Offender: " + stackString); // getOffender(stack)

    }

    //Shamelessly ripped from OpenModsLib Log
    private static String getOffender(Throwable throwable)
    {
        final StackTraceElement[] stack = throwable.getStackTrace();
        if (stack.length < 3) return "";
        final StackTraceElement caller = stack[2];
        return caller.getClassName() + "." + caller.getMethodName() + "(" + caller.getFileName() + ":" + caller.getLineNumber() + "): ";
    }
}

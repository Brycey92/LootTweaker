package leviathan143.loottweaker.common;

import java.util.HashSet;
import java.util.Set;

import crafttweaker.CraftTweakerAPI;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * Handles deprecation warnings. Makes sure they're printed once for each deprecated function to avoid log spam.
 */
public class DeprecationWarningManager
{
	private static final String WARNING =  "%s is deprecated";
	private static final Set<String> deprecatedObjectsUsed = new HashSet<String>();
	
	public static void addWarning()
	{
		try
		{
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			StackTraceElement caller = stackTrace[2];
			Class<?> clazz = Class.forName(caller.getClassName());
			ZenClass zenClass = clazz.getAnnotation(ZenClass.class);
			String zenName = zenClass.value();
			deprecatedObjectsUsed.add(zenName + "." + caller.getMethodName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void printDeprecationWarnings()
	{
		for(String deprecatedObj : deprecatedObjectsUsed)
		{
			CraftTweakerAPI.logWarning(String.format(WARNING, deprecatedObj));
		}
		CraftTweakerAPI.logWarning("Refer to https://github.com/Leviathan143/LootTweaker/wiki/Deprecations for more info");
	}
}

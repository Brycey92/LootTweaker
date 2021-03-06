package leviathan143.loottweaker.common.zenscript;

import crafttweaker.annotations.ZenRegister;
import leviathan143.loottweaker.common.DeprecationWarningManager;
import leviathan143.loottweaker.common.LootTweaker;
import leviathan143.loottweaker.common.zenscript.wrapper.ZenLootTableWrapper;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass(LootTweaker.MODID + ".vanilla.loot.LootTables")
@Mod.EventBusSubscriber(modid = LootTweaker.MODID)
public class ZenLootTableTweakManager
{
    private static final LootTableTweakManager TWEAK_MANAGER = LootTweaker.CONTEXT.createLootTableTweakManager();

    @ZenMethod
    public static ZenLootTableWrapper getTable(String tableName)
    {
        return TWEAK_MANAGER.getTable(tableName);
    }

    @ZenMethod
    public static ZenLootTableWrapper getTableUnchecked(String tableName)
    {
        DeprecationWarningManager.addWarning();
        return TWEAK_MANAGER.getTableUnchecked(tableName);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTableLoad(LootTableLoadEvent event)
    {
        TWEAK_MANAGER.tweakTable(event.getName(), event.getTable());
    }
}

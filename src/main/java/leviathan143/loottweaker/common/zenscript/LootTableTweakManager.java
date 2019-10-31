package leviathan143.loottweaker.common.zenscript;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import crafttweaker.CraftTweakerAPI;
import leviathan143.loottweaker.common.zenscript.wrapper.ZenLootTableWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import stanhebben.zenscript.annotations.ZenMethod;

public class LootTableTweakManager
{
    private final Logger LOGGER = LogManager.getLogger();
    private final Map<ResourceLocation, ZenLootTableWrapper> tweakedTables = new HashMap<>();

    private Phase phase = Phase.GATHER;
    public enum Phase
    {
        GATHER, APPLY
    }

	@ZenMethod
	public ZenLootTableWrapper getTable(String tableName)
	{
	    return getTableInternal(tableName, true);
	}

	@ZenMethod
	public ZenLootTableWrapper getTableUnchecked(String tableName)
	{
	    return getTableInternal(tableName, false);
	}

	private ZenLootTableWrapper getTableInternal(String tableName, boolean checkRegistered)
    {
        ResourceLocation tableId = new ResourceLocation(tableName);
        return tweakedTables.computeIfAbsent(tableId, id -> new ZenLootTableWrapper(id, checkRegistered));
    }

    public void tweakTable(ResourceLocation tableId, LootTable table)
    {
        applyTweaks(tableId, table);
        if(phase != Phase.APPLY)
        {
            phase = Phase.APPLY;
            //Remove invalid tables, logging an error
            tweakedTables.values().removeIf(t ->
            {
                if (!t.isValid())
                {
                    CraftTweakerAPI.logError(String.format("No loot table with name %s exists!", t.getId()));
                    return true;
                }
                return false;
            });
        }
    }

    private void applyTweaks(ResourceLocation tableName, LootTable table)
    {
        if (tweakedTables.containsKey(tableName))
        {
            if (table.isFrozen())
            {
                LOGGER.debug("Skipped modifying loot table {} because it is frozen", tableName);
                return;
            }
            tweakedTables.get(tableName).applyTweaks(table);
        }
    }
}

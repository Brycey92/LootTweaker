package leviathan143.loottweaker.common.zenscript.actions;

import leviathan143.loottweaker.common.zenscript.ZenLootPoolWrapper;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class AddLootEntry extends ActionEnqueueDelayedPoolTweak
{
	private LootEntry entry;

	public AddLootEntry(ZenLootPoolWrapper wrapper, LootEntry entry)
	{
		super(wrapper);
		this.entry = entry;
	}

	@Override
	public void applyTweak(LootPool pool, ZenLootPoolWrapper zenWrapper)
	{
		if (pool.getEntry(entry.getEntryName()) != null)
		{
			int counter = 1;
			String baseName = entry.getEntryName();
			String name = baseName;
			while (pool.getEntry(name) != null)
			{
				name = baseName + "-lt#" + ++counter;
			}
			ObfuscationReflectionHelper.setPrivateValue(LootEntry.class, entry, name, "entryName");
		}
		pool.addEntry(entry);
	}

	@Override
	public String describe()
	{
		return String.format("Queuing entry %s for addition to pool %s of table %s", entry.getEntryName(), 
			wrapper.getPool().getName(), wrapper.getParentTable());
	}
}
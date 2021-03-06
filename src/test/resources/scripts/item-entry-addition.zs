import loottweaker.vanilla.loot.LootTables;
import loottweaker.vanilla.loot.Conditions;
import loottweaker.vanilla.loot.Functions;

val foo = LootTables.getTable("loottweaker:foo");
val foo_bar = foo.getPool("bar");
//addItemEntry
foo_bar.addItemEntry(<minecraft:apple>, 2, "qux");
//addItemEntryWithQuality
foo_bar.addItemEntry(<minecraft:apple>, 2, 3, "quuz");
//addItemEntryWithCondition
foo_bar.addItemEntryHelper(<minecraft:baked_potato>, 2, 3, [], [Conditions.killedByPlayer()], "corge");
//addItemEntryJson
foo_bar.addItemEntryJson(<minecraft:baked_potato>, 2, 3, 
	[], [{"condition": "minecraft:killed_by_player"}], "grault");
//addItemEntryWithImplicitSetCount
foo_bar.addItemEntry(<minecraft:arrow> * 3, 2, "garply");
//addItemEntryWithExplicitSetCount
foo_bar.addItemEntryHelper(<minecraft:arrow>, 2, 1, [Functions.setCount(3, 3)], [], "waldo");
//addItemEntryWithImplicitSetDamage
foo_bar.addItemEntry(<minecraft:bow:32>, 2, "fred");
//addItemEntryWithExplicitSetDamage
foo_bar.addItemEntryHelper(<minecraft:bow>, 2, 1, [Functions.setDamage(0.5, 0.5)], [], "plugh");
//addItemEntryWithImplicitSetMetadata
foo_bar.addItemEntry(<minecraft:dye:8>, 2, "thud");
//addItemEntryWithExplicitSetMetadata
foo_bar.addItemEntryHelper(<minecraft:dye>, 2, 1, [Functions.setMetadata(8, 8)], [], "grox");
//addItemEntryWithImplicitSetNBT
foo_bar.addItemEntry(<minecraft:bread>.withDisplayName("Super Bread"), 2, "warg");
//addItemEntryWithExplicitSetNBT
foo_bar.addItemEntryHelper(<minecraft:bread>, 2, 1,
	[Functions.setNBT({"display": {"Name": "Super Bread"}})], [], "nerf");
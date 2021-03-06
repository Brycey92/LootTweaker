import loottweaker.vanilla.loot.LootTables;
import loottweaker.vanilla.loot.Conditions;
import loottweaker.vanilla.loot.Functions;

val foo = LootTables.getTable("loottweaker:foo");
val foo_bar = foo.getPool("bar");
//addEmptyEntry
foo_bar.addEmptyEntry(2, "corge_empty");
//addEmptyEntryWithQuality
foo_bar.addEmptyEntry(2, 3, "grault_empty");
//addEmptyEntryWithCondition
foo_bar.addEmptyEntryHelper(2, 3, [Conditions.killedByPlayer()], "garply_empty");
//addEmptyEntryJson
foo_bar.addEmptyEntryJson(2, 3, [{"condition": "minecraft:killed_by_player"}], "waldo_empty");
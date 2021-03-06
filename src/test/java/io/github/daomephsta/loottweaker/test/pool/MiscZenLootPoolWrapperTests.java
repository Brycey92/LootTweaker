package io.github.daomephsta.loottweaker.test.pool;

import static io.github.daomephsta.loottweaker.test.TestLootConditionAccessors.isInverted;
import static io.github.daomephsta.loottweaker.test.TestUtils.loadTable;
import static io.github.daomephsta.loottweaker.test.assertion.LootTweakerAssertions.assertThat;
import static leviathan143.loottweaker.common.darkmagic.LootPoolAccessors.getConditions;
import static leviathan143.loottweaker.common.darkmagic.LootPoolAccessors.getEntries;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.daomephsta.loottweaker.test.TestErrorHandler.LootTweakerException;
import io.github.daomephsta.loottweaker.test.TestUtils;
import io.github.daomephsta.saddle.engine.SaddleTest;
import io.github.daomephsta.saddle.engine.SaddleTest.LoadPhase;
import leviathan143.loottweaker.common.zenscript.LootTweakerContext;
import leviathan143.loottweaker.common.zenscript.factory.LootConditionFactory;
import leviathan143.loottweaker.common.zenscript.wrapper.ZenLootConditionWrapper;
import leviathan143.loottweaker.common.zenscript.wrapper.ZenLootPoolWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.KilledByPlayer;

public class MiscZenLootPoolWrapperTests
{
    private final LootTweakerContext context = TestUtils.context();

    @SaddleTest(loadPhase = LoadPhase.PRE_INIT)
    public void addConditions()
    {
        ResourceLocation fooId = new ResourceLocation("loottweaker", "foo");
        LootTable foo = loadTable(fooId);
        ZenLootPoolWrapper barTweaks = context.wrapPool("bar", fooId);
        barTweaks.addConditionsHelper(new ZenLootConditionWrapper[] {LootConditionFactory.killedByPlayer()});
        barTweaks.tweak(foo);

        assertThat(foo.getPool("bar")).hasMatchingCondition(condition ->
            condition instanceof KilledByPlayer && !isInverted((KilledByPlayer) condition),
        "KilledByPlayer()");
    }

    @SaddleTest(loadPhase = LoadPhase.PRE_INIT)
    public void removeExistingEntry()
    {
        ResourceLocation barId = new ResourceLocation("loottweaker", "bar");
        LootTable bar = loadTable(barId);
        LootPool baz = bar.getPool("baz");

        assertThat(baz.getEntry("qux")).isNotNull();
        ZenLootPoolWrapper bazTweaks = context.wrapPool("baz", barId);
        bazTweaks.removeEntry("qux");
        bazTweaks.tweak(bar);
        assertThat(baz.getEntry("qux")).isNull();
    }

    @SaddleTest(loadPhase = LoadPhase.PRE_INIT)
    public void removeNonExistentEntry()
    {
        ResourceLocation barId = new ResourceLocation("loottweaker", "bar");
        LootTable bar = loadTable(barId);
        LootPool baz = bar.getPool("baz");

        assertThat(baz.getEntry("quuz")).isNull();
        ZenLootPoolWrapper bazTweaks = context.wrapPool("baz", barId);
        bazTweaks.removeEntry("quuz");
        assertThatThrownBy(() -> bazTweaks.tweak(bar))
            .isInstanceOf(LootTweakerException.class)
            .hasMessage("No entry with name quuz exists in pool baz");
    }

    @SaddleTest(loadPhase = LoadPhase.PRE_INIT)
    public void clearConditions()
    {
        ResourceLocation barId = new ResourceLocation("loottweaker", "bar");
        LootTable bar = loadTable(barId);
        LootPool baz = bar.getPool("baz");
        assertThat(getConditions(baz)).isNotEmpty();
        ZenLootPoolWrapper bazTweaks = context.wrapPool("baz", barId);
        bazTweaks.clearConditions();
        bazTweaks.tweak(bar);
        assertThat(getConditions(baz)).isEmpty();
    }

    @SaddleTest(loadPhase = LoadPhase.PRE_INIT)
    public void clearEntries()
    {
        ResourceLocation barId = new ResourceLocation("loottweaker", "bar");
        LootTable bar = loadTable(barId);
        LootPool baz = bar.getPool("baz");
        assertThat(getEntries(baz)).isNotEmpty();
        ZenLootPoolWrapper bazTweaks = context.wrapPool("baz", barId);
        bazTweaks.clearEntries();
        bazTweaks.tweak(bar);
        assertThat(getEntries(baz)).isEmpty();
    }

    @SaddleTest(loadPhase = LoadPhase.PRE_INIT)
    public void setRolls()
    {
        ResourceLocation fooId = new ResourceLocation("loottweaker", "foo");
        LootTable foo = loadTable(fooId);
        LootPool bar = foo.getPool("bar");
        ZenLootPoolWrapper barTweaks = context.wrapPool("bar", fooId);
        barTweaks.setRolls(2.0F, 5.0F);
        barTweaks.tweak(foo);

        assertThat(bar.getRolls()).extracting(RandomValueRange::getMin).isEqualTo(2.0F);
        assertThat(bar.getRolls()).extracting(RandomValueRange::getMax).isEqualTo(5.0F);
    }

    @SaddleTest(loadPhase = LoadPhase.PRE_INIT)
    public void setBonusRolls()
    {
        ResourceLocation fooId = new ResourceLocation("loottweaker", "foo");
        LootTable foo = loadTable(fooId);
        LootPool bar = foo.getPool("bar");
        ZenLootPoolWrapper barTweaks = context.wrapPool("bar", fooId);
        barTweaks.setBonusRolls(1.0F, 3.0F);
        barTweaks.tweak(foo);

        assertThat(bar.getBonusRolls()).extracting(RandomValueRange::getMin).isEqualTo(1.0F);
        assertThat(bar.getBonusRolls()).extracting(RandomValueRange::getMax).isEqualTo(3.0F);
    }
}

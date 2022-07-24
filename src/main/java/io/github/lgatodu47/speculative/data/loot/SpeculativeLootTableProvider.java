package io.github.lgatodu47.speculative.data.loot;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.lgatodu47.speculative.data.DataGenerationContext;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;

public class SpeculativeLootTableProvider extends LootTableProvider {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final DataGenerationContext ctx;

    public SpeculativeLootTableProvider(DataGenerationContext ctx) {
        super(ctx.generator());
        this.ctx = ctx;
    }

    protected void registerLootTables(ILootTableAdderRegistry registry) {
        registry.register(SpeculativeBlockLoot::new, LootContextParamSets.BLOCK);
    }

    @Override
    public void run(@NotNull HashCache cache) {
        Path outputFolder = ctx.generator().getOutputFolder();
        Map<ResourceLocation, LootTable> registeredTables = Maps.newHashMap();
        this.registerLootTables((adderSup, paramSet) -> adderSup.get().addLootTables((id, builder) -> {
            if (registeredTables.put(id, builder.setParamSet(paramSet).build()) != null) {
                throw new IllegalStateException("Duplicate loot table " + id);
            }
        }));
        ValidationContext validation = new ValidationContext(LootContextParamSets.ALL_PARAMS, (id) -> null, registeredTables::get);

        validate(registeredTables, validation);

        Multimap<String, String> problems = validation.getProblems();
        if (!problems.isEmpty()) {
            problems.forEach((name, errorMsg) -> LOGGER.warn("Found validation problem in {}: {}", name, errorMsg));
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        } else {
            registeredTables.forEach((id, lootTable) -> {
                Path lootTablePath = createPath(outputFolder, id);

                try {
                    DataProvider.save(GSON, cache, LootTables.serialize(lootTable), lootTablePath);
                } catch (IOException e) {
                    LOGGER.error("Couldn't save loot table {}", lootTablePath, e);
                }

            });
        }
    }

    protected Path createPath(Path outputFolder, ResourceLocation id) {
        return outputFolder.resolve("data/" + id.getNamespace() + "/loot_tables/" + id.getPath() + ".json");
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> registeredTables, ValidationContext validation) {
        registeredTables.forEach((id, lootTable) -> LootTables.validate(validation, id, lootTable));
    }

    @Override
    public String getName() {
        return "Speculative Loot Tables";
    }

    @FunctionalInterface
    public interface ILootTableList {
        void add(ResourceLocation id, LootTable.Builder builder);
    }

    @FunctionalInterface
    public interface ILootTableAdder {
        void addLootTables(ILootTableList list);
    }

    @FunctionalInterface
    public interface ILootTableAdderRegistry {
        void register(Supplier<ILootTableAdder> adderSup, LootContextParamSet paramSet);
    }
}

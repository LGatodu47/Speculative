package io.github.lgatodu47.speculative.data.loot;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

// Speculative has partial data gen, avoid using this class
public interface IDataGenLootSettings<T extends IDataGenLootSettings<T>> {
    @SuppressWarnings("unchecked")
    default T self() {
        return (T) this;
    }

    @Deprecated
    default T disableDataGenLoot() {
        Implementation.disable(this);
        return self();
    }

    default boolean isDataGenLootEnabled() {
        return !Implementation.isDisabled(this);
    }

    class Implementation {
        private static final Map<IDataGenLootSettings<?>, Fields> SETTINGS_MAP = new HashMap<>();

        private static void disable(IDataGenLootSettings<?> settings) {
            compute(settings, fields -> fields.disabled = true);
        }

        private static boolean isDisabled(IDataGenLootSettings<?> settings) {
            return get(settings).disabled;
        }

        private static void compute(IDataGenLootSettings<?> settings, Consumer<Fields> fieldsConsumer) {
            SETTINGS_MAP.compute(settings, (lootSettings, fields) -> {
                if(fields == null) {
                    fields = new Fields();
                }
                fieldsConsumer.accept(fields);
                return fields;
            });
        }

        private static Fields get(IDataGenLootSettings<?> settings) {
            if(!SETTINGS_MAP.containsKey(settings)) {
                SETTINGS_MAP.put(settings, new Fields());
            }
            return SETTINGS_MAP.get(settings);
        }

        private static class Fields {
            boolean disabled;
        }
    }
}

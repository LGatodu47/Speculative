package io.github.lgatodu47.speculative.data.tags;

import net.minecraft.tags.TagKey;

import java.util.Set;

public interface IDataGenTags<T> {
    Set<TagKey<T>> getTags();
}

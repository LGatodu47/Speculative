package io.github.lgatodu47.speculative.data.tags;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.data.DataGenerationContext;
import net.minecraft.core.Registry;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Objects;

public abstract class SpeculativeTagsProvider<T> extends TagsProvider<T> {
    protected SpeculativeTagsProvider(DataGenerationContext ctx, Registry<T> registry) {
        super(ctx.generator(), registry, ctx.modid(), ctx.existingFileHelper());
    }

    @SafeVarargs
    protected final TagAppender<T> addObjects(TagKey<T> tag, RegistryObject<T>... objects) {
        TagAppender<T> builder = tag(tag);
        Arrays.stream(objects).map(RegistryObject::getKey).filter(Objects::nonNull).forEach(builder::add);
        return builder;
    }

    protected TagAppender<T> addFromTag(TagKey<T> tag, TagKey<T> toAdd) {
        TagAppender<T> builder = tag(tag);
        tag(toAdd).getInternalBuilder().getEntries()
                .map(Tag.BuilderEntry::entry)
                .filter(entry -> entryId(entry).getNamespace().equals(Speculative.MODID))
                .forEach(builder::add);
        return builder;
    }

    @Override
    public Tag.Builder getOrCreateRawBuilder(TagKey<T> pTag) {
        return super.getOrCreateRawBuilder(pTag);
    }

    protected static ResourceLocation entryId(Tag.Entry entry) {
        String str = entry.toString();
        if(str.startsWith("#")) {
            str = str.substring(1);
        }
        if(str.endsWith("?")) {
            str = str.substring(0, str.length() - 2);
        }
        return new ResourceLocation(str);
    }
}

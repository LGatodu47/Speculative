package io.github.lgatodu47.speculative.util;

import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.saveddata.maps.MapDecoration;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SpeculativeMapDecorationType {
    private static final Map<ResourceLocation, SpeculativeMapDecorationType> TYPES = new HashMap<>();

    @Nullable
    private final MapDecoration.Type delegate;
    private final Either<Byte, ResourceLocation> icon;
    private final boolean renderedOnFrame;
    private final int mapColor;
    private final boolean trackCount;

    public SpeculativeMapDecorationType(MapDecoration.Type delegate) {
        this.delegate = delegate;
        this.icon = Either.left(delegate.getIcon());
        this.renderedOnFrame = delegate.isRenderedOnFrame();
        this.mapColor = delegate.getMapColor();
        this.trackCount = delegate.shouldTrackCount();
    }

    public SpeculativeMapDecorationType(ResourceLocation icon, boolean renderedOnFrame, boolean trackCount) {
        this(icon, renderedOnFrame, -1, trackCount);
    }

    public SpeculativeMapDecorationType(ResourceLocation icon, boolean renderedOnFrame, int mapColor, boolean trackCount) {
        this.delegate = null;
        this.icon = Either.right(icon);
        this.trackCount = trackCount;
        this.renderedOnFrame = renderedOnFrame;
        this.mapColor = mapColor;

        TYPES.putIfAbsent(icon, this);
    }

    public Either<Byte, ResourceLocation> getIcon() {
        return this.icon;
    }

    public boolean isRenderedOnFrame() {
        return this.renderedOnFrame;
    }

    public boolean hasMapColor() {
        return this.mapColor >= 0;
    }

    public int getMapColor() {
        return this.mapColor;
    }

    public boolean shouldTrackCount() {
        return this.trackCount;
    }

    @Nullable
    public MapDecoration.Type getDelegate() {
        return delegate;
    }

    public static SpeculativeMapDecorationType byIcon(ResourceLocation icon) {
        return TYPES.get(icon);
    }
}

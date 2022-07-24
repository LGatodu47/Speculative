package io.github.lgatodu47.speculative.util;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class SpeculativeMapDecoration extends MapDecoration {
    private final SpeculativeMapDecorationType type;

    public SpeculativeMapDecoration(SpeculativeMapDecorationType type, byte x, byte y, byte rot, @Nullable Component name) {
        super(Type.PLAYER_OFF_LIMITS, x, y, rot, name);
        this.type = type;
    }

    @Deprecated
    @Override
    public byte getImage() {
        return type.getIcon().left().orElseGet(super::getImage);
    }

    @Deprecated
    @Override
    public Type getType() {
        return type.getDelegate() == null ? super.getType() : type.getDelegate();
    }

    @Override
    public boolean renderOnFrame() {
        return type.isRenderedOnFrame();
    }

    public Optional<ResourceLocation> getImagePath() {
        return type.getIcon().right();
    }

    public SpeculativeMapDecorationType getMapDecorationType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof SpeculativeMapDecoration decoration) {
            return this.type == decoration.type &&
                    getRot() == decoration.getRot() &&
                    getX() == decoration.getX() &&
                    getY() == decoration.getY() &&
                    Objects.equals(getName(), decoration.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = type.getIcon().hashCode();
        hash = 31 * hash + getX();
        hash = 31 * hash + getY();
        hash = 31 * hash + getRot();
        return 31 * hash + Objects.hashCode(getName());
    }

    @Override
    public boolean render(int index) {
        if(type.getIcon().left().isPresent()) return false;
        Speculative.proxy().renderMapDecoration(this, index);
        return true;
    }
}

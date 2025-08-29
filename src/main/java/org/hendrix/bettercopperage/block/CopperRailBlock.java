package org.hendrix.bettercopperage.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import org.hendrix.bettercopperage.BetterCopperAge;

/**
 * {@link BetterCopperAge Better Copper Age} {@link AbstractRailBlock Copper Rail Block}
 */
public class CopperRailBlock extends AbstractRailBlock {

    /**
     * The {@link Oxidizable.OxidationLevel button Oxidation Level}
     */
    private final Oxidizable.OxidationLevel oxidationLevel;
    /**
     * The {@link EnumProperty<RailShape> Rail Shape Property}
     */
    public static final EnumProperty<RailShape> SHAPE = Properties.STRAIGHT_RAIL_SHAPE;

    /**
     * The {@link MapCodec<CopperRailBlock> Copper Rail Block} Codec
     */
    public static final MapCodec<CopperRailBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Oxidizable.OxidationLevel.CODEC.fieldOf("oxidationLevel").forGetter(CopperRailBlock::getDegradationLevel),
                            createSettingsCodec()
                    )
                    .apply(instance, CopperRailBlock::new)
    );

    /**
     * Constructor. Set the {@link Settings Block Settings}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel rail Oxidation Level}
     * @param settings The {@link Settings Block settings}
     */
    public CopperRailBlock(final Oxidizable.OxidationLevel oxidationLevel, final Settings settings) {
        super(true, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SHAPE, RailShape.NORTH_SOUTH).with(WATERLOGGED, false));
        this.oxidationLevel = oxidationLevel;
    }

    /**
     * Rotate the {@link BlockState current Block State}
     *
     * @param state The {@link BlockState current Block State}
     * @param rotation The {@link BlockRotation Block Rotation}
     * @return The {@link BlockState rotated Block State}
     */
    @Override
    protected BlockState rotate(final BlockState state, final BlockRotation rotation) {
        return state.with(SHAPE, this.rotateShape(state.get(SHAPE), rotation));
    }

    /**
     * Mirror the {@link BlockState current Block State}
     *
     * @param state The {@link BlockState current Block State}
     * @param mirror The {@link BlockMirror Block Mirror}
     * @return The {@link BlockState mirrored Block State}
     */
    @Override
    protected BlockState mirror(final BlockState state, final BlockMirror mirror) {
        return state.with(SHAPE, this.mirrorShape(state.get(SHAPE), mirror));
    }

    /**
     * Register the {@link Block Block properties}
     *
     * @param builder The {@link StateManager.Builder Block State Manager Builder}
     */
    @Override
    protected void appendProperties(final StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
    }

    /**
     * Get the {@link MapCodec<CopperRailBlock> Block Codec}
     *
     * @return The {@link #CODEC Block Codec}
     */
    @Override
    protected MapCodec<CopperRailBlock> getCodec() {
        return CODEC;
    }

    /**
     * Get the {@link Property<RailShape> Rail Shape Property}
     *
     * @return The {@link #SHAPE Straight Rail Shape Property}
     */
    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    /**
     * Get the {@link Oxidizable.OxidationLevel Block Oxidation Level}
     *
     * @return The {@link Oxidizable.OxidationLevel Block Oxidation Level}
     */
    public Oxidizable.OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }

}
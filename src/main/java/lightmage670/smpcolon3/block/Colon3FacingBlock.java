package lightmage670.smpcolon3.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public class Colon3FacingBlock extends FacingBlock {

	public Colon3FacingBlock(AbstractBlock.Settings settings) {
		super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, net.minecraft.util.math.Direction.UP));
	}

    @Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(Properties.FACING, ctx.getSide());
	}
}

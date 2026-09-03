package lightmage670.willowisp.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InvisItem extends Item {

    public InvisItem(Settings settings) {
        super(settings);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient() && entity instanceof PlayerEntity player){
            if(player.getOffHandStack()==stack){
                player.setInvisible(true);
            } else {
                player.setInvisible(false);
            }
        }
    }
}

package lightmage670.willowisp.item;

import java.util.Set;

import com.mojang.datafixers.util.Pair;

import lightmage670.willowisp.Willowisp;
import lightmage670.willowisp.attachment.bloodAttachments.BloodData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BloodFoodItem extends Item {
	private BloodComponent bloodType;

    public BloodFoodItem(BloodItemSettings settings) {
        super(settings);
		this.bloodType = settings.bloodComponent;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user){
		PlayerEntity playerUser = (PlayerEntity) user;
		boolean invFull = invIsFull(playerUser);
		if(invFull){ user.dropStack(new ItemStack(Willowisp.VIAL)); }
		else{ playerUser.giveItemStack(new ItemStack(Willowisp.VIAL)); }
		playerUser.playerScreenHandler.sendContentUpdates();
		if (new BloodData((PlayerEntity) user, "").getBloodType((PlayerEntity) user).toString().equals("vampire")){
			return this.eatVampFood(world, stack, user);
		} else {
			return user.eatFood(world, stack);
		}
    }

	boolean invIsFull(PlayerEntity user){
        Inventory userInv = user.getInventory();
        for(int j = 0; j<userInv.size()-5;j++){
            if(userInv.getStack(j).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public ItemStack eatVampFood(World world, ItemStack stack, LivingEntity user) {
		if (stack.isFood()) {
			world.playSound(
				null,
				user.getX(),
				user.getY(),
				user.getZ(),
				user.getEatSound(stack),
				SoundCategory.NEUTRAL,
				1.0F,
				1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F
			);
			this.applyVampFoodEffects(stack, world, user);
			if (!(user instanceof PlayerEntity) || !((PlayerEntity)user).getAbilities().creativeMode) {
				stack.decrement(1);
			}

			user.emitGameEvent(GameEvent.EAT);
		}

		return stack;
	}

	private void applyVampFoodEffects(ItemStack stack, World world, LivingEntity targetEntity) {
		Item item = stack.getItem();
		if (item.isFood()) {
			for (Pair<StatusEffectInstance, Float> pair : bloodType.getStatusEffects()) {
				if (!world.isClient && pair.getFirst() != null && world.random.nextFloat() < pair.getSecond()) {
					targetEntity.addStatusEffect(new StatusEffectInstance(pair.getFirst()));
				}
			}
		}
	}
}

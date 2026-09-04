package lightmage670.willowisp.item;

import lightmage670.willowisp.attachment.bloodAttachments.BloodData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class Fangs extends Item {

    public Fangs(Settings settings) {
        super(settings);
    }
    
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!entity.getWorld().isClient()){
            if(!user.getItemCooldownManager().isCoolingDown(this)){
                if(getBlood(user).equals("vampire")){
                    user.getItemCooldownManager().set(this, 2400);
                    if(entity instanceof PlayerEntity){
                        if(getBlood((PlayerEntity) entity).equals("none")){ return ActionResult.FAIL; }
                        else if(getBlood((PlayerEntity) entity).equals("mortal")){ mortalBlood(user); biteTarget(entity); return ActionResult.SUCCESS; }
                        else if(getBlood((PlayerEntity) entity).equals("magic")){ magicBlood(user); biteTarget(entity); return ActionResult.SUCCESS; }
                        else if(getBlood((PlayerEntity) entity).equals("divine")){ divineBlood(user); biteTarget(entity); return ActionResult.SUCCESS; }
                        else if(getBlood((PlayerEntity) entity).equals("god")){ godBlood(user); biteTarget(entity); return ActionResult.SUCCESS; }
                        else if(getBlood((PlayerEntity) entity).equals("vampire")){ vampBlood(user); biteTarget(entity); return ActionResult.SUCCESS; }
                        else if(getBlood((PlayerEntity) entity).equals("bad")){ badBlood(user); biteTarget(entity); return ActionResult.SUCCESS; }
                        else if(getBlood((PlayerEntity) entity).equals("sculk")){ sculkBlood(user); biteTarget(entity); return ActionResult.SUCCESS; }
                        else if(getBlood((PlayerEntity) entity).equals("ink")){ inkBlood(user); biteTarget(entity); return ActionResult.SUCCESS; }
                        else { return ActionResult.FAIL; }
                    }
                    else if (entity instanceof PigEntity){
                        mortalBlood(user);
                        biteTarget(entity);
                        return ActionResult.SUCCESS;
                    }
                    else if (entity instanceof WardenEntity){
                        sculkBlood(user);
                        biteTarget(entity);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS;
	}
    void biteTarget(LivingEntity target) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 2));
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100, 0));
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 4));
        return;
    }

    void mortalBlood(PlayerEntity user){
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 0));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 9));
        return;
    }
    void magicBlood(PlayerEntity user){
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 1));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 9));
        return;
    }
    void badBlood(PlayerEntity user){
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 4));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
        return;
    }
    void vampBlood(PlayerEntity user){
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 9));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 1));
        return;
    }
    void divineBlood(PlayerEntity user){
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 1));
	    user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 9));
        return;
    }
    void godBlood(PlayerEntity user){
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 1));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 2));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 19));
        return;
    }
    void sculkBlood(PlayerEntity user){
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 0));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 400, 1));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0));
        return;
    }
    void inkBlood(PlayerEntity user){
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, 0));
        return;
    }

    String getBlood(PlayerEntity target){
        return new BloodData(target, "").getBloodType(target).toString();
    }
}

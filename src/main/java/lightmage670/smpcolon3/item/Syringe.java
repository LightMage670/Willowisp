package lightmage670.smpcolon3.item;

import java.util.Set;

import lightmage670.smpcolon3.Smpcolon3;
import lightmage670.smpcolon3.attachment.bloodAttachments.BloodData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Syringe extends Item {
    public Syringe(Item.Settings settings){
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.isSneaking()){
            ItemStack vial = null;
            if(getBlood(user).equals("none")){ vial = null; return TypedActionResult.pass(user.getStackInHand(hand)); }
            else if(getBlood(user).equals("mortal")){ vial = new ItemStack(Smpcolon3.MORTAL_VIAL); }
            else if(getBlood(user).equals("divine")){ vial = new ItemStack(Smpcolon3.DIVINE_VIAL); }
            else if(getBlood(user).equals("god")){ vial = new ItemStack(Smpcolon3.GOD_VIAL); }
            else if(getBlood(user).equals("vampire")){ vial = new ItemStack(Smpcolon3.VAMP_VIAL); }
            else if(getBlood(user).equals("sculk")){ vial = new ItemStack(Smpcolon3.SCULK_VIAL); }
            else if(getBlood(user).equals("ink")){ vial = new ItemStack(Smpcolon3.INK_VIAL); }
            else { return TypedActionResult.pass(user.getStackInHand(hand)); }
            Inventory userInv = user.getInventory();
            Set<Item> vialItems = Set.of(Smpcolon3.VIAL);
            if(userInv.containsAny(vialItems)){
                boolean invFull = invIsFull(user);
                for(int i = 0; i<userInv.size();i++){
                    ItemStack invSlot = userInv.getStack(i);
                    if (!invSlot.isEmpty() && vialItems.contains(invSlot.getItem())) {
                        invSlot.decrement(1);
                        if(invFull){ user.dropStack(vial); }
                        else{ user.giveItemStack(vial); }
                        user.playerScreenHandler.sendContentUpdates();
                        return TypedActionResult.success(user.getStackInHand(hand));
                    }
                }
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack vial = null;
        Inventory userInv = user.getInventory();
        Set<Item> vialItems = Set.of(Smpcolon3.VIAL);
        if(userInv.containsAny(vialItems)){
            boolean invFull = invIsFull(user);
            if(entity instanceof PlayerEntity){
                if(getBlood((PlayerEntity) entity).equals("none")){ vial = null; return ActionResult.FAIL; }
                else if(getBlood((PlayerEntity) entity).equals("mortal")){ vial = new ItemStack(Smpcolon3.MORTAL_VIAL); }
                else if(getBlood((PlayerEntity) entity).equals("divine")){ vial = new ItemStack(Smpcolon3.DIVINE_VIAL); }
                else if(getBlood((PlayerEntity) entity).equals("god")){ vial = new ItemStack(Smpcolon3.GOD_VIAL); }
                else if(getBlood((PlayerEntity) entity).equals("vampire")){ vial = new ItemStack(Smpcolon3.VAMP_VIAL); }
                else if(getBlood((PlayerEntity) entity).equals("sculk")){ vial = new ItemStack(Smpcolon3.SCULK_VIAL); }
                else if(getBlood((PlayerEntity) entity).equals("ink")){ vial = new ItemStack(Smpcolon3.INK_VIAL); }
                else { return ActionResult.FAIL; }
                for(int i = 0; i<userInv.size();i++){
                    ItemStack invSlot = userInv.getStack(i);
                    if (!invSlot.isEmpty() && vialItems.contains(invSlot.getItem())) {
                        invSlot.decrement(1);
                        if(invFull){ user.dropStack(vial); }
                        else{ user.giveItemStack(vial); }
                        user.playerScreenHandler.sendContentUpdates();
                        return ActionResult.SUCCESS;
                    }
                }
            }
            else if (entity instanceof PigEntity){
                for(int i = 0; i<userInv.size();i++){
                    ItemStack invSlot = userInv.getStack(i);
                    if (!invSlot.isEmpty() && vialItems.contains(invSlot.getItem())) {
                        invSlot.decrement(1);
                        if(invFull){ user.dropStack(new ItemStack(Smpcolon3.MORTAL_VIAL)); }
                        else{ user.giveItemStack(new ItemStack(Smpcolon3.MORTAL_VIAL)); }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            else if (entity instanceof WardenEntity){
                for(int i = 0; i<userInv.size();i++){
                    ItemStack invSlot = userInv.getStack(i);
                    if (!invSlot.isEmpty() && vialItems.contains(invSlot.getItem())) {
                        invSlot.decrement(1);
                        if(invFull){ user.dropStack(new ItemStack(Smpcolon3.SCULK_VIAL)); }
                        else{ user.giveItemStack(new ItemStack(Smpcolon3.SCULK_VIAL)); }
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS;
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
    String getBlood(PlayerEntity target){
        return new BloodData(target, "").getBloodType(target).toString();
    }
}
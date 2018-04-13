/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.inventory.handler;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.UserInventory;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;

import java.util.function.Consumer;

public class InvSeeHandler implements Consumer<InteractInventoryEvent> {

    private User target;
    private boolean canModify;

    public InvSeeHandler(User target, boolean canModify) {
        this.target = target;
        this.canModify = canModify;
    }

    @Override
    public void accept(InteractInventoryEvent event) {
        if (event instanceof InteractInventoryEvent.Open || event instanceof InteractInventoryEvent.Close) {
            return;
        }
        if (!canModify) {
            event.setCancelled(true);
            event.getCursorTransaction().setValid(false);
            return;
        }
        final Inventory inventory = event.getTargetInventory();
        final UserInventory userInventory = target.getInventory().query(QueryOperationTypes.INVENTORY_TYPE.of(UserInventory.class));

        int i = 0;
        for (Inventory slot : inventory.slots()) {
            final ItemStack itemStack = slot.peek().orElse(ItemStack.empty());
            if (i < 9) {
                if (i == 0) {
                    target.equip(EquipmentTypes.HEADWEAR, itemStack);
                } else if (i == 1) {
                    target.equip(EquipmentTypes.CHESTPLATE, itemStack);
                } else if (i == 2) {
                    target.equip(EquipmentTypes.LEGGINGS, itemStack);
                } else if (i == 3) {
                    target.equip(EquipmentTypes.BOOTS, itemStack);
                } else if (i == 4) {
                    target.equip(EquipmentTypes.OFF_HAND, itemStack);
                } else {
                    event.setCancelled(true);
                    event.getCursorTransaction().setValid(false);
                }
            } else {
                userInventory.getMain().set(SlotIndex.of(i - 9), itemStack);
            }
            i++;
        }
    }
}

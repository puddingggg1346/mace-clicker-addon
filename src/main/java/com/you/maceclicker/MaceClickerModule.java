package com.you.maceclicker;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.settings.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class MaceClickerModule extends Module {
    private final Setting<Integer> delay = settings.add(new IntSetting.Builder()
        .name("delay-ms")
        .description("攻击间隔")
        .defaultValue(50)
        .min(0).max(200).sliderMax(200).build()
    );

    private long lastAttackTime = 0;

    public MaceClickerModule() {
        super(Category.Combat, "mace-clicker", "仅对 Mace 连点，无蓄力");
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.target == null) return;
        if (!(mc.target instanceof LivingEntity target)) return;
        if (!mc.player.getMainHandStack().isOf(Items.MACE)) return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime < delay.get()) return;

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastAttackTime = currentTime;
    }
}

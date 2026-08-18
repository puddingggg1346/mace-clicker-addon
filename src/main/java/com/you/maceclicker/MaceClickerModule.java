package com.you.maceclicker;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.settings.*;
import net.minecraft.entity.LivingEntity;
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
        super(Category.Combat, "mace-clicker", "仅对 Mace 连点");
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.target == null) return;
        if (!(mc.target instanceof LivingEntity)) return;
        
        // 检查主手是否是 Mace
        var stack = mc.player.getMainHandStack();
        if (!stack.getItem().equals(Items.MACE)) return;

        long now = System.currentTimeMillis();
        if (now - lastAttackTime < delay.get()) return;

        mc.interactionManager.attackEntity(mc.player, mc.target);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastAttackTime = now;
    }
}

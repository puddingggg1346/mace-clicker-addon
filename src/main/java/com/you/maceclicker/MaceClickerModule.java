package com.you.maceclicker;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.settings.*;
import net.minecraft.item.MaceItem;
import net.minecraft.entity.LivingEntity;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;

public class MaceClickerModule extends Module {
    private final Setting<Integer> delay = settings.add(new IntSetting.Builder()
        .name("delay-ms")
        .description("攻击间隔")
        .defaultValue(50)
        .min(0).max(200).sliderMax(200).build()
    );

    public MaceClickerModule() {
        super(Category.Combat, "mace-clicker", "仅对 Mace 连点，无蓄力");
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.target == null) return;
        if (!(mc.target instanceof LivingEntity target)) return;
        if (!mc.player.getMainHandStack().getItem().getClass().equals(MaceItem.class)) return;

        // 自动切换 Mace
        FindItemResult mace = InvUtils.findInHotbar(item -> item.getItem() instanceof MaceItem);
        if (mace.found()) InvUtils.swap(mace.slot(), true);

        // 无蓄力直接攻击
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(); // 动画
    }
}

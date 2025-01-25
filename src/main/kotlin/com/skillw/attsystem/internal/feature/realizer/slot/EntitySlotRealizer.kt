package com.skillw.attsystem.internal.feature.realizer.slot

import com.skillw.attsystem.AttributeSystem
import com.skillw.attsystem.api.equipment.EquipmentLoader
import com.skillw.pouvoir.api.feature.realizer.BaseRealizer
import com.skillw.pouvoir.api.feature.realizer.BaseRealizerManager
import com.skillw.pouvoir.api.feature.realizer.component.Awakeable
import com.skillw.pouvoir.api.plugin.annotation.AutoRegister
import com.skillw.pouvoir.api.plugin.map.LowerMap
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.console
import taboolib.common.util.unsafeLazy
import taboolib.module.lang.sendLang
import taboolib.type.BukkitEquipment

@AutoRegister
object EntitySlotRealizer : BaseRealizer("entity"), Awakeable {
    private val slots = LowerMap<BukkitEquipment>()
    override val file by lazy {
        AttributeSystem.slot.file!!
    }
    override val manager: BaseRealizerManager by unsafeLazy {
        AttributeSystem.realizerManager
    }

    override fun onEnable() {
        onReload()
    }

    override fun onReload() {
        slots.clear()
        for (key in config.keys) {
            val slot = config[key].toString()
            val type = kotlin.runCatching { BukkitEquipment.fromString(slot) }.getOrNull()
            type ?: console().sendLang("equipment-type-error", key)
            type ?: continue
            slots.register(key, type)
        }
        slots.putAll(
            mapOf(
                "头盔" to BukkitEquipment.HEAD,
                "胸甲" to BukkitEquipment.CHEST,
                "护腿" to BukkitEquipment.LEGS,
                "靴子" to BukkitEquipment.FEET,
                "主手" to BukkitEquipment.HAND,
                "副手" to BukkitEquipment.OFF_HAND
            )
        )
    }

    @AutoRegister
    object NormalEquipmentLoader : EquipmentLoader<LivingEntity> {

        override val key: String = "default"

        override val priority: Int = 1000

        override fun loadEquipment(entity: LivingEntity): Map<String, ItemStack?> {
            val items = HashMap<String, ItemStack?>()
            for ((key, equipmentType) in slots) {
                items[key] = equipmentType.getItem(entity)
            }
            return items
        }
    }
}
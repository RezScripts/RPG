package me.rezscipts.rpg.items;

import static me.rezscipts.rpg.items.ItemBalance.PREFIX_CHANCE;
import static me.rezscipts.rpg.items.ItemBalance.RARITY_CHANCES;
import static me.rezscipts.rpg.items.ItemBalance.RARITY_COLORS;
import static me.rezscipts.rpg.items.ItemBalance.RARITY_NAMES;
import static me.rezscipts.rpg.items.ItemBalance.SAGE_CHANCE;
import static me.rezscipts.rpg.items.ItemBalance.SAGE_NAMES;
import static me.rezscipts.rpg.items.ItemBalance.SET_PREFIXES;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import me.rezscipts.rpg.items.stats.StatAccumulator;
import me.rezscipts.rpgexperience.utils.RMath;

public class RandomItemGenerator {

    private static double rand(int rarity) {
        double val = Math.random() - (0.02 * rarity);
        if (val < 0)
            val = 0;
        return val;
    }

    public static ItemStack generateEquip(EquipType et, int level) {
        return generateEquip(et, level, 0);
    }

    public static ItemStack generateEquip(EquipType et, int level, int rarityFinder) {
        // Roll rarity
        int rarity = 0;
        double rand = Math.random();
        rand *= 1 / (1 + (rarityFinder / 100.0));
        for (int k = 0; k < RARITY_CHANCES.length; k++) {
            if (rand < RARITY_CHANCES[k])
                rarity = k;
        }
        return generateEquip(et, level, rarityFinder, rarity);
    }

    public static ItemStack generateEquip(EquipType et, int level, int rarityFinder, int rarity) {
        String setName = null;
        if (rand(rarity) < PREFIX_CHANCE) {
            setName = SET_PREFIXES[(int) (Math.random() * SET_PREFIXES.length)];
        }
        return generateEquip(et, level, rarityFinder, rarity, setName);
    }

    public static ItemStack generateEquip(EquipType et, int level, int rarityFinder, int rarity, String setName) {
        // Roll sage name
        boolean sage = false;
        if (Math.random() < SAGE_CHANCE) {
            sage = true;
        }
        return generateEquip(et, level, rarityFinder, rarity, setName, sage);
    }

    public static ItemStack generateEquip(EquipType et, int level, int rarityFinder, int rarity, String setName, boolean isSage) {
        // Set up stat accumulator
        StatAccumulator stats = new StatAccumulator();
        stats.level = level;

        // Check if armor
        boolean isArmor = EquipType.isArmor(et);

        // Calculate level including rarity bonus
        int rarityLevel = level + StatsBalance.getRarityLevelBonus(rarity);

        // Add armor-only stats
        if (isArmor) {
            stats.setHP(StatsBalance.getHP(rarityLevel));
            stats.setDefense(StatsBalance.getDefense(rarityLevel));
            if (rand(rarity) < 0.1) {
                stats.setHPMultiplier(StatsBalance.getHPMultiplier(rarityLevel));
            }
            if (rand(rarity) < 0.1) {
                stats.setDefenseMultiplier(StatsBalance.getDefenseMultiplier(rarityLevel));
            }
            if (et == EquipType.BOOTS && rand(rarity) < 0.5) {
                stats.setSpeed(StatsBalance.getSpeed(rarityLevel));
            } else if (rand(rarity) < 0.15) {
                stats.setSpeed(StatsBalance.getSpeed(rarityLevel));
            }
            if (rand(rarity) < 0.15)
                stats.setHPRegen(StatsBalance.getHPRegen(rarityLevel));
        }

        // Add weapon-only stats
        if (!isArmor) {
            int[] range = StatsBalance.getDamageRange(rarityLevel);
            stats.setDamage(range[0], range[1]);
            if (rand(rarity) < 0.25)
                stats.setCritChance(StatsBalance.getCritChanceWeapon(rarityLevel));
            if (rand(rarity) < 0.15)
                stats.setAttackSpeed(StatsBalance.getAttackSpeed(rarityLevel));
            if (rand(rarity) < 0.10)
                stats.setAttackDamage(StatsBalance.getAttackDamage(rarityLevel));
        }

        // add non-exclusive stats
        if (rand(rarity) < 0.15)
            stats.setRarityFinder(StatsBalance.getRarityFinder(rarityLevel));
        if (rand(rarity) < 0.10)
            stats.setManaRegenRate(StatsBalance.getManaRegenRate(rarityLevel));
        if (rand(rarity) < 0.15)
            stats.setSpellDamage(StatsBalance.getSpellDamage(rarityLevel));

        // Build name
        StringBuilder sb = new StringBuilder();
        if (isSage) {
            sb.append(SAGE_NAMES[RMath.randInt(0, SAGE_NAMES.length - 1)]);
            sb.append("'s "); //rezscipts's
        }
        if (rarity > 0) {
            sb.append(RARITY_NAMES[rarity]); //rezscipts's Godlike
            sb.append(' ');
        }
        if (setName != null) {
            sb.append(setName);
            sb.append(' ');
        }
        if (isArmor) {
            sb.append(ItemBalance.getTierPrefixArmor(level));
        } else {
            sb.append(ItemBalance.getTierPrefixWeapon(level));
        }
        sb.append(' ');
        sb.append(et.equipName); //Sword

        // Roll suffixes - these DON'T use the rarity random b/c it'd make high rarity equips too consistently strong
        double rand = Math.random(); // the last number is the chance of that suffix
        if (rand < 0.03) {
            int a = (int) (Math.random() * 4);
            switch (a) {
                case 0:
                    sb.append(" of Seeking");
                    stats.setCritChance(StatsBalance.getCritChanceWeapon(rarityLevel + 10));
                    stats.setCritDamage(StatsBalance.getCritDamage(rarityLevel + 10));
                    break;
                case 1:
                    sb.append(" of Swiftness");
                    stats.setSpeed(StatsBalance.getSpeed(rarityLevel + 10));
                    stats.setAttackSpeed(StatsBalance.getAttackSpeed(rarityLevel + 10));
                    break;
                case 2:
                    sb.append(" of Mysticism");
                    stats.setSpellDamage(StatsBalance.getSpellDamage(rarityLevel + 10));
                    stats.setManaRegenRate(StatsBalance.getManaRegenRate(rarityLevel + 10));
                    break;
                case 3:
                    sb.append(" of Wealth");
                    stats.setRarityFinder(StatsBalance.getRarityFinder(rarityLevel + 50));
                    break;
                default:
                    break;
            }
        }

        ChatColor rarityColor = RARITY_COLORS[rarity];
        sb.insert(0, rarityColor); //&6rezscipts's Godlike Sword

        // sage stats are incremented only at the end
        stats.setSage(isSage);

        // Create the item
        EquipItem ei = new EquipItem(et.getMaterial(level));
        ei.name = sb.toString();
        ei.stats = stats;
        return ei.generate();
    }

}
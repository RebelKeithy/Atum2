package com.teammetallurgy.atum.init;

import com.teammetallurgy.atum.entity.*;
import com.teammetallurgy.atum.entity.bandit.*;
import com.teammetallurgy.atum.entity.projectile.EntitySmallBone;
import com.teammetallurgy.atum.entity.projectile.arrow.*;
import com.teammetallurgy.atum.entity.stone.EntityStoneguard;
import com.teammetallurgy.atum.entity.stone.EntityStonewarden;
import com.teammetallurgy.atum.entity.undead.*;
import com.teammetallurgy.atum.utils.Constants;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.teammetallurgy.atum.utils.AtumRegistry.*;

@GameRegistry.ObjectHolder(value = Constants.MOD_ID)
public class AtumEntities {
    //Mobs
    public static final EntityEntry ASSASSIN = registerMob(EntityAssassin.class, 0x433731, 0xd99220);
    public static final EntityEntry BANDIT_WARLORD = registerMob(EntityWarlord.class, 0xa62d1b, 0xe59a22);
    public static final EntityEntry BARBARIAN = registerMob(EntityBarbarian.class, 0x9c7359, 0x8c8c8c);
    public static final EntityEntry BONESTORM = registerMob(EntityBonestorm.class, 0x74634e, 0xab9476);
    public static final EntityEntry BRIGAND = registerMob(EntityBrigand.class, 0xC2C2C2, 0x040F85);
    public static final EntityEntry DESERT_WOLF = registerMob(EntityDesertWolf.class, 0xE7DBC8, 0xAD9467);
    public static final EntityEntry FORSAKEN = registerMob(EntityForsaken.class, 0xB59C7D, 0x6F5C43);
    public static final EntityEntry MUMMY = registerMob(EntityMummy.class, 0x515838, 0x868F6B);
    public static final EntityEntry NOMAD = registerMob(EntityNomad.class, 0xC2C2C2, 0x7E0C0C);
    public static final EntityEntry PHARAOH = registerMob(EntityPharaoh.class, 0xD4BC37, 0x3A4BE0);
    public static final EntityEntry RABBIT = registerMob(EntityDesertRabbit.class, 0xAE8652, 0x694C29);
    public static final EntityEntry SCARAB = registerMob(EntityScarab.class, 0x61412C, 0x2F1D10);
    public static final EntityEntry STONEGUARD = registerMob(EntityStoneguard.class, 0x918354, 0x695D37);
    public static final EntityEntry STONEWARDEN = registerMob(EntityStonewarden.class, 0x918354, 0x695D37);
    public static final EntityEntry TARANTULA = registerMob(EntityTarantula.class, 0x745c47, 0xd2b193);
    public static final EntityEntry WRAITH = registerMob(EntityWraith.class, 0x544d34, 0x3e3927);

    //Entities
    public static final EntityEntry DOUBLE_SHOT_BLACK = registerArrow(EntityArrowDoubleShotBlack.class);
    public static final EntityEntry DOUBLE_SHOT_WHITE = registerArrow(EntityArrowDoubleShotWhite.class);
    public static final EntityEntry EXPLOSIVE_ARROW = registerArrow(EntityArrowExplosive.class);
    public static final EntityEntry FIRE_ARROW = registerArrow(EntityArrowFire.class);
    public static final EntityEntry HEART_OF_RA  = registerEntity(EntityHeartOfRa.class, 256, Integer.MAX_VALUE, false);
    public static final EntityEntry POISON_ARROW = registerArrow(EntityArrowPoison.class);
    public static final EntityEntry QUICKDRAW_ARROW = registerArrow(EntityArrowQuickdraw.class);
    public static final EntityEntry RAIN_ARROW = registerArrow(EntityArrowRain.class);
    public static final EntityEntry SLOWNESS_ARROW = registerArrow(EntityArrowSlowness.class);
    public static final EntityEntry SMALL_BONE = registerEntity(EntitySmallBone.class, 64, 1, true);
    public static final EntityEntry STRAIGHT_ARROW = registerArrow(EntityArrowStraight.class);
    public static final EntityEntry TEFNUTS_CALL = registerArrow(EntityTefnutsCall.class);
}
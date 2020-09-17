package uk.hexeption.roost.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

/**
 * Config
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 17/09/2020 - 05:57 am
 */
public class Config {

    public static void init() {
        ModLoadingContext.get().registerConfig(Type.COMMON, Common.spec);
    }

    public static final class Common {

        public static final ForgeConfigSpec.DoubleValue roostSpeed;
        public static final ForgeConfigSpec.DoubleValue breaderSpeed;
        public static final ForgeConfigSpec.BooleanValue disableEgglaying;
        static final ForgeConfigSpec spec;

        static {
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

            roostSpeed = builder.comment("The speed multiplier for the roost. Higher is faster.").defineInRange("roost.roostSpeed", 1d, 0.01d, 100d);
            breaderSpeed = builder.comment("The speed multiplier for the breeder. Higher is faster.").defineInRange("roost.breederSpeed", 1d, 0.01d, 100d);
            disableEgglaying = builder.comment("Prevent vanilla chickens from laying eggs. Of interest to modpack makers only.").define("roost.disableEgglaying", false);

            spec = builder.build();
        }
    }


}

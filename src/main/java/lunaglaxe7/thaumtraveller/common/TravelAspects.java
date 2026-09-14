package lunaglaxe7.thaumtraveller.common;

import net.minecraft.util.ResourceLocation;

import thaumcraft.api.aspects.Aspect;

// create aspects of my mod
public class TravelAspects {

    public static Aspect VOYAGE;
    public static Aspect BEAUTY;
    public static Aspect TEMPTATION;

    public static void initAspects() {
        VOYAGE = new Aspect(
                "navigatio",
                11908557,
                new Aspect[] { Aspect.TRAVEL, Aspect.FLIGHT },
                new ResourceLocation("thaumtraveller", "textures/aspects/navigatio.png"),
                1);
        BEAUTY = new Aspect(
                "pulchritudo",
                8345008,
                new Aspect[] { Aspect.SOUL, Aspect.HEAL },
                new ResourceLocation("thaumtraveller", "textures/aspects/pulchritudo.png"),
                1);
        TEMPTATION = new Aspect(
                "temptationem",
                15411091,
                new Aspect[] { BEAUTY, Aspect.ENTROPY },
                new ResourceLocation("thaumtraveller", "textures/aspects/temptationem.png"),
                1);
    }

    public static void addAspects() {

    }
}

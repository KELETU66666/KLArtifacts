package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.ModConfig;
import artifacts.common.entity.EntityHallowStar;
import artifacts.common.init.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class BaubleStarCloak extends BaubleBase implements IRenderCloak {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/star_cloak.png");

    private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/star_cloak_overlay.png");

    public BaubleStarCloak() {
        super("star_cloak", BaubleType.BODY);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getTexture() {
        return TEXTURES;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getTextureOverlay() {
        return TEXTURE_OVERLAY;
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if(!event.getEntity().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntityLiving(), ModItems.STAR_CLOAK) != -1) {
            if (event.getSource().getTrueSource() != null && event.getEntityLiving().world.canSeeSky(event.getEntityLiving().getPosition())) {
                int stars = ModConfig.general.starCloakStarsMin;
                if (ModConfig.general.starCloakStarsMax > ModConfig.general.starCloakStarsMin) {
                    stars += event.getEntityLiving().getRNG().nextInt(ModConfig.general.starCloakStarsMax - ModConfig.general.starCloakStarsMin + 1);
                }
                for (int i = 0; i < stars; i++) {
                    event.getEntityLiving().world.spawnEntity(new EntityHallowStar(event.getEntityLiving().world, event.getEntityLiving()));
                }
            }
        }
    }
}

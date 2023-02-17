package jimmy.mixin;

import com.jimmy.events.SpawnParticleEvent;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({NetHandlerPlayClient.class})
public class MixinNetHandlerPlayClient {

    @Redirect(method = {"handleParticles"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;ZDDDDDD[I)V"))
    public void handleParticles(WorldClient world, EnumParticleTypes particleTypes, boolean isLongDistance, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int[] params) {
        SpawnParticleEvent event = new SpawnParticleEvent(particleTypes, isLongDistance, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, params);
        if (!event.post())
            world.spawnParticle(particleTypes, isLongDistance, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, params);
    }


}

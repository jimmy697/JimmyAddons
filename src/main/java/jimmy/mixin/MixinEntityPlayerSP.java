package jimmy.mixin;

import com.jimmy.events.PlayerMoveEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraftforge.fml.common.eventhandler.Event;


@Mixin({EntityPlayerSP.class})
public class MixinEntityPlayerSP {

    @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("HEAD")}, cancellable = true)
    public void onUpdateWalking(CallbackInfo cir) {
        if (MinecraftForge.EVENT_BUS.post((Event) new PlayerMoveEvent.Pre()))
            cir.cancel();
    }

    @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("RETURN")}, cancellable = true)
    public void onWalking(CallbackInfo cir) {
        if (MinecraftForge.EVENT_BUS.post((Event)new PlayerMoveEvent.Post()))
            cir.cancel();
    }

}

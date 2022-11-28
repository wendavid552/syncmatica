package ch.endte.syncmatica.mixin;

import ch.endte.syncmatica.*;
import ch.endte.syncmatica.communication.CommunicationManager;
import ch.endte.syncmatica.communication.ServerCommunicationManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    @Inject(method = "start", at = @At("TAIL"))
    private <S extends MinecraftServer> void initSyncmatica(CallbackInfo ci) {
        final IFileStorage data = new FileStorage();
        final SyncmaticManager man = new SyncmaticManager();
        final CommunicationManager comms = new ServerCommunicationManager();

        Syncmatica.initServer(comms, data, man);
        final Context con = Syncmatica.getContext(Syncmatica.SERVER_CONTEXT);
        con.startup();
    }

    // at
    @Inject(method = "shutdown", at = @At("TAIL"))
    public void shutdownSyncmatica(final CallbackInfo ci) {
        final Context con = Syncmatica.getContext(Syncmatica.SERVER_CONTEXT);
        con.shutdown();
    }
}

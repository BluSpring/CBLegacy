package xyz.bluspring.cblegacy.client;

import net.fabricmc.api.ClientModInitializer;
import xyz.bluspring.cblegacy.core.ChiselsAndBits;
import xyz.bluspring.cblegacy.core.ClientSide;

import java.io.File;

public class ChiselsAndBitsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        loadClientAssets = true;

        // load this after items are created...
        CreativeClipboardTab.load( new File( configFile.getParent(), ChiselsAndBits.MODID + "_clipboard.cfg" ) );

        ClientSide.instance.preinit( this );
    }
}

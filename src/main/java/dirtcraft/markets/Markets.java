package dirtcraft.markets;

import com.google.inject.Inject;
import dirtcraft.markets.commands.MarketCommandManager;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "markets",
        name = "Markets",
        description = "Adds a better chunk based market overview",
        authors = {
                "Dev"
        }
)
public class Markets {

    @Inject
    private Logger logger;

    @Listener
    public void onGameStarting (GameStartingServerEvent event){
        new MarketCommandManager(this).register();
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    }
}

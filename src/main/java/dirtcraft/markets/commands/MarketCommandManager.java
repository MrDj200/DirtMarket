package dirtcraft.markets.commands;

import com.flowpowered.math.vector.Vector3i;
import dirtcraft.markets.Database;
import dirtcraft.markets.Market;
import dirtcraft.markets.Markets;
import dirtcraft.markets.utils.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MarketCommandManager implements Supplier<CommandCallable> {

    private final Markets plugin;
    private final MarketCommands cmds = new MarketCommands();
    private Database db = Database.getInstance();

    private final CommandCallable marketCommand;
    private final CommandCallable marketClaimCommand;
    private final CommandCallable marketUnclaimCommand;
    private final CommandCallable marketBlockCommand;
    private final CommandCallable marketUnblockCommand;
    private final CommandCallable marketListCommand;

    public MarketCommandManager(Markets plugin) {
        this.plugin = plugin;

        this.marketClaimCommand = CommandSpec.builder()
                .description(Text.of("Claim market Command"))
                .permission("dirtmarkets.commands.market.claim")
                .executor(cmds::processMarketClaimCommand)
                .build();

        this.marketUnclaimCommand = CommandSpec.builder()
                .description(Text.of("Unclaim market Command"))
                .permission("dirtmarkets.commands.market.unclaim")
                .executor(cmds::processMarketUnclaimCommand)
                .build();

        this.marketBlockCommand = CommandSpec.builder()
                .description(Text.of("Block market Command"))
                .permission("dirtmarkets.commands.market.block")
                .executor(cmds::processMarketBlockCommand)
                .build();

        this.marketUnblockCommand = CommandSpec.builder()
                .description(Text.of("Unblock market Command"))
                .permission("dirtmarkets.commands.market.unblock")
                .executor(cmds::processMarketUnblockCommand)
                .build();

        this.marketListCommand = CommandSpec.builder()
                .description(Text.of("List your markets"))
                .permission("dirtmarkets.commands.market.list")
                .executor(cmds::processMarketListCommand)
                .build();

        this.marketCommand = CommandSpec.builder()
                .description(Text.of("Basic Market Command"))
                .permission("dirtmarkets.commands.market.base")
                .executor(cmds::processMarketCommand)
                .child(marketClaimCommand, "claim")
                .child(marketUnclaimCommand, "unclaim")
                .child(marketBlockCommand, "block")
                .child(marketUnblockCommand, "unblock")
                .child(marketListCommand, "list", "ls")
                .build();
    }

    public void register() {
        Sponge.getCommandManager().register(this.plugin, this.get(), "market", "mrk");
    }

    @Override
    public CommandCallable get() {
        return this.marketCommand;
    }
}


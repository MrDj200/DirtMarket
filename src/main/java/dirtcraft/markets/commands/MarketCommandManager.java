package dirtcraft.markets.commands;

import com.flowpowered.math.vector.Vector3i;
import dirtcraft.markets.Markets;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.Supplier;

public class MarketCommandManager implements Supplier<CommandCallable> {

    private final Markets plugin;

    private final CommandCallable marketCommand;
    private final CommandCallable marketClaimCommand;
    private final CommandCallable marketUnclaimCommand;
    private final CommandCallable marketBlockCommand;
    private final CommandCallable marketUnblockCommand;

    public MarketCommandManager(Markets plugin) {
        this.plugin = plugin;

        this.marketClaimCommand = CommandSpec.builder()
                .description(Text.of("Claim market Command"))
                .permission("dirtmarkets.commands.market.claim")
                .executor(this::processMarketClaimCommand)
                .build();

        this.marketUnclaimCommand = CommandSpec.builder()
                .description(Text.of("Unclaim market Command"))
                .permission("dirtmarkets.commands.market.unclaim")
                .executor(this::processMarketUnclaimCommand)
                .build();

        this.marketBlockCommand = CommandSpec.builder()
                .description(Text.of("Unclaim market Command"))
                .permission("dirtmarkets.commands.market.block")
                .executor(this::processMarketBlockCommand)
                .build();

        this.marketUnblockCommand = CommandSpec.builder()
                .description(Text.of("Unclaim market Command"))
                .permission("dirtmarkets.commands.market.unblock")
                .executor(this::processMarketUnblockCommand)
                .build();

        this.marketCommand = CommandSpec.builder()
                .description(Text.of("Basic Market Command"))
                .permission("dirtmarkets.commands.market.base")
                .executor(this::processMarketCommand)
                .child(marketClaimCommand, "claim")
                .child(marketUnclaimCommand, "unclaim")
                .build();
    }

    public void register() {
        Sponge.getCommandManager().register(this.plugin, this.get(), "market", "mrk");
    }

    private CommandResult processMarketCommand(CommandSource source, CommandContext args) throws CommandException {
        Player ply = (Player) source;
        Vector3i loc = ply.getLocation().getChunkPosition();
        source.sendMessage(Text.of("Information about " + loc + ""));
        return CommandResult.success();
    }

    private CommandResult processMarketClaimCommand(CommandSource source, CommandContext args) throws CommandException {
        return CommandResult.success();
    }

    private CommandResult processMarketUnclaimCommand(CommandSource source, CommandContext args) throws CommandException {
        return CommandResult.success();
    }

    private CommandResult processMarketBlockCommand(CommandSource source, CommandContext args) throws CommandException{
        return CommandResult.success();
    }

    private CommandResult processMarketUnblockCommand(CommandSource source, CommandContext args) throws CommandException{
        return CommandResult.success();
    }

    @Override
    public CommandCallable get() {
        return this.marketCommand;
    }
}


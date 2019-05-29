package dirtcraft.markets.commands;

import com.flowpowered.math.vector.Vector3i;
import dirtcraft.markets.Database;
import dirtcraft.markets.Market;
import dirtcraft.markets.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;

public class MarketCommands {

    Database db = Database.getInstance();

    CommandResult processMarketCommand(CommandSource source, CommandContext args) throws CommandException {
        if (!(source instanceof Player)){
            throw new CommandException(Text.of("This command can only be used by players!"));
        }
        Player ply = (Player) source;

        Market market = db.queryGetMarket(Utils.getChunkPos(ply.getPosition()), ply.getWorld().getUniqueId());
        StringBuilder fuck = new StringBuilder();

        if (market == null){
            fuck.append("No results found!");
        }else{
            fuck.append(market.toString());
        }

        ply.sendMessage(Text.of(fuck));

        return CommandResult.success();
    }

    CommandResult processMarketClaimCommand(CommandSource source, CommandContext args) throws CommandException {
        if (!(source instanceof Player)){
            throw new CommandException(Text.of("This command can only be used by players!"));
        }
        Player ply = (Player) source;
        if (db.queryActiveMarkets(ply.getUniqueId()).size() >= 1){
            throw new CommandException(Text.of("You already have an active market!"));
        }

        db.queryAddMarket(ply.getUniqueId(), Utils.getChunkPos(ply.getPosition()), ply.getWorld().getUniqueId());

        ply.sendMessage(Text.of("Chunk claimed!"));

        return CommandResult.success();
    }

    CommandResult processMarketUnclaimCommand(CommandSource source, CommandContext args) throws CommandException {
        if (!(source instanceof Player)){
            throw new CommandException(Text.of("This command can only be used by players!"));
        }
        return CommandResult.success();
    }

    CommandResult processMarketBlockCommand(CommandSource source, CommandContext args) throws CommandException{
        if (!(source instanceof Player)){
            throw new CommandException(Text.of("This command can only be used by players!"));
        }
        Player ply = (Player) source;

        Vector3i chunk = Utils.getChunkPos(ply.getPosition());

        ply.sendMessage(Text.of(chunk.getX() + " " + chunk.getZ()));

        return CommandResult.success();
    }

    CommandResult processMarketUnblockCommand(CommandSource source, CommandContext args) throws CommandException{
        if (!(source instanceof Player)){
            throw new CommandException(Text.of("This command can only be used by players!"));
        }
        return CommandResult.success();
    }

    CommandResult processMarketListCommand(CommandSource source, CommandContext args) throws CommandException{
        if (!(source instanceof Player)){
            throw new CommandException(Text.of("This command can only be used by players!"));
        }
        Player ply = (Player) source;

        List<Market> markets = db.queryActiveMarkets(ply.getUniqueId());

        if(markets.isEmpty()){
            throw new CommandException(Text.of("You have no active markets!"));
        }

        StringBuilder marketString = new StringBuilder();

        for (Market market : markets) {
            marketString
                    .append(market.toString())
                    .append("\n");
        }

        ply.sendMessage(Text.of(marketString));

        return CommandResult.success();
    }
}

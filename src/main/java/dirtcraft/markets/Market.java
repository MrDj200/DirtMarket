package dirtcraft.markets;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import java.util.UUID;

public class Market {
    private int id;
    private int x;
    private int z;
    private UUID dim;
    private UUID plyUuid;
    private String claimedOn;

    public Market(int id, int x, int z, UUID dim, UUID plyUuid, String claimedOn) {
        this.id = id;
        this.x = x;
        this.z = z;
        this.dim = dim;
        this.plyUuid = plyUuid;
        this.claimedOn = claimedOn;
    }

    @Override
    public String toString() {
        Player ply = Sponge.getServer().getPlayer(this.plyUuid).orElse(null);
        World world = Sponge.getServer().getWorld(this.dim).orElse(null);
        return "Chunk (" + this.x + "," + this.z + ") in world \"" + (world == null ? "N/A" : world.getName()) + "\" owned by player \"" + (ply == null ? "N/A" : ply.getName()) + "\"";
    }
}

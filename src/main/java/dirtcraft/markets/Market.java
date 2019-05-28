package dirtcraft.markets;

import com.flowpowered.math.vector.Vector3i;

public class Market {
    private Vector3i pos;
    private String plyUuid;


    public Market(Vector3i pos) {
        this.pos = pos;
    }

    public Market(Vector3i pos, String plyUuid) {
        this.pos = pos;
        this.plyUuid = plyUuid;
    }
}

package dirtcraft.markets.utils;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

public class Utils {

    public static Vector3i convertVectorDToI(Vector3d old){

        return old.toInt();

    }

    public static Vector3i getChunkPos(Vector3d pos){

        int x = (int) Math.floor(pos.getX() / 16);
        int y = (int) Math.floor(pos.getY() / 16);
        int z = (int) Math.floor(pos.getZ() / 16);

        return new Vector3i(x, y, z);

    }


}

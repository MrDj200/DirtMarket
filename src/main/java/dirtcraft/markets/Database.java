package dirtcraft.markets;

import com.flowpowered.math.vector.Vector3i;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    @Inject
    private Logger logger;

    private static Database INSTANCE;

    private SqlService sql;
    private DataSource dataSource;

    private Database() {
        this.sql = Sponge.getServiceManager().provide(SqlService.class).get();
        //String conString = sql.getConnectionUrlFromAlias("devAlias").get();
        connect("jdbc:h2:./config/DirtMarket/data/markets");
    }

    public void connect(String url) {
        String createString = "CREATE TABLE IF NOT EXISTS `market` (  `id`      int NOT NULL AUTO_INCREMENT,  `x`       int NOT NULL ,  `y`       int NOT NULL ,  `dim`     int NOT NULL ,  `plyUUID` char(36) NOT NULL ,  `claimed` datetime NOT NULL ,  PRIMARY KEY (`id`) );  CREATE TABLE IF NOT EXISTS `market_history` (  `plyUUID`   char(36) NOT NULL ,  `claimed`   datetime NOT NULL ,  `unclaimed` datetime NOT NULL ,  `fk_market_id`        int NOT NULL );  ALTER TABLE market_history ADD FOREIGN KEY (fk_market_id) REFERENCES market(id);";
        try {
            this.dataSource = this.sql.getDataSource(url);
            try (Connection c = this.dataSource.getConnection()){
                c.prepareStatement(createString).execute();
            } catch (SQLException e) {
                logger.error("An error occured!", e);
            }
        } catch (SQLException e) {
            logger.error("An error occured!", e);
        }
    }

    public String evilShit(String sql){

        return "nope";
    }

    public String testShit() {
        String sql = "SELECT * from some_table";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet results = stmt.executeQuery()){

            StringBuilder fuck = new StringBuilder("nothing to see over here. Move along");

            int i = 0;
            while (results.next()){
                fuck
                        .append(results.getMetaData().getColumnName(i++))
                        .append("\n");
            }

            return fuck.toString();

        }catch (SQLException e){
            return "An error occured \n" + e.toString();
        }

    }

    /**
     * Adds a market at the given pos for the given UUID
     *
     * @param pos The position where the market should be added
     * @param plyUuid The UUID of the player
     * @throws SQLException
     */
    public void queryAddMarket(Vector3i pos, String plyUuid) throws SQLException{

    }

    /**
     * Updates market at pos to the given UUID
     *
     * @param pos The position that should be updated
     * @param plyUuid The UUID of the player
     * @throws SQLException
     */
    public void queryUpdateMarket(Vector3i pos, String plyUuid) throws SQLException{

    }

    /**
     * Gets the market at the given pos
     *
     * @return  <code>null</code> if no market is at the given pos
     *          <code>Market Object</code> otherwise
     *
     * @param pos The position that should queried
     * @throws SQLException
     */
    public Market queryGetMarket(Vector3i pos) throws SQLException{

        return new Market(pos);

    }

    /**
     *
     * @param plyUuid The UUID of the player
     * @return  <code>true</code> if an active market is registered for the given UUID
     *          <code>false</code> otherwise
     * @throws SQLException
     */
    public boolean queryHasActiveMarket(String plyUuid) throws SQLException{
        return false;
    }

    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

}

package dirtcraft.markets;

import com.flowpowered.math.vector.Vector3i;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database {

    private Logger logger = Markets.getInstance().getLogger();

    private static Database INSTANCE;

    private SqlService sql;
    private DataSource dataSource;

    private Database() {
        this.sql = Sponge.getServiceManager().provide(SqlService.class).get();
        //String conString = sql.getConnectionUrlFromAlias("devAlias").get();
        connect("jdbc:h2:./config/DirtMarket/data/markets");
    }

    public void connect(String url) {
        String createString = "CREATE TABLE IF NOT EXISTS `market`( `id`       int NOT NULL AUTO_INCREMENT, `x`        int NOT NULL , `z`        int NOT NULL , `dim`      char(36) NOT NULL , `plyUUID`  char(36) NOT NULL , `claimed`  varchar(255) NOT NULL ,PRIMARY KEY (`id`));CREATE TABLE IF NOT EXISTS `market_history`( `plyUUID`          char(36) NOT NULL , `claimed`          varchar(255) NOT NULL , `unclaimed`        varchar(255) NOT NULL , `fk_market_id`     int NOT NULL);ALTER TABLE market_history ADD FOREIGN KEY (fk_market_id) REFERENCES market(id);";
        try {
            this.dataSource = this.sql.getDataSource(url);
            try (Connection c = this.dataSource.getConnection()){
                c.prepareStatement(createString).execute();
            } catch (SQLException e) {
                this.logger.error("An error occured!", e);
            }
        } catch (SQLException e) {
            this.logger.error("An error occured!", e);
        }
    }

    /**
     * Adds a market at the given pos for the given UUID
     *
     * @param plyUuid The UUID of the player
     * @param pos The position where the market should be added
     * @param dimId The ID of the dimension the pos is in
     */
    public void queryAddMarket(UUID plyUuid, Vector3i pos, UUID dimId){
        String sql = "INSERT INTO market (x, z, dim, plyUUID, claimed) VALUES (" +
                "'" + pos.getX() + "'," +
                "'" + pos.getZ()+ "'," +
                "'" + dimId + "'," +
                "'" + plyUuid + "'," +
                "'" + System.currentTimeMillis() / 1000L + "');";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);){
            int results = stmt.executeUpdate();
        }catch (SQLException e){
            this.logger.error("An error occured: ", e);
        }

    }

    /**
     * Updates market at pos to the given UUID
     *
     * @param pos The position that should be updated
     * @param plyUuid The UUID of the player
     */
    public void queryUpdateMarket(UUID plyUuid, Vector3i pos, UUID dimId){

    }

    /**
     * Gets the market at the given pos
     *
     * @return  <code>null</code> if no market is at the given pos
     *          <code>Market Object</code> otherwise
     *
     * @param pos The position that should queried
     * @param dimId The ID of the dimension the pos is in
     */
    public Market queryGetMarket(Vector3i pos, UUID dimId){

        Market market = null;
        String sql = "SELECT * FROM market WHERE x = '" + pos.getX() + "' AND z = '" + pos.getZ() + "' AND dim = '" + dimId + "';";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet results = stmt.executeQuery()){

            while(results.next()){
                market = new Market(
                    results.getInt("id"),
                    results.getInt("x"),
                    results.getInt("z"),
                    UUID.fromString(results.getString("dim")),
                    UUID.fromString(results.getString("plyUUID")),
                    results.getString("claimed"));
            }

        }catch (SQLException e){
            this.logger.error("An error occured: ", e);
        }

        return market;
    }

    /**
     *
     * @param plyUuid The UUID of the player
     * @return  number of active markets
     */
    public List<Market> queryActiveMarkets(UUID plyUuid){

        List<Market> returner = new ArrayList<Market>();

        String sql = "SELECT * from market WHERE plyUUID='" + plyUuid.toString() + "'";

        try (Connection conn = this.dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet results = stmt.executeQuery()){

            while(results.next()){
                Market tempMarket = new Market(
                results.getInt("id"),
                results.getInt("x"),
                results.getInt("z"),
                UUID.fromString(results.getString("dim")),
                UUID.fromString(results.getString("plyUUID")),
                results.getString("claimed"));

                returner.add(tempMarket);
            }

        }catch (SQLException e){
            this.logger.error("An error occured: ", e);
        }

        return returner;
    }

    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

}

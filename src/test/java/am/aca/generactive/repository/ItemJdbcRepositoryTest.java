package am.aca.generactive.repository;

import am.aca.generactive.database.DatabaseConnection;
import am.aca.generactive.model.Item;
import am.aca.generactive.model.StockItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class ItemJdbcRepositoryTest {

    @BeforeAll
    public static void initDatabase() throws SQLException, ClassNotFoundException {
        Connection databaseConnection = DatabaseConnection.initializeConnection();
        Statement statement = databaseConnection.createStatement();
        statement.executeUpdate("CREATE TABLE item (" +
                "id SERIAL PRIMARY KEY," +
                "name  VARCHAR," +
                "base_price  INTEGER" +
                ")");
        databaseConnection.close();
    }

    @Test
    public void testConnection() {
        assertDoesNotThrow(DatabaseConnection::initializeConnection);
    }

    @Test
    @DisplayName("Insert")
    public void insert() {
        ItemJdbcRepository itemJdbcRepository = new ItemJdbcRepository();
        try {
            itemJdbcRepository.insert(new StockItem(10, 100, "TestItem"));
            Item item = itemJdbcRepository.getOneById(1);
            assertNotNull(item);
            assertEquals(1, item.getId());
            itemJdbcRepository.insert(new StockItem(10, 100, "TestItem"));
            Item item1 = itemJdbcRepository.getOneById(2);
            assertNotNull(item1);
            assertEquals(2, item1.getId());
        } catch (SQLException | ClassNotFoundException throwables) {
            fail(throwables.getMessage());
        }
    }

//    @AfterEach
//    public void cleanUpDatabase() throws SQLException, ClassNotFoundException {
//        Connection databaseConnection = DatabaseConnection.initializeConnection();
//        Statement statement = databaseConnection.createStatement();
//        statement.executeUpdate("TRUNCATE item RESTART IDENTITY");
//
//        databaseConnection.close();
//    }
}

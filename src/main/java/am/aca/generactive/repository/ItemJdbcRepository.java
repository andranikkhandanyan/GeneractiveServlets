package am.aca.generactive.repository;

import am.aca.generactive.database.DatabaseConnection;
import am.aca.generactive.model.Group;
import am.aca.generactive.model.Item;
import am.aca.generactive.model.StockItem;
import am.aca.generactive.repository.mapper.ItemResultSetMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemJdbcRepository {

    public List<Item> findItems(String pName) {
        List<Item> rv = new ArrayList<>();
        // create a connection and handle it with try-with-resources
        try (Connection connection = DatabaseConnection.initializeConnection()) {
            // define select query
            String query = "select * from item where name = ?";
            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            // set parameters
            statement.setString(1, pName);
            // execute the query
            ResultSet resultSet = statement.executeQuery();
            // map each result to Item and add to return list
            while (resultSet.next()) {
                rv.add(ItemResultSetMapper.mapToPojo(resultSet));
            }
        } catch (SQLException e) {
            // Wrap with RuntimeException as this is the last layer, where an SQLException
            // can be handled
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return rv;
    }

    public List<Item> getAllItems() {
        List<Item> rv = new ArrayList<>();
        // create a connection and handle it with try-with-resources
        try (Connection connection = DatabaseConnection.initializeConnection()) {
            // define select query
            String query = "select * from item";
            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            // execute the query
            ResultSet resultSet = statement.executeQuery();
            // map each result to Item and add to return list
            while (resultSet.next()) {
                rv.add(ItemResultSetMapper.mapToPojo(resultSet));
            }
        } catch (SQLException e) {
            // Wrap with RuntimeException as this is the last layer, where an SQLException
            // can be handled
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return rv;
    }

    public Item getOneById(long itemId) {
        Item rv;
        // create a connection and handle it with try-with-resources
        try (Connection connection = DatabaseConnection.initializeConnection()) {
            // define select query
            String query = "select * from item where id = ?";
            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            // set parameters
            statement.setLong(1, itemId);
            // execute the query
            ResultSet resultSet = statement.executeQuery();
            rv = null;
            if (resultSet.next()) {
                rv = ItemResultSetMapper.mapToPojo(resultSet);
            }
        } catch (SQLException e) {
            // Wrap with RuntimeException as this is the last layer, where an SQLException
            // can be handled
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return rv;
    }

    public long insert(Item item) {
        long rv = -1;
        // create a connection and handle it with try-with-resources
        try (Connection connection = DatabaseConnection.initializeConnection()) {
            // define insert query
            String query = "insert into item(name, base_price) values("
                    + "'" + item.getName()
                    + "','" + item.getBasePrice()
                    + "')";
            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println(query);
            // execute the query
            statement.executeUpdate();

            // get generated key (id) for the inserted entries
            ResultSet generatedKeys = statement.getGeneratedKeys();
            rv = generatedKeys.next() ? generatedKeys.getLong(1) : -1;

            return rv;
        } catch (SQLException e) {
            // Wrap with RuntimeException as this is the last layer, where an SQLException
            // can be handled
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is only for preview and not intended to be used
     */
    @Deprecated
    public long insertTestTransaction(Item item) {
        long rv = -1;
        Connection connection;
        try {
            connection = DatabaseConnection.initializeConnection();
        } catch (SQLException e) {
            // Wrap with RuntimeException as this is the last layer, where an SQLException
            // can be handled
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // create a connection and handle it with try-with-resources
        try {
            // define insert query
            String query = "insert into item(name, base_price) values("
                    + "'" + item.getName()
                    + "','" + item.getBasePrice()
                    + "')";

            // here connection.setAutoCommit(false) starts a new transaction,
            // which is equal to BEGIN in postgreSQL
            connection.setAutoCommit(false);
            // create first prepared statement
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println(query);
            // execute first query
            rv = statement.executeUpdate();

            // get generated key (id) for the inserted entries
            ResultSet generatedKeys = statement.getGeneratedKeys();
            long genId = generatedKeys.next() ? generatedKeys.getLong(1) : -1;

            String secondQuery = "update item set base_price = -1000 "
                    + "WHERE id = " + genId;
            // create second prepared statement
            PreparedStatement statement1 = connection.prepareStatement(secondQuery, Statement.RETURN_GENERATED_KEYS);
            // execute first query
            statement1.executeUpdate();

            // try to commit
            // second query will fail, because the item table has constraint on base_price column
            // base_price value can't have negative value
            connection.commit();
        } catch (SQLException e) {
            // Wrap with RuntimeException as this is the last layer, where an SQLException
            // can be handled
            e.printStackTrace();
            try {
                // rollback all queries in transaction (statement and statement1)
                connection.rollback();
            } catch (SQLException inEx) {
                inEx.printStackTrace();
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        }

        return rv;
    }
}

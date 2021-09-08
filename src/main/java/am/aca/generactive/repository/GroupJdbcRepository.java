package am.aca.generactive.repository;

import am.aca.generactive.database.DatabaseConnection;
import am.aca.generactive.model.Group;
import am.aca.generactive.model.Item;
import am.aca.generactive.repository.mapper.GroupResultSetMapper;
import am.aca.generactive.repository.mapper.ItemResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class GroupJdbcRepository {

    public Optional<Group> getGroup(long groupId) {
        Group group = null;
        // create a connection and handle it with try-with-resources
        try (Connection connection = DatabaseConnection.initializeConnection()) {
            // define select query
            String query = "SELECT g.id as group_id, g.name as group_name, i.id, i.name, i.base_price " +
                    "FROM \"group\" g " +
                    "left join item i on g.id = i.group_id " +
                    "where g.id = ?";
            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            // set parameters
            statement.setLong(1, groupId);
            // execute the query and get ResultSet
            ResultSet resultSet = statement.executeQuery();
            // join query results in a multiple row
            // rows count is equal to one or to items count related to group(item.group_id)
            // so each row contains group columns and joined columns from item
            // group column values are the same for all the rows
            // each row contains appropriate values from the item
            while (resultSet.next()) {
                // first time we read a group, we keep its reference
                // then it will be used to add Items into it
                // It will be done once for each group
                if (group == null) {
                    // read group from ResultSet
                    group = GroupResultSetMapper.mapToPojo(resultSet);
                }
                // read Item from each group
                Item item = ItemResultSetMapper.mapToPojo(resultSet);
                // add Item to concrete group
                group.addItem(item);
            }
            return group == null ? Optional.empty() : Optional.of(group);
        } catch (SQLException e) {
            // Wrap with RuntimeException as this is the last layer, where an SQLException
            // can be handled
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

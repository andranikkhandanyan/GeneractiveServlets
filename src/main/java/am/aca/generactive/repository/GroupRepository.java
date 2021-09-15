package am.aca.generactive.repository;

import am.aca.generactive.model.Group;
import am.aca.generactive.orm.HibernateConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;

public class GroupRepository {

    public static final HibernateConfiguration HIBERNATE_CONFIGURATION =
            HibernateConfiguration.getInstance();

    public Optional<Group> getGroup(long groupId) {
        Session session = HIBERNATE_CONFIGURATION.getSession();
        Transaction transaction = session.beginTransaction();

        // Hibernate query to get group by id
        // equivalent sql query: select * from group where id = ?;
        // equivalent JPQL query: select i from Group i where id = :id;
        // Group is the name of the Entity defined by @Entity annotation
        Query<Group> query = session.createQuery(
                "select g from Group g" +
                        " where g.id = :id ",
                Group.class);
        query.setParameter("id", groupId);
        // Execute the query and get single result
        Group group = query.getSingleResult();

        transaction.commit();
        session.close();

        return Optional.ofNullable(group);
    }
}

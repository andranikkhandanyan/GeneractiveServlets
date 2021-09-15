package am.aca.generactive.orm;

import am.aca.generactive.model.Basket;
import am.aca.generactive.model.Group;
import am.aca.generactive.model.Item;
import am.aca.generactive.model.ItemDetails;
import am.aca.generactive.util.DatabaseConfigurationUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {

    private final SessionFactory factory;
    private static HibernateConfiguration sInstance;

    public static HibernateConfiguration getInstance() {
        if (sInstance == null) {
            sInstance = new HibernateConfiguration();
        }
        return sInstance;
    }

    private HibernateConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addProperties(DatabaseConfigurationUtil
                .readProperties("hibernate.properties"));
        addAnnotatedClasses(configuration);
        factory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return factory.getCurrentSession();
    }

    private void addAnnotatedClasses(Configuration configuration) {
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(ItemDetails.class);
        configuration.addAnnotatedClass(Group.class);
        configuration.addAnnotatedClass(Basket.class);
    }
}

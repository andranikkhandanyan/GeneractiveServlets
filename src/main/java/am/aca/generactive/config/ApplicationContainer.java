package am.aca.generactive.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContainer {

    public static final ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");
}

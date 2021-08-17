//package maciej.grochowski.studentsidentities.session;
//
//import maciej.grochowski.studentsidentities.entity.Student;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.service.ServiceRegistry;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class HibernateFactory {
//
//    public static Session getCurrentSession() {
//        Map<String, String> settings = new HashMap<>();
//        settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
//        settings.put("dialect", "org.hibernate.dialect.MySQLDialect");
//        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/student");
//        settings.put("hibernate.connection.username", "root");
//        settings.put("hibernate.connection.password", "password");
//        settings.put("hibernate.current_session_context_class", "thread");
//        settings.put("hibernate.show_sql", "true");
//        settings.put("hibernate.format_sql", "true");
//
//        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(settings).build();
//
//        MetadataSources metadataSources = new MetadataSources(registry);
//        metadataSources.addAnnotatedClass(Student.class);
//        Metadata metadata = metadataSources.buildMetadata();
//
//        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
//        Session session = sessionFactory.getCurrentSession();
//
//        return session;
//    }
//}
//
//
////    If using hibernate.cfg.xml
////    public SessionFactory getSessionFactory() {
////        Configuration configuration = new Configuration().configure();
////        StandardServiceRegistryBuilder registryBuilder =
////                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
////
////        //        SessionFactory sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
////        return configuration.buildSessionFactory(registryBuilder.build());
////    }
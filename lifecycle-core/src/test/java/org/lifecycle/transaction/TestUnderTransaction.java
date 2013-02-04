package org.lifecycle.transaction;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.hibernate.SessionFactoryFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.lifecycle.config.Config;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

public class TestUnderTransaction {

    Session session;
    static SessionFactory hibernateSessionFactory;

    public SessionFactory getSessionFactory() {
        return hibernateSessionFactory;
    }

    protected static void initDB(String configFile, List<Class<?>> entities)throws IOException, ClassNotFoundException {
        Config config = fromJson(jsonFixture(configFile), Config.class);
        hibernateSessionFactory = createHibernateSessionFactory(config, entities);
    }

    protected void startSession() {
        session = openSession(hibernateSessionFactory);
        session.beginTransaction();
    }

    protected void cleanTables(Set<String> tables){
        for (String table: tables){
            session.createSQLQuery("delete from "+  table).executeUpdate();
        }
        commitAndBeginNewTransaction();
    }

    protected Session openSession(SessionFactory hibernateSessionFactory) {
        Session session = hibernateSessionFactory.openSession();
        session.setDefaultReadOnly(true);
        session.setFlushMode(FlushMode.AUTO);
        ManagedSessionContext.bind(session);
        return session;
    }

    protected static SessionFactory createHibernateSessionFactory(Config config, List<Class<?>> entities) throws IOException, ClassNotFoundException {
        SessionFactoryFactory factory = new SessionFactoryFactory();
        Environment mockEnvironment = Mockito.mock(Environment.class);
        return factory.build(mockEnvironment, config.getDatabaseConfiguration(), entities);
    }

    protected static List<Class<?>> asList(Class<?>... classes) {
        return Lists.newArrayList(classes);
    }

    protected static <T> Set<T> asSet(T... set) {
        return Sets.newHashSet(set);
    }

    protected void rollbakAndCloseSession() {
        ManagedSessionContext.unbind(hibernateSessionFactory);
        final Transaction txn = session.getTransaction();
        if (txn != null && txn.isActive()) {
            txn.rollback();
        }
        session.close();
    }

    protected void commitAndCloseSession() {
        ManagedSessionContext.unbind(hibernateSessionFactory);
        commitTransaction();
        session.close();
    }

    private void commitTransaction() {
        final Transaction txn = session.getTransaction();
        if (txn != null && txn.isActive()) {
            txn.commit();
        }
    }

    protected void commitAndBeginNewTransaction() {
        commitTransaction();
        session.beginTransaction();
    }

    protected void commitAndOpenNewSession(){
        commitAndCloseSession();
        startSession();
    }

}

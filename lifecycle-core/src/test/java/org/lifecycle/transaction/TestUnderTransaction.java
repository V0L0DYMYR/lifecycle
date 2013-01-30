package org.lifecycle.transaction;

import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.hibernate.SessionFactoryFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.lifecycle.config.Config;
import org.lifecycle.domain.Ticket;
import org.mockito.Mockito;

import java.io.IOException;

import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;

public class TestUnderTransaction {

    Session session;
    SessionFactory hibernateSessionFactory;

    public SessionFactory getSessionFactory() {
        return hibernateSessionFactory;
    }

    protected void startTransaction() throws IOException, ClassNotFoundException {
        hibernateSessionFactory = createHibernateSessionFactory();
        session = openSession(hibernateSessionFactory);
        session.beginTransaction();
    }

    protected Session openSession(SessionFactory hibernateSessionFactory) {
        Session session = hibernateSessionFactory.openSession();
        session.setDefaultReadOnly(true);
        session.setFlushMode(FlushMode.AUTO);
        ManagedSessionContext.bind(session);
        return session;
    }

    protected SessionFactory createHibernateSessionFactory() throws IOException, ClassNotFoundException {
        Config config = fromJson(jsonFixture("conf/lifecycle.json"), Config.class);
        SessionFactoryFactory factory = new SessionFactoryFactory();
        Environment mockEnvironment = Mockito.mock(Environment.class);
        return factory.build(mockEnvironment, config.getDatabaseConfiguration(), asList(Ticket.class));
    }

    protected ImmutableList<Class<?>> asList(Class<?>... classes){
        return ImmutableList.copyOf(classes);
    }
    protected void endTransaction() {
        ManagedSessionContext.unbind(hibernateSessionFactory);
        final Transaction txn = session.getTransaction();
        if (txn != null && txn.isActive()) {
            txn.rollback();
        }
        session.close();
    }

}

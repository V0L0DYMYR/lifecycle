package org.lifecycle.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.lifecycle.domain.Ticket;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TicketDao {

    private final SessionFactory sessionFactory;

    public TicketDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Ticket> findAll(){
        return session().
                createCriteria(Ticket.class)
                .list();
    }

    public void save(Ticket ticket) {
        session().saveOrUpdate(checkNotNull(ticket));
    }

    protected Session session() {
        return sessionFactory.getCurrentSession();
    }
}

package org.lifecycle.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.lifecycle.domain.Ticket;

import java.util.List;

public class TicketDao {

    private final SessionFactory sessionFactory;

    public TicketDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<TicketDao> findAll(){
        return session().
                createCriteria(Ticket.class)
                .list();
    }

    public void save(Ticket ticket) {
        session().save(ticket);
    }

    protected Session session() {
        return sessionFactory.openSession();
    }
}

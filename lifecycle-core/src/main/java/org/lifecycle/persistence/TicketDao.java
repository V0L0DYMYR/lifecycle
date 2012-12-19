package org.lifecycle.persistence;

import org.hibernate.SessionFactory;
import org.lifecycle.domain.Ticket;

public class TicketDao extends AbstractDao<Ticket> {

    public TicketDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}

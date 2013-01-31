package org.lifecycle.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.lifecycle.domain.Label;
import org.lifecycle.domain.Ticket;

import java.util.List;

public class TicketDao extends AbstractDao<Ticket> {

    public TicketDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Ticket> findByLabel(Label label) {
        return list(criteria()
                .add(Restrictions.eq("labels", label)));
    }
}

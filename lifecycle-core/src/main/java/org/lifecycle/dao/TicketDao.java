package org.lifecycle.dao;

import com.google.common.collect.Iterables;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.Ticket;
import org.lifecycle.domain.User;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.isEmpty;

public class TicketDao extends AbstractDao<Ticket> {

    public TicketDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Ticket> findByLabel(String label) {
        List<Long> ids = listIds(sql("select l.ticket_id ID from LABELS l where l.label = :label ")
                .addScalar("ID", LongType.INSTANCE)
                .setParameter("label", label));

        if(isEmpty(ids)) return Collections.EMPTY_LIST;
        return list(criteria().add(Restrictions.in("id", ids)));
    }

    public List<Ticket> findByProject(long projectId) {
        return list(criteria().add(Restrictions.eq("projectId", projectId)));
    }
}

package org.lifecycle.resource;

import com.yammer.dropwizard.hibernate.UnitOfWork;
import org.lifecycle.dao.ProjectDao;
import org.lifecycle.dao.TicketDao;
import org.lifecycle.domain.Project;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

public class ProjectResource {

    private ProjectDao projectDao;

    public ProjectResource(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @GET
    @Path("/project/{id}")
    @UnitOfWork
    public Project getProjects(@PathParam("id") Long projectId){
        return projectDao.get(projectId);
    }

}

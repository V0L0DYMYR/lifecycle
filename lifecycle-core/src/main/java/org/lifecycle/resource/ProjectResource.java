package org.lifecycle.resource;

import com.yammer.dropwizard.hibernate.UnitOfWork;
import org.lifecycle.dao.ProjectDao;
import org.lifecycle.domain.Project;
import org.lifecycle.domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/project")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {

    private ProjectDao projectDao;

    public ProjectResource(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @GET
    @UnitOfWork
    public List<Project> getProjects(@Context User user){
        return projectDao.findByUser(user);
    }

    @POST
    @UnitOfWork
    public Project createProject(@Context User user, Project project){
        return projectDao.saveOrUpdate(new Project(project, user));
    }

}

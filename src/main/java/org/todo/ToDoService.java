package org.todo;

import io.quarkus.panache.common.Sort;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import javax.json.Json;

import javax.transaction.Transactional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.todo.ToDo;
 
@Path("/todos")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ToDoService {
 
    @GET
    public List<ToDo> get() {
        return ToDo.listAll(Sort.by("task"));
    }
 
    @GET
    @Path("{id}")
    public ToDo getSingle( @PathParam("id") Long id) {
        ToDo entity = ToDo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("ToDo with id of " + id + " does not exist.", 404);
        }
        return entity;
    }
 
    @GET
    @Path("filter")
    public List<ToDo> getSingle( @QueryParam("complete") Boolean complete) {
        return ToDo.list("complete", complete);
    }

    @POST
    @Transactional
    public Response create(ToDo todo) {
        if (todo.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
 
        todo.persist();
        return Response.ok(todo).status(201).build();
    }
 
    @PUT
    @Path("{id}")
    @Transactional
    public ToDo update( @PathParam("id") Long id, ToDo todo) {
        if (todo.task == null) {
            throw new WebApplicationException("ToDo Name was not set on request.", 422);
        }
 
        ToDo entity = ToDo.findById(id);
 
        if (entity == null) {
            throw new WebApplicationException("ToDo with id of " + id + " does not exist.", 404);
        }
 
        entity.task = todo.task;
 
        return entity;
    }
 
    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam ("id") Long id) {
        ToDo entity = ToDo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("ToDo with id of " + id + " does not exist.", 404);
        }
        entity.delete();
        return Response.status(204).build();
    }
 
    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {
 
        @Override
        public Response toResponse(Exception exception) {
            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }
            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
                    .build();
        }
 
    }
}

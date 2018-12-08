package com.mycompany.mavenproject1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserService {
    
    EntityManager entityManager;
    
    public UserService(){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }
     
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}")
    public User getUser(@PathParam("id") int id) {
        User test = entityManager.find(User.class, id);
        System.out.println(test);
        return test;
    }
     

}
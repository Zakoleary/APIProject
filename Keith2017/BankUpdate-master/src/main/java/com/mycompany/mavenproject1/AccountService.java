package com.mycompany.mavenproject1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountService {

    EntityManager entityManager;

    public AccountService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }

   
    
    @GET
    @Path("test")
    public Response get() {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(10000);
        System.out.println("\n\n\n\n+go");
        return Response.ok("Some Data").cacheControl(cc).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccount() {

        List<Account> list = allEntries();
        GenericEntity entity = new GenericEntity<List<Account>>(list) {};
        return Response.ok(entity).build();

    }

    public List<Account> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> rootEntry = cq.from(Account.class);
        CriteriaQuery<Account> all = cq.select(rootEntry);
        TypedQuery<Account> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
        public Account getAccount(@PathParam("id") int id) {
        Account test = entityManager.find(Account.class, id);
        if (test == null) {
            throw new NotFoundException("You dun goofed");
        }
        return test;
    }
  
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
            public Response createAccount(Account c){
            entityManager.getTransaction().begin();
            entityManager.persist(c);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
            return Response.status(200).entity(c).build();
        }

    @POST
    @Path("{update/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
           public Account updatebalance(@PathParam("id")int id, @QueryParam("balance")double balance){
           Account Account = entityManager.find(Account.class, id);
           if(Account == null){
               throw new NotFoundException("Balance was not updated ! hehe xD");
           }
           entityManager.getTransaction().begin();
           Account.setBalance(balance); 
           entityManager.getTransaction().commit();
           return Account;
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
            public Response save(Account c) {
            System.out.println(c);
            entityManager.getTransaction().begin();
            entityManager.persist(c);
            entityManager.getTransaction().commit();
            entityManager.close();
            return Response.status(200).entity(c).build();
    }

    @DELETE
    @Path("delete/{id}")
    public String deleteAccount(@PathParam("id") int id) {
        Account acc = entityManager.find(Account.class, id);
             if (acc == null) {
                 throw new NotFoundException("Sorry no Transaction matching this id " + id);
            }
                entityManager.getTransaction().begin();
                entityManager.remove(acc);
                entityManager.getTransaction().commit();
                acc.setId(id - 1);
                return "Transaction has been Deleted";
        } 
    }

    
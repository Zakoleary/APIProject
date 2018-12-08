/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table
@XmlRootElement
public class Transaction implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @XmlTransient
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        User user = new User();

        entitymanager.persist(user);

        Transaction trans1 = new Transaction();
        Transaction trans2 = new Transaction();
        Transaction trans3 = new Transaction();
        Transaction trans4 = new Transaction();

        trans1.setUser(user);
        trans2.setUser(user);
        trans3.setUser(user);
        trans4.setUser(user);

        // entitymanager.persist(trans1);
        
        ArrayList<Transaction> list = new ArrayList<>();
        list.add(trans1);
        list.add(trans2);
        list.add(trans3);
        list.add(trans4);
        
        
        user.setTransactions(list);
        entitymanager.persist(user);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
}

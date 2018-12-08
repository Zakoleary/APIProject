package com.mycompany.mavenproject1;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.namespace.QName;

@Entity
@Table
@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Transaction.class})

public class User implements Serializable {
    
    public User(){
        trans = new ArrayList<>();
          
         
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    
    @OneToMany(targetEntity=Transaction.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="user")
    private List<Transaction> trans;

    public void setTransactions(List<Transaction> employeelist) {
        this.trans = employeelist;
    }
    
    @XmlElementWrapper(name="transactions")
    @XmlElementRef()
    public List<Transaction> getTransactions() {
        return trans;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", transactions=" + trans + '}';
    }
    
    public static void main(String[] arges) throws PropertyException, JAXBException{
        StringWriter stringWriter = new StringWriter();

        JAXBContext ctx = JAXBContext.newInstance(User.class);
        Marshaller msh = ctx.createMarshaller();
    msh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        msh.marshal(new User(), System.out);
    }
}

package org.bielefeld.epp.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public class JPAUtil {
    @PersistenceUnit
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pruefplanverwaltungPU");
    
    public JPAUtil() {
    }
    
    public static EntityManagerFactory getEMF() {
        return emf;
    }    

    public static EntityManager getEM() {
        return emf.createEntityManager();
    }     
    
    public static void closeEMF() {
        emf.close();
    }    
    
}

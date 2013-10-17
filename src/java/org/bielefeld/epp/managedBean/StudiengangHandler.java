package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.bielefeld.epp.entity.Studiengang;

@ManagedBean
@ViewScoped
public class StudiengangHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung");
    Studiengang currentStudiengang;
    List<Studiengang> listStudiengang;
    
    @PersistenceContext
    private EntityManager em;

    public StudiengangHandler() {}
        
    public StudiengangHandler(EntityManager em) {
        this.em = em;
    }
    
    public List<Studiengang> getStudiengang() {
        logger.log(Level.INFO, "Start getStudiengang");
        
        listStudiengang = em.createNamedQuery("Studiengang.findAll")
                .getResultList();
        
        logger.log(Level.INFO, "Success at getStudiengang");
        return listStudiengang;
    }
    
    public Studiengang getStudiengangBySgid(int sgid) {
        
        try{
            currentStudiengang = (Studiengang) em.createNamedQuery("Studiengang.findBySgid")
                    .setParameter("sgid", sgid).getSingleResult();
            
            return currentStudiengang;
        }
        catch (Exception ex){
            Logger.getLogger(StudiengangHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }    
    }
}

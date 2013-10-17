package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.bielefeld.epp.entity.Benutzergruppe;

@ManagedBean
@ViewScoped
public class BenutzergruppeHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung");
    Benutzergruppe currentBenutzergruppe;
    @PersistenceContext
    private EntityManager em;

    public BenutzergruppeHandler() {}    
    
    public BenutzergruppeHandler(EntityManager em) {
        this.em = em;
    }
    
    public List<Benutzergruppe> getBenutzergruppe() {
        List<Benutzergruppe> lb = em.createNamedQuery("Benutzergruppe.findAll").
                getResultList();
        return lb;
    }
    
    public Benutzergruppe getBenutzergruppeByBgid(int bgid){
        try{
            logger.log(Level.INFO, "Start getBenutzergruppeByBgid, bgid: {0}", bgid);
            currentBenutzergruppe = (Benutzergruppe) em.createNamedQuery("Benutzergruppe.findByGroupID")
                    .setParameter("groupID", bgid)
                    .getSingleResult();
            logger.log(Level.INFO, "Success at getBenutzergruppeByBgid, bgid: {0}",
                    currentBenutzergruppe.getGroupID());
            return currentBenutzergruppe;
        }
        catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
            return null;
        }   
    }
}

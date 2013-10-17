package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.bielefeld.epp.entity.Pruefungsform;

@ManagedBean
@ViewScoped
public class PruefungsformHandler implements Serializable {
    Pruefungsform currentPruefungsform;
    List<Pruefungsform> listPrufungsform;
  
    @PersistenceContext
    private EntityManager em;

    public PruefungsformHandler(EntityManager em) {
        this.em = em;
    }
    
    public PruefungsformHandler() {}
    
    public List<Pruefungsform> getPruefungsform() {
        
        Logger.getLogger(PruefungsformHandler.class.getName())
                .log(Level.INFO, "Start getPruefungsform");
        listPrufungsform = em.createNamedQuery("Pruefungsform.findAll")
                .getResultList();
        
        return listPrufungsform;
    }    
 
    public Pruefungsform getPruefungsformByPfid(int pfid) {
        
        try{
            Logger.getLogger(PruefungsformHandler.class.getName()).log(Level.INFO, 
                    "Start getPruefungsformByPfid, pfid: {0}", pfid);
            currentPruefungsform = (Pruefungsform) em.createNamedQuery("Pruefungsform.findByPfid")
                    .setParameter("pfid", pfid).getSingleResult();
            Logger.getLogger(PruefungsformHandler.class.getName()).log(Level.INFO, 
                    "Success at getPruefungsformByPfid, pfid: {0}", 
                    currentPruefungsform.getPfid());
            
            return currentPruefungsform;
        }
        catch (Exception ex){
            Logger.getLogger(PruefungsformHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        } 
    }
}
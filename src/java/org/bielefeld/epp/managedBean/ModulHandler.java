package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.bielefeld.epp.entity.Modul;
import org.bielefeld.epp.util.JPAUtil;

@ManagedBean
@ViewScoped
public class ModulHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung");
    
    @PersistenceContext
    private EntityManager em;

    public ModulHandler() {}    
    
    public ModulHandler(EntityManager em) {
        this.em = em;
    }
    
    public List<Modul> getModul() {
        
        List<Modul> lm = em.createNamedQuery("Modul.findAll").
                getResultList();
        
        return lm;
    }
   
    public Modul getModulByMid(int modID) {
        
        try{
            logger.log(Level.INFO, 
                    "Start getModulByMid, modID: {0}", modID);
            Modul mod = (Modul) em
                    .createNamedQuery("Modul.findByModID")
                    .setParameter("modID", modID).getSingleResult();
            Logger.getLogger(ModulHandler.class.getName()).log(Level.INFO, 
                    "Success at getModulByMid, modID: {0}", 
                    mod.getModID());
            
            return mod;
        }
        catch (Exception ex){
            Logger.getLogger(ModulHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }        
    } 
}

package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.bielefeld.epp.entity.Raum;
import org.bielefeld.epp.util.JPAUtil;

@ManagedBean
@ViewScoped
public class RaumHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung");
    private Raum currentRaum;
    private List<Raum> selectedRaum;
    private List<Raum> filteredRaum;
    private List<Raum> allRaum;
    
    private int currentRID;
    private String currentRname;
    private int currentRkap;
    private boolean currentRnachb;
    
    private String dialogHeader;

    @PostConstruct
    private void init(){
        allRaum = this.getRaum();
    }
    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;
    
    public RaumHandler(){}
    
    public RaumHandler(EntityManager em) {
        this.em = em;
    }
    
    public Raum getCurrentRaum() {
        return currentRaum;
    }

    public void setCurrentRaum(Raum currentRaum) {
        this.currentRaum = currentRaum;
    }

    public List<Raum> getSelectedRaum() {
        return selectedRaum;
    }

    public void setSelectedRaum(List<Raum> selectedRaum) {
        this.selectedRaum = selectedRaum;
    }

    public List<Raum> getFilteredRaum() {
        return filteredRaum;
    }

    public void setFilteredRaum(List<Raum> filteredRaum) {
        this.filteredRaum = filteredRaum;
    }

    public List<Raum> getAllRaum() {
        return allRaum;
    }

    public void setAllRaum(List<Raum> allRaum) {
        this.allRaum = allRaum;
    }

    public int getCurrentRID() {
        return currentRID;
    }

    public void setCurrentRID(int currentRID) {
        this.currentRID = currentRID;
    }

    public String getCurrentRname() {
        return currentRname;
    }

    public void setCurrentRname(String currentRname) {
        this.currentRname = currentRname;
    }

    public int getCurrentRkap() {
        return currentRkap;
    }

    public void setCurrentRkap(int currentRkap) {
        this.currentRkap = currentRkap;
    }

    public boolean isCurrentRnachb() {
        return currentRnachb;
    }

    public void setCurrentRnachb(boolean currentRnachb) {
        this.currentRnachb = currentRnachb;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }
    
    public List<Raum> getRaum() {
        
        logger
                .log(Level.INFO, "Start getRaum");
        List<Raum> lr = em.createNamedQuery("Raum.findAll").getResultList();
        
        return lr;
    }

    public void deleteRaum() {
        
        logger.log(Level.INFO, "Start deleteRaum, selected: {0}", selectedRaum.size());
	try{
            utx.begin();
            for(Raum raum : selectedRaum) { 
                em.remove(em.merge(raum));
            }             
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at deleteRaum, deleted: {0}", selectedRaum.size());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }   
    }    
    
    public void insertOrUpdateRaum(){
        logger.log(Level.INFO, "Start insertOrUpdateRaum");
        
        Raum r;
        try {
            utx.begin(); 
            if(currentRaum == null){
                r = new Raum();
                r.setRName(currentRname);
                r.setKapazitaet(currentRkap);
                r.setNachbarRaum(currentRnachb);
                em.persist(r);                 
            } else {
                r = em.find(Raum.class, currentRaum.getRid());
                r.setRName(currentRname);
                r.setKapazitaet(currentRkap);
                r.setNachbarRaum(currentRnachb);
                em.persist(r);                
            }
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at insertOrUpdateRaum");
            
        } catch (Exception ex) {
            logger
                    .log(Level.SEVERE, null, ex);
        }
    }   
    
    public void editRaum(){
        dialogHeader = "Bearbeiten";
        currentRaum = selectedRaum.get(0);
        currentRname = currentRaum.getRName();
        currentRkap = currentRaum.getKapazitaet();
        currentRnachb = currentRaum.getNachbarRaum();
    }
    
    public void newRaum(){
        dialogHeader = "Neu";
        currentRaum = null;
        currentRname = null;
        currentRkap = 0;
        currentRnachb = false;
    }        
    
    public Raum getRaumByRid(int rid) {
        
        try{
            logger.log(Level.INFO, 
                    "Start getRaumByRid, rid: {0}", rid);
            currentRaum = (Raum) em.createNamedQuery("Raum.findByRid")
                    .setParameter("rid", rid).getSingleResult();
            logger.log(Level.INFO, 
                    "Success at getRaumByRid, rid: {0}", 
                    currentRaum.getRid());
            
            return currentRaum;
        }
        catch (Exception ex){
            logger
                    .log(Level.SEVERE, null, ex);
            return null;
        }   
    }
}

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
import org.bielefeld.epp.entity.Pruefer;

@ManagedBean
@ViewScoped
public class PrueferHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung");
    private Pruefer currentPruefer;
    private List<Pruefer> selectedPruefer;
    private List<Pruefer> filteredPruefer;
    private List<Pruefer> allPruefer;
    
    private int currentPrID;
    private String currentPrVorname;
    private String currentPrName;
    private String currentPrTitel;
    private String currentPrKurz;
    
    private String dialogHeader;

    @PostConstruct
    private void init(){
        allPruefer = this.getPruefer();
    }
    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    public PrueferHandler() {}
        
    public PrueferHandler(EntityManager em) {
        this.em = em;
    }

    public Pruefer getCurrentPruefer() {
        return currentPruefer;
    }

    public void setCurrentPruefer(Pruefer currentPruefer) {
        this.currentPruefer = currentPruefer;
    }

    public List<Pruefer> getSelectedPruefer() {
        return selectedPruefer;
    }

    public void setSelectedPruefer(List<Pruefer> selectedPruefer) {
        this.selectedPruefer = selectedPruefer;
    }

    public List<Pruefer> getFilteredPruefer() {
        return filteredPruefer;
    }

    public void setFilteredPruefer(List<Pruefer> filteredPruefer) {
        this.filteredPruefer = filteredPruefer;
    }

    public List<Pruefer> getAllPruefer() {
        return allPruefer;
    }

    public void setAllPruefer(List<Pruefer> allPruefer) {
        this.allPruefer = allPruefer;
    }

    public int getCurrentPrID() {
        return currentPrID;
    }

    public void setCurrentPrID(int currentPrID) {
        this.currentPrID = currentPrID;
    }

    public String getCurrentPrVorname() {
        return currentPrVorname;
    }

    public void setCurrentPrVorname(String currentPrVorname) {
        this.currentPrVorname = currentPrVorname;
    }

    public String getCurrentPrName() {
        return currentPrName;
    }

    public void setCurrentPrName(String currentPrName) {
        this.currentPrName = currentPrName;
    }

    public String getCurrentPrTitel() {
        return currentPrTitel;
    }

    public void setCurrentPrTitel(String currentPrTitel) {
        this.currentPrTitel = currentPrTitel;
    }

    public String getCurrentPrKurz() {
        return currentPrKurz;
    }

    public void setCurrentPrKurz(String currentPrKurz) {
        this.currentPrKurz = currentPrKurz;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }

    public List<Pruefer> getPruefer() {
        
        logger.log(Level.INFO, "Start getPruefer");
        List<Pruefer> lp = em.createNamedQuery("Pruefer.findAll")
                .getResultList();
        
        return lp;
    }
    
    public void deletePruefer() {
        
        logger.log(Level.INFO, "Start deletePruefer, selected: {0}", selectedPruefer.size());
	try{
            utx.begin();
            for(Pruefer pruefer : selectedPruefer) { 
                em.remove(em.merge(pruefer));
            }             
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at deletePruefer, deleted: {0}", selectedPruefer.size());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }   
    }    
    
    public void insertOrUpdatePruefer(){
        logger.log(Level.INFO, "Start insertOrUpdatePruefer");
        
        Pruefer p;
        try {
            utx.begin(); 
            if(currentPruefer == null){
                p = new Pruefer();
                p.setPrName(currentPrName);
                p.setPrVorname(currentPrVorname);
                p.setPrKurz(currentPrKurz);
                p.setPrTitel(currentPrTitel);
                em.persist(p);                 
            } else {
                p = em.find(Pruefer.class, currentPruefer.getPrID());
                p.setPrName(currentPrName);
                p.setPrVorname(currentPrVorname);
                p.setPrKurz(currentPrKurz);
                p.setPrTitel(currentPrTitel);
                em.merge(p);                
            }
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at insertOrUpdatePruefer");
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }   
    
    public void editPruefer(){
        dialogHeader = "Bearbeiten";
        currentPruefer = selectedPruefer.get(0);
        currentPrName = currentPruefer.getPrName();
        currentPrVorname = currentPruefer.getPrVorname();
        currentPrKurz = currentPruefer.getPrKurz();
        currentPrTitel = currentPruefer.getPrTitel();
    }
    
    public void newPruefer(){
        dialogHeader = "Neu";
        currentPruefer = null;
        currentPrName = null;
        currentPrVorname = null;
        currentPrKurz = null;
        currentPrTitel = null;
    }    
    
    public Pruefer getPrueferByPrID(int prid) {
        
        try{
            logger.log(Level.INFO, "Start getPrueferByPrID, prid: {0}", prid);
            currentPruefer = (Pruefer) em.createNamedQuery("Pruefer.findByPrID")
                    .setParameter("prID", prid)
                    .getSingleResult();
            logger.log(Level.INFO, "Success at getPrueferByPrID, prid: {0}",
                    currentPruefer.getPrID());
            
            return currentPruefer;
        }
        catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    public String getAsString(Pruefer p){
        return p.getPrVorname()+", "+p.getPrName();
    }
}

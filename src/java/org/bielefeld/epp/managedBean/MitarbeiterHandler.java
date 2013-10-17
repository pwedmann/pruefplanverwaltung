package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.Date;
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
import org.bielefeld.epp.entity.Accounts;
import org.bielefeld.epp.entity.Mitarbeiter;
import org.bielefeld.epp.util.JPAUtil;

@ManagedBean
@ViewScoped
public class MitarbeiterHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung");
    private List<Mitarbeiter> selectedMitarbeiter;
    private List<Mitarbeiter> filteredMitarbeiter;
    private List<Mitarbeiter> allMitarbeiter;
    
    private Mitarbeiter currentMitarbeiter;
    
    private int currentMaID;
    private String currentMaVorname;
    private String currentMaName;
    private String currentMaTitel;
    private String currentMaKurz;
    private Date currentSperrDatZeitVon;  
    private Date currentSperrDatZeitBis; 
    private int currentAccID;

    private String dialogHeader;
    
    @PostConstruct
    private void init(){
        allMitarbeiter = this.getMitarbeiter();
    }
    
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    
    public MitarbeiterHandler(){}

    public MitarbeiterHandler(EntityManager em){
        this.em = em;    
    }    

    public Mitarbeiter getCurrentMitarbeiter() {
        return currentMitarbeiter;
    }

    public void setCurrentMitarbeiter(Mitarbeiter currentMitarbeiter) {
        this.currentMitarbeiter = currentMitarbeiter;
    }
    
    public int getCurrentMaID() {
        return currentMaID;
    }

    public void setCurrentMaID(int currentMaID) {
        this.currentMaID = currentMaID;
    }

    public String getCurrentMaVorname() {
        return currentMaVorname;
    }

    public void setCurrentMaVorname(String currentMaVorname) {
        this.currentMaVorname = currentMaVorname;
    }

    public String getCurrentMaName() {
        return currentMaName;
    }

    public void setCurrentMaName(String currentMaName) {
        this.currentMaName = currentMaName;
    }

    public String getCurrentMaTitel() {
        return currentMaTitel;
    }

    public void setCurrentMaTitel(String currentMaTitel) {
        this.currentMaTitel = currentMaTitel;
    }

    public String getCurrentMaKurz() {
        return currentMaKurz;
    }

    public int getCurrentAccID() {
        return currentAccID;
    }

    public void setCurrentAccID(int currentAccID) {
        this.currentAccID = currentAccID;
    }

    public void setCurrentMaKurz(String currentMaKurz) {
        this.currentMaKurz = currentMaKurz;
    }

    public Date getCurrentSperrDatZeitVon() {
        return currentSperrDatZeitVon;
    }

    public void setCurrentSperrDatZeitVon(Date currentSperrDatZeitVon) {
        this.currentSperrDatZeitVon = currentSperrDatZeitVon;
    }

    public Date getCurrentSperrDatZeitBis() {
        return currentSperrDatZeitBis;
    }

    public void setCurrentSperrDatZeitBis(Date currentSperrDatZeitBis) {
        this.currentSperrDatZeitBis = currentSperrDatZeitBis;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }
    
     public List<Mitarbeiter> getMitarbeiter() {
        
        List<Mitarbeiter> lma;
        try {
            logger.log(Level.INFO, "Start getMitarbeiter");
            lma = em.createNamedQuery("Mitarbeiter.SelectMitarbeiter").
                getResultList();
            
            return lma;
        }
        catch (Exception ex){
            Logger.getLogger(MitarbeiterHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Mitarbeiter> getAllMitarbeiter() {
        return allMitarbeiter;
    }

    public void setAllMitarbeiter(List<Mitarbeiter> allMitarbeiter) {
        this.allMitarbeiter = allMitarbeiter;
    }

    public List<Mitarbeiter> getSelectedMitarbeiter() {
        return selectedMitarbeiter;
    }

    public void setSelectedMitarbeiter(List<Mitarbeiter> selectedMitarbeiter) {
        this.selectedMitarbeiter = selectedMitarbeiter;
    }

    public List<Mitarbeiter> getFilteredMitarbeiter() {
        return filteredMitarbeiter;
    }

    public void setFilteredMitarbeiter(List<Mitarbeiter> filteredMitarbeiter) {
        this.filteredMitarbeiter = filteredMitarbeiter;
    }
    
    public void deleteMitarbeiter() {
        
        logger.log(Level.INFO, "Start deleteMitarbeiter, selected: {0}", selectedMitarbeiter.size());
	try{
            utx.begin();
            for(Mitarbeiter mitarbeiter : selectedMitarbeiter) { 
                em.remove(em.merge(mitarbeiter));
            }             
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at deleteMitarbeiter, deleted: {0}", selectedMitarbeiter.size());
        } catch (Exception ex) {
            Logger.getLogger(MitarbeiterHandler.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }    
    
    public void insertOrUpdateMitarbeiter(){
        logger.log(Level.INFO, "Start insertOrUpdateMitarbeiter");
        
        Mitarbeiter m;
        Accounts a;
        try {
            utx.begin(); 
            if(currentMitarbeiter == null){
                m = new Mitarbeiter();
                if(currentAccID!=0) {
                    a = (Accounts) em.createNamedQuery("Accounts.findByAccID")
                            .setParameter("accID", currentAccID)
                            .getSingleResult();
                    m.setAccID(a);
                }
                m.setMaName(currentMaName);
                m.setMaVorname(currentMaVorname);
                m.setMaKurz(currentMaKurz);
                m.setMaTitel(currentMaTitel);
                m.setSperrDatZeitVon(currentSperrDatZeitVon);
                m.setSperrDatZeitBis(currentSperrDatZeitBis);
                em.persist(m);                 
            } else {
                m = em.find(Mitarbeiter.class, currentMitarbeiter.getMaID());
                if(currentAccID!=0) {
                    a = (Accounts) em.createNamedQuery("Accounts.findByAccID")
                            .setParameter("accID", currentAccID)
                            .getSingleResult();
                    m.setAccID(a);
                }
                m.setMaName(currentMaName);
                m.setMaVorname(currentMaVorname);
                m.setMaKurz(currentMaKurz);
                m.setMaTitel(currentMaTitel);
                m.setSperrDatZeitVon(currentSperrDatZeitVon);
                m.setSperrDatZeitBis(currentSperrDatZeitBis);
                em.merge(m);                
            }
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at insertOrUpdateMitarbeiter");
            
        } catch (Exception ex) {
            Logger.getLogger(MitarbeiterHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }   
    
    public void editMitarbeiter(){
        dialogHeader = "Bearbeiten";
        currentMitarbeiter = selectedMitarbeiter.get(0);
        currentMaName = currentMitarbeiter.getMaName();
        currentMaVorname = currentMitarbeiter.getMaVorname();
        currentMaKurz = currentMitarbeiter.getMaKurz();
        currentMaTitel = currentMitarbeiter.getMaTitel();
        currentSperrDatZeitVon = currentMitarbeiter.getSperrDatZeitVon();
        currentSperrDatZeitBis = currentMitarbeiter.getSperrDatZeitBis();
        try{
            currentAccID = currentMitarbeiter.getAccID().getAccID();
        } catch (Exception ex) {
            Logger.getLogger(MitarbeiterHandler.class.getName())
                    .log(Level.INFO, "No account for selected Mitarbeier exists");
        }
    }
    
    public void newMitarbeiter(){
        dialogHeader = "Neu";
        currentMitarbeiter = null;
        currentMaName = null;
        currentMaVorname = null;
        currentMaKurz = null;
        currentMaTitel = null;
        currentSperrDatZeitVon = null;
        currentSperrDatZeitBis = null;
    }
}

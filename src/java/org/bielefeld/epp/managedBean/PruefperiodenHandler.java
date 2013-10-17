package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
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
import org.bielefeld.epp.entity.Pruefperioden;
import org.bielefeld.epp.util.JPAUtil;

@ManagedBean
@ViewScoped
public class PruefperiodenHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung.PruefperiodenHandler");
    private Pruefperioden currentPruefperioden;
    private List<Pruefperioden> selectedPruefperioden;
    private List<Pruefperioden> filteredPruefperioden;
    private List<Pruefperioden> allPruefperioden;
    
    private int currentPrPeID;
    private Date currentDate;
    private String currentSemester;
    private String currentTermin;
    
    private String dialogHeader;

    private static final LinkedHashMap<String, String> semester;
    private static final LinkedHashMap<String, String> termin;    
    static {
        semester = new LinkedHashMap<String, String>();
        semester.put("WiSe", "WiSe");
        semester.put("SoSe", "SoSe");
        
        termin = new LinkedHashMap<String, String>();
        termin.put("T1", "1. Termin");
        termin.put("T2", "2. Termin");        
    }
    
    @PostConstruct
    private void init(){
        allPruefperioden = this.getPruefperiodenByWeek(1);
    }
    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    public PruefperiodenHandler() {}
    
    public PruefperiodenHandler(EntityManager em) {
        this.em = em;
    }

    public Pruefperioden getCurrentPruefperioden() {
        return currentPruefperioden;
    }

    public void setCurrentPruefperioden(Pruefperioden currentPruefperioden) {
        this.currentPruefperioden = currentPruefperioden;
    }

    public List<Pruefperioden> getSelectedPruefperioden() {
        return selectedPruefperioden;
    }

    public void setSelectedPruefperioden(List<Pruefperioden> selectedPruefperioden) {
        this.selectedPruefperioden = selectedPruefperioden;
    }

    public List<Pruefperioden> getFilteredPruefperioden() {
        return filteredPruefperioden;
    }

    public void setFilteredPruefperioden(List<Pruefperioden> filteredPruefperioden) {
        this.filteredPruefperioden = filteredPruefperioden;
    }

    public List<Pruefperioden> getAllPruefperioden() {
        return allPruefperioden;
    }

    public void setAllPruefperioden(List<Pruefperioden> allPruefperioden) {
        this.allPruefperioden = allPruefperioden;
    }

    public int getCurrentPrPeID() {
        return currentPrPeID;
    }

    public void setCurrentPrPeID(int currentPrPeID) {
        this.currentPrPeID = currentPrPeID;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }

    public String getCurrentTermin() {
        return currentTermin;
    }

    public void setCurrentTermin(String currentTermin) {
        this.currentTermin = currentTermin;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }

    public static LinkedHashMap<String, String> getSemester() {
        return semester;
    }

    public static LinkedHashMap<String, String> getTermin() {
        return termin;
    }
    
    public List<Pruefperioden> getPruefperioden() {
        
        List<Pruefperioden> lpp = em.createNamedQuery("Pruefperioden.findAll")
                .getResultList();
        
        return lpp;
    }
    
    public void deletePruefperioden() {
        
        logger.log(Level.INFO, 
                "Start deletePruefperioden, selected: {0}", 
                selectedPruefperioden.size());
	try{
            utx.begin();
            for(Pruefperioden pruefperioden : selectedPruefperioden) { 
                em.remove(em.merge(pruefperioden));
            }             
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, 
                    "Success at deletePruefperioden, deleted: {0}", 
                    selectedPruefperioden.size());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }   
    }    
    
    public void insertOrUpdatePruefperioden(){
        logger.log(Level.INFO, "Start insertOrUpdatePruefperioden");
        Pruefperioden pp;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(currentDate); 
        try {
            utx.begin(); 
            
            if(currentPruefperioden == null){
                pp = new Pruefperioden();
                pp.setPPJahr(gc.get(Calendar.YEAR));
                pp.setPPKw(gc.get(Calendar.WEEK_OF_YEAR)-1);
                pp.setPPWoche(1);
                pp.setPPotherKw(gc.get(Calendar.WEEK_OF_YEAR));
                pp.setPruefSemester(currentSemester);
                pp.setPruefTermin(currentTermin);
                em.persist(pp);     
                pp = new Pruefperioden();
                pp.setPPJahr(gc.get(Calendar.YEAR));
                pp.setPPKw(gc.get(Calendar.WEEK_OF_YEAR));
                pp.setPPWoche(2);
                pp.setPPotherKw(gc.get(Calendar.WEEK_OF_YEAR)-1);
                pp.setPruefSemester(currentSemester);
                pp.setPruefTermin(currentTermin); 
                em.persist(pp); 
            } else {
                pp = em.find(Pruefperioden.class, currentPruefperioden.getPrPeID());
                pp.setPPJahr(gc.get(Calendar.YEAR));
                pp.setPPKw(gc.get(Calendar.WEEK_OF_YEAR)-1);
                pp.setPPWoche(1);
                pp.setPPotherKw(gc.get(Calendar.WEEK_OF_YEAR));
                pp.setPruefSemester(currentSemester);
                pp.setPruefTermin(currentTermin); 
                em.merge(pp);           
                
                Pruefperioden otherPP = (Pruefperioden)em.
                        createNamedQuery("Pruefperioden.findByWeeksAndYear").
                        setParameter("pPotherKw", currentPruefperioden.getPPKw()).
                        setParameter("pPKw", currentPruefperioden.getPPotherKw()).
                        setParameter("pPJahr", gc.get(Calendar.YEAR)).
                        getSingleResult();
                
                pp = em.find(Pruefperioden.class, otherPP.getPrPeID());
                pp.setPPJahr(gc.get(Calendar.YEAR));
                pp.setPPKw(gc.get(Calendar.WEEK_OF_YEAR));
                pp.setPPWoche(2);
                pp.setPPotherKw(gc.get(Calendar.WEEK_OF_YEAR)-1);
                pp.setPruefSemester(currentSemester);
                pp.setPruefTermin(currentTermin);
                em.merge(pp);                  
            }
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at insertOrUpdatePruefperioden");
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }   
    
    public void editPruefperioden(){
        dialogHeader = "Bearbeiten";
        currentPruefperioden = selectedPruefperioden.get(0);
        GregorianCalendar gc = new GregorianCalendar();

        gc.setFirstDayOfWeek(Calendar.MONDAY);
        gc.set(Calendar.WEEK_OF_YEAR, currentPruefperioden.getPPKw()+1);
        gc.set(Calendar.YEAR, currentPruefperioden.getPPJahr());    
        gc.set(Calendar.DAY_OF_WEEK, 2);
        gc.set(Calendar.HOUR_OF_DAY, 8);
        gc.set(Calendar.MINUTE, 0);  
        gc.set(Calendar.SECOND, 0);          
        currentDate = gc.getTime();
        
        currentSemester = semester.get(currentPruefperioden.getPruefSemester());
        currentTermin = termin.get(currentPruefperioden.getPruefTermin());
    }
    
    public void newPruefperioden(){
        dialogHeader = "Neu";
        currentPruefperioden = null;
        currentDate = new Date();
        currentSemester = null;
        currentTermin = null;
    }       
    
    public List<Pruefperioden> getPruefperiodenByWeek(int pwoche) {
        
        try {
            List<Pruefperioden> lpp = em
                    .createNamedQuery("Pruefperioden.findAllForWeek"+pwoche)
                    .getResultList();
            
            return lpp;
            
        }
        catch (Exception ex){
            Logger.getLogger(PruefperiodenHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    public Pruefperioden getPruefperiodenByPrPeID(int ppid) {
        
        try {
            currentPruefperioden = (Pruefperioden)em
                    .createNamedQuery("Pruefperioden.findByPrPeID")
                    .setParameter("prPeID", ppid)
                    .getSingleResult();
            
            return currentPruefperioden;
        }
        catch (Exception ex){
            Logger.getLogger(PruefperiodenHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }        
    }    
    
    public String getAsString(Pruefperioden pp){
        return (pp.getPruefSemester().equals("WiSe") ? 
                "Wintersemester '"+(pp.getPPJahr()-1-2000)+"/'"+(pp.getPPJahr()-2000) : 
                "Sommersemester '"+(pp.getPPJahr()-2000))+" "+
                pp.getPruefTermin()+" "+
                (pp.getPPKw() < pp.getPPotherKw() ? "1. Woche" : "2. Woche");
                
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.bielefeld.epp.entity.Modul;
import org.bielefeld.epp.entity.Pruefcodes;
import org.bielefeld.epp.util.JPAUtil;

@ManagedBean
@ViewScoped
public class PruefcodesHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung.PruefcodesHandler"); 
    
    Pruefcodes currentPruefcodes;
    private List<Pruefcodes> selectedPruefcodes;
    private List<Pruefcodes> filteredPruefcodes;
    private List<Pruefcodes> allPruefcodes;
    
    private int currentPcid;
    private int currentPcode;
    private char currentPabschnitt;
    private char currentPart;
    private String currentPform;
    private String currentPpflicht;
    private Modul currentModul;
    
    private String dialogHeader;

    @PostConstruct
    private void init(){
        allPruefcodes = this.getPruefcodes();
    }
    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    public PruefcodesHandler() {}
        
    public PruefcodesHandler(EntityManager em) {
        this.em = em;
    }

    public Pruefcodes getCurrentPruefcodes() {
        return currentPruefcodes;
    }

    public void setCurrentPruefcodes(Pruefcodes currentPruefcodes) {
        this.currentPruefcodes = currentPruefcodes;
    }

    public List<Pruefcodes> getSelectedPruefcodes() {
        return selectedPruefcodes;
    }

    public void setSelectedPruefcodes(List<Pruefcodes> selectedPruefcodes) {
        this.selectedPruefcodes = selectedPruefcodes;
    }

    public List<Pruefcodes> getFilteredPruefcodes() {
        return filteredPruefcodes;
    }

    public void setFilteredPruefcodes(List<Pruefcodes> filteredPruefcodes) {
        this.filteredPruefcodes = filteredPruefcodes;
    }

    public List<Pruefcodes> getAllPruefcodes() {
        return allPruefcodes;
    }

    public void setAllPruefcodes(List<Pruefcodes> allPruefcodes) {
        this.allPruefcodes = allPruefcodes;
    }

    public int getCurrentPcid() {
        return currentPcid;
    }

    public void setCurrentPcid(int currentPcid) {
        this.currentPcid = currentPcid;
    }

    public int getCurrentPcode() {
        return currentPcode;
    }

    public void setCurrentPcode(int currentPcode) {
        this.currentPcode = currentPcode;
    }

    public char getCurrentPabschnitt() {
        return currentPabschnitt;
    }

    public void setCurrentPabschnitt(char currentPabschnitt) {
        this.currentPabschnitt = currentPabschnitt;
    }

    public char getCurrentPart() {
        return currentPart;
    }

    public void setCurrentPart(char currentPart) {
        this.currentPart = currentPart;
    }

    public String getCurrentPform() {
        return currentPform;
    }

    public void setCurrentPform(String currentPform) {
        this.currentPform = currentPform;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public Modul getCurrentModul() {
        return currentModul;
    }

    public void setCurrentModul(Modul currentModul) {
        this.currentModul = currentModul;
    }

    public String getCurrentPpflicht() {
        return currentPpflicht;
    }

    public void setCurrentPpflicht(String currentPpflicht) {
        this.currentPpflicht = currentPpflicht;
    }
    
    public List<Pruefcodes> getPruefcodes() {
        
        List<Pruefcodes> lpc = em.createNamedQuery("Pruefcodes.findAll")
                .getResultList();
        
        return lpc;
    }
    
    public void deletePruefcodes() {
        
        logger.log(Level.INFO, "Start deletePruefcodes, selected: {0}", selectedPruefcodes.size());
	try{
            utx.begin();
            for(Pruefcodes pc : selectedPruefcodes) { 
                em.remove(em.merge(pc));
            }             
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at deletePruefcodes, deleted: {0}", selectedPruefcodes.size());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }   
    }    
    
    public void insertOrUpdatePruefcodes(){
        logger.log(Level.INFO, "Start insertOrUpdatePruefcodes");
        
        Pruefcodes pc;
        Modul m;
        try {
            utx.begin(); 
            if(currentPruefcodes == null){
                pc = new Pruefcodes();
                pc.setPrAbschnitt(currentPabschnitt);
                pc.setPrArt(currentPart);
                pc.setPrCode(currentPcode);
                pc.setPrForm(currentPform);
                pc.setPrPflicht(currentPpflicht);
                em.persist(pc);              
                utx.commit();
                
                
                utx.begin();
                
                m = em.find(Modul.class, currentModul.getModID());
                m.setPcid(pc);
                em.merge(m);
            } else {
                pc = em.find(Pruefcodes.class, currentPruefcodes.getPcid());
                pc.setPrAbschnitt(currentPabschnitt);
                pc.setPrArt(currentPart);
                pc.setPrCode(currentPcode);
                pc.setPrForm(currentPform);
                pc.setPrPflicht(currentPpflicht);                
                em.merge(pc);      
                utx.commit();
                
                
                utx.begin();
                
                m = em.find(Modul.class, currentModul.getModID());
                m.setPcid(pc);
                em.merge(m);                
            }
            utx.commit(); 
            
            this.init();
            logger.log(Level.INFO, "Success at insertOrUpdatePruefcodes");
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }   
    
    public void editPruefcodes(){
        dialogHeader = "Bearbeiten";
        currentPruefcodes = selectedPruefcodes.get(0);
        currentPabschnitt = currentPruefcodes.getPrAbschnitt();
        currentPart = currentPruefcodes.getPrArt();
        currentPcode = currentPruefcodes.getPrCode();
        currentPform = currentPruefcodes.getPrForm();
        currentPpflicht = currentPruefcodes.getPrPflicht();
        
        currentModul = (Modul) em.createNamedQuery("Modul.findByPcid")
                .setParameter("pcid", currentPruefcodes)
                .getResultList().get(0);
    }
    
    public void newPruefcodes(){
        dialogHeader = "Neu";
        currentPruefcodes = null;
        currentPabschnitt = ' ';
        currentPart = ' ';
        currentPcode = 0;
        currentPform = null;
        currentPpflicht = null;
        currentModul = null;
    }    
    
    public Pruefcodes getPruefcodesByPcid(int pcid) {
        
        try{
            logger.log(Level.INFO, "Start getPruefcodesByPcid, pcid: {0}", pcid);
            currentPruefcodes = (Pruefcodes) em.createNamedQuery("Pruefcodes.findByPcid")
                    .setParameter("pcid", pcid)
                    .getSingleResult();
            logger.log(Level.INFO, "Success at getPruefcodesByPcid, pcid: {0}",
                    currentPruefcodes.getPcid());
            
            return currentPruefcodes;
        }
        catch (Exception ex){
            logger.log(Level.SEVERE, "New Pcode...");
            return null;
        }        
    }
    
    public String getAsString(Pruefcodes pc){
        String s = pc.getPrCode() + 
                " " + pc.getPrForm() + 
                " (" + pc.getPrAbschnitt() + 
                "/" + pc.getPrPflicht() + 
                "/" + pc.getPrArt() + ")";
                
        return s;
    }
}
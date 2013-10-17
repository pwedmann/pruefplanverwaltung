package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.bielefeld.epp.entity.Pruefperioden;
import org.bielefeld.epp.entity.Pruefplaneintrag;
import org.bielefeld.epp.entity.Studiengang;
import org.bielefeld.epp.util.JPAUtil;
import org.bielefeld.epp.util.PruefplanKalenderHandler;
import org.bielefeld.epp.util.PruefplanTabelleHandler;

@ManagedBean
@ViewScoped
public class PruefplaneintragHandler implements Serializable {
    private List<Pruefplaneintrag> allPpe;
    
    @Resource
    private UserTransaction utx;   

    @ManagedProperty(value="#{pruefplanKalenderHandler}")
    private PruefplanKalenderHandler pkh;    
    
    @ManagedProperty(value="#{pruefplanTabelleHandler}")
    private PruefplanTabelleHandler pth;  
    
    @PersistenceContext
    private EntityManager em;   
    
    @PostConstruct
    public void initAllPpe(){
        allPpe = this.findAllPPE();
    }
    
    public  List<Pruefplaneintrag> getAllPpe(){
        return allPpe;
    }
    
    public PruefplaneintragHandler() {}
    
    public PruefplaneintragHandler(EntityManager em) {
        this.em = em;
    }
    
    public PruefplanKalenderHandler getPkh() {
        return pkh;
    }

    public void setPkh(PruefplanKalenderHandler pkh) {
        this.pkh = pkh;
    }    

    public PruefplanTabelleHandler getPth() {
        return pth;
    }

    public void setPth(PruefplanTabelleHandler pth) {
        this.pth = pth;
    }
    
    public void insertOrUpdatePPE() {
        Logger.getLogger(PruefplaneintragHandler.class.getName())
                .log(Level.INFO, "Start insertPPE");
        FacesContext context = FacesContext.getCurrentInstance(); 
        
        Pruefplaneintrag ppe = new Pruefplaneintrag();
        try {   
            utx.begin();
            Pruefperioden currenPruefperiode = pkh.getCurrentPruefperiode();
            if(pkh.getCurrentPPE() == null) {
                ppe.setSgmid(pkh.getCurrentSgmodul());
                ppe.setPruefPeriode(currenPruefperiode);
                ppe.setPPEDatZeit(pkh.getCurrentPPEDatZeit());
                ppe.setErstPruefID(pkh.getCurrentErstPruefID());
                ppe.setZweitPruefID(pkh.getCurrentZweitPruefID());
                ppe.setRid(pkh.getCurrentRid());
                ppe.setAnmeldeZahl(pkh.getCurrentAnmeldeZahl());
                ppe.setPfid(pkh.getCurrentPfid());
                
                em.persist(ppe);
                Logger.getLogger(PruefplaneintragHandler.class.getName())
                        .log(Level.INFO, "ppe inserted");         
                context.addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Prüfung angelegt: ", 
                        ppe.getSgmid().getModID().getModName()));
            } else {
                ppe = em.find(Pruefplaneintrag.class,pkh.getCurrentPPE().getPpid());
                ppe.setSgmid(pkh.getCurrentSgmodul());
                ppe.setPruefPeriode(currenPruefperiode);
                ppe.setPPEDatZeit(pkh.getCurrentPPEDatZeit());
                ppe.setErstPruefID(pkh.getCurrentErstPruefID());
                ppe.setZweitPruefID(pkh.getCurrentZweitPruefID());
                ppe.setRid(pkh.getCurrentRid());
                ppe.setAnmeldeZahl(pkh.getCurrentAnmeldeZahl());
                ppe.setPfid(pkh.getCurrentPfid());
                
                em.merge(ppe);
                Logger.getLogger(PruefplaneintragHandler.class.getName())
                        .log(Level.INFO, "ppe updated"); 
                context.addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Prüfung aktualisiert: ", 
                        ppe.getSgmid().getModID().getModName()));                
            }
            utx.commit();
            
            
            this.updateStatus(ppe, pkh.getNewConflicts());
            allPpe = this.findAllPPE();
            pkh.updateBothWeeks();
        } catch (Exception ex) {
            context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Internal error", "")); 
            Logger.getLogger(PruefplaneintragHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }    

    public void deletePPE(int w, int d, String t) {
        FacesMessage msg;  
        Pruefplaneintrag ppe = pkh.findPPEByTimeAndDay(w,d,t);
        Pruefperioden currenPruefperiode = ppe.getPruefPeriode();
        int ppeId = ppe.getPpid();
        Logger.getLogger(PruefplaneintragHandler.class.getName())
                .log(Level.INFO, "Start deletePPE, selected: {0}", ppeId);  
	try{
            
            utx.begin();
            em.remove(em.merge(ppe));
            utx.commit();
            
            this.updateStatus(ppe,new ArrayList<Pruefplaneintrag>());
            Logger.getLogger(PruefplaneintragHandler.class.getName())
                    .log(Level.INFO, "Success at deletePPE, deleted: {0}", ppe.getPpid());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Prüfung gelöscht: ", 
                    ppe.getSgmid().getModID().getModName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            allPpe = this.findAllPPE();
            pkh.updateWeek(currenPruefperiode.getPPWoche());
        } catch (Exception ex) {
            Logger.getLogger(PruefplaneintragHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }    
    }  
  
    public void deletePPEs (){
        FacesMessage msg;  
        Logger.getLogger(PruefplaneintragHandler.class.getName())
                .log(Level.INFO, "Start deletePPEs");        
        try{
            
            for(Pruefplaneintrag p : pth.getSelectedPPEs()){
                utx.begin();
                em.find(Pruefplaneintrag.class, p.getPpid());
                em.remove(em.merge(p));
                utx.commit();
                this.updateStatus(p,new ArrayList<Pruefplaneintrag>());
            }
                        
            allPpe = this.findAllPPE();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Prüfungen gelöscht","");
            FacesContext.getCurrentInstance().addMessage(null, msg);             
        } catch (Exception ex) {
            Logger.getLogger(PruefplaneintragHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }  
    }    
    
    public List<Pruefplaneintrag> findAllPPE(){
        return em.createNamedQuery("Pruefplaneintrag.findAll").getResultList();
    }  
    
    public List<Pruefplaneintrag> findByPruefperiode(Pruefperioden p){
        return em.createNamedQuery("Pruefplaneintrag.findByPruefPeriode").setParameter("pruefPeriode", p).getResultList();
    }    

    
    public List<Pruefplaneintrag> findByPrPeAndSgAndSem(Pruefperioden p, 
            int sem, Studiengang s){
        try {
            return em.createNamedQuery("Pruefplaneintrag.findPPEintraegeWithUeg")
                    .setParameter("prPeID", p.getPrPeID())
                    .setParameter("semester", sem)
                    .setParameter("sgid", s.getSgid())        
                    .getResultList();
        } catch (Exception ex) {
            Logger.getLogger(PruefplaneintragHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }
    }   
    
    public Pruefplaneintrag findByPpeid(int ppeid){
        try {
            return (Pruefplaneintrag)em
                    .createNamedQuery("Pruefplaneintrag.findByPpid")
                    .setParameter("ppid", ppeid).getSingleResult();
        } catch (Exception ex) {
            Logger.getLogger(PruefplaneintragHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void updateStatus (Pruefplaneintrag ppe, ArrayList<Pruefplaneintrag> newConflicts){
        Logger.getLogger(PruefplaneintragHandler.class.getName())
                .log(Level.INFO, "Start updateStatus");
        FacesContext context = FacesContext.getCurrentInstance();         
        try{
            ArrayList<Pruefplaneintrag> existingConflicts = 
                    new ArrayList(ppe.getExistingConflicts());
            ArrayList<Pruefplaneintrag> tempExistsitngConflicts =
                    (ArrayList<Pruefplaneintrag>)existingConflicts.clone();
            ArrayList<Pruefplaneintrag> tempNewConflicts =
                    (ArrayList<Pruefplaneintrag>)newConflicts.clone();            
            boolean oldEqualsNew = true;

            if(!newConflicts.isEmpty()){
                utx.begin();
                em.find(Pruefplaneintrag.class, ppe.getPpid());
                ppe.setExistingConflicts(newConflicts);
                em.merge(ppe);    
                utx.commit();
                
                utx.begin();
                for(Pruefplaneintrag p : newConflicts){
                    Pruefplaneintrag oneOfConflicts = em.find(Pruefplaneintrag.class, p.getPpid());
                    ArrayList<Pruefplaneintrag> current = new ArrayList(oneOfConflicts.getExistingConflicts());
                    if(current.contains(ppe)){continue;}
                    current.add(ppe);
                    oneOfConflicts.setExistingConflicts(current);
                    em.merge(oneOfConflicts);
                }
                utx.commit();
                
                context.addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        "Prüfungen überschneiden sich!", 
                        "Unter Vorbehalt eingetragen"));     
                for(Pruefplaneintrag p : tempExistsitngConflicts){
                    if(!tempNewConflicts.contains(p)){
                        oldEqualsNew = false;
                        Logger.getLogger(PruefplaneintragHandler.class.getName())
                            .log(Level.INFO, "New conflicts found, some old can be resolved");                        
                    } else {
                        existingConflicts.remove(p);
                    }
                }                
            } 
            if(newConflicts.isEmpty() || !oldEqualsNew){
                
                Logger.getLogger(PruefplaneintragHandler.class.getName())
                    .log(Level.INFO, "No new conflicts, remove from other");   
                utx.begin();
                for(Pruefplaneintrag p : existingConflicts){
                    Pruefplaneintrag oneOfConflicts = em.find(Pruefplaneintrag.class, p.getPpid());
                    ArrayList<Pruefplaneintrag> temp = 
                            new ArrayList(oneOfConflicts.getExistingConflicts());
                    temp.remove(ppe);
                    oneOfConflicts.setExistingConflicts(temp);
                    em.merge(oneOfConflicts);
                }    
                utx.commit();
                
                utx.begin();
                if (em.find(Pruefplaneintrag.class, ppe.getPpid()) != null){
                    ppe.setExistingConflicts(newConflicts);
                    em.merge(ppe);    
                    Logger.getLogger(PruefplaneintragHandler.class.getName())
                        .log(Level.INFO, "Success at updateStatus"); 
                } else {
                   Logger.getLogger(PruefplaneintragHandler.class.getName())
                        .log(Level.INFO, "Status updated, ppe can be deleted"); 
                }
                utx.commit();
            }  
        } catch (Exception ex){
            Logger.getLogger(PruefplaneintragHandler.class.getName())
                    .log(Level.SEVERE, null, ex);    
        } 
    }
}

package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import org.bielefeld.epp.entity.Modul;
import org.bielefeld.epp.entity.Pruefcodes;
import org.bielefeld.epp.entity.Pruefer;
import org.bielefeld.epp.entity.Sgmodul;
import org.bielefeld.epp.entity.Studiengang;
import org.bielefeld.epp.util.JPAUtil;

@ManagedBean
@ViewScoped
public class SgmodulHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung.SgmodulHandler");
    private Sgmodul currentSgmodul;
    private List<Sgmodul> selectedSgmodul;
    private List<Sgmodul> filteredSgmodul;
    private List<Sgmodul> allSgmodul;
    
    private static final LinkedHashMap<Integer, String> notPruefDates;
    private static final LinkedHashMap<Integer, String> pruefDates;
    private static final LinkedHashMap<Integer, String> semester;
    private static final LinkedHashMap<String, String> pruefleistung;
    static {
        notPruefDates = new LinkedHashMap<Integer, String>();
        notPruefDates.put(0, "immer geprüft");
        notPruefDates.put(1, "Termin 1. WiSe");
        notPruefDates.put(2, "Termin 2. WiSe");
        notPruefDates.put(3, "Termin 1. SoSe"); 
        notPruefDates.put(4, "Termin 2. SoSe");
        
        pruefDates = new LinkedHashMap<Integer, String>();
        pruefDates.put(0, "Alle Termine");
        pruefDates.put(1, "WiSe T2 / SoSe T1 /SoSe T2");
        pruefDates.put(2, "WiSe T1 / SoSe T1 /SoSe T2");
        pruefDates.put(3, "WiSe T1 / WiSe T2 /SoSe T2"); 
        pruefDates.put(4, "WiSe T1 / WiSe T2 /SoSe T1");
        
        semester = new LinkedHashMap<Integer, String>();
        semester.put(1, "1. Semester");
        semester.put(2, "2. Semester");
        semester.put(3, "3. Semester"); 
        semester.put(4, "4. Semester");
        semester.put(5, "5. Semester");
        semester.put(6, "6. Semester"); 
        
        pruefleistung = new LinkedHashMap<String, String>();
        pruefleistung.put("K", "Klausur");
        pruefleistung.put("M", "mündl. Prüfung");
        pruefleistung.put("H", "Hausarbeit");
    }

    private int currentModID;
    private String currentModKuerzel;
    private String currentModName;
    private Studiengang currentStudiengang;
    private Pruefcodes currentPcid;
    private int currentNewPcid;
    private Pruefer currentPruefer;
    private int currentModSemester;
    private String currentPruefleistung;
    private int currentPruefDate;
    private int currentModGroup;
    private String currentSgmNotiz;
    
    private String dialogHeader;

    @PostConstruct
    private void init(){
        allSgmodul = this.getSgmodul();
    }
    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;
    
    public SgmodulHandler() {}    

    public SgmodulHandler(EntityManager em) {
        this.em = em; 
    }

    public Sgmodul getCurrentSgmodul() {
        return currentSgmodul;
    }

    public void setCurrentSgmodul(Sgmodul currentSgmodul) {
        this.currentSgmodul = currentSgmodul;
    }

    public List<Sgmodul> getSelectedSgmodul() {
        return selectedSgmodul;
    }

    public void setSelectedSgmodul(List<Sgmodul> selectedSgmodul) {
        this.selectedSgmodul = selectedSgmodul;
    }

    public List<Sgmodul> getFilteredSgmodul() {
        return filteredSgmodul;
    }

    public void setFilteredSgmodul(List<Sgmodul> filteredSgmodul) {
        this.filteredSgmodul = filteredSgmodul;
    }

    public List<Sgmodul> getAllSgmodul() {
        return allSgmodul;
    }

    public void setAllSgmodul(List<Sgmodul> allSgmodul) {
        this.allSgmodul = allSgmodul;
    }

    public int getCurrentModID() {
        return currentModID;
    }

    public void setCurrentModID(int currentModID) {
        this.currentModID = currentModID;
    }

    public String getCurrentModKuerzel() {
        return currentModKuerzel;
    }

    public void setCurrentModKuerzel(String currentModKuerzel) {
        this.currentModKuerzel = currentModKuerzel;
    }

    public String getCurrentModName() {
        return currentModName;
    }

    public void setCurrentModName(String currentModName) {
        this.currentModName = currentModName;
    }

    public Studiengang getCurrentStudiengang() {
        return currentStudiengang;
    }

    public void setCurrentStudiengang(Studiengang currentStudiengang) {
        this.currentStudiengang = currentStudiengang;
    }

    public Pruefcodes getCurrentPcid() {
        return currentPcid;
    }

    public void setCurrentPcid(Pruefcodes currentPcid) {
        this.currentPcid = currentPcid;
    }

    public int getCurrentNewPcid() {
        return currentNewPcid;
    }

    public void setCurrentNewPcid(int currentNewPcid) {
        this.currentNewPcid = currentNewPcid;
    }

    public Pruefer getCurrentPruefer() {
        return currentPruefer;
    }

    public void setCurrentPruefer(Pruefer currentPruefer) {
        this.currentPruefer = currentPruefer;
    }

    public int getCurrentModSemester() {
        return currentModSemester;
    }

    public void setCurrentModSemester(int currentModSemester) {
        this.currentModSemester = currentModSemester;
    }

    public String getCurrentPruefleistung() {
        return currentPruefleistung;
    }

    public void setCurrentPruefleistung(String currentPruefleistung) {
        this.currentPruefleistung = currentPruefleistung;
    }

    public int getCurrentPruefDate() {
        return currentPruefDate;
    }

    public void setCurrentPruefDate(int currentPruefDate) {
        this.currentPruefDate = currentPruefDate;
    }

    public int getCurrentModGroup() {
        return currentModGroup;
    }

    public void setCurrentModGroup(int currentModGroup) {
        this.currentModGroup = currentModGroup;
    }

    public String getCurrentSgmNotiz() {
        return currentSgmNotiz;
    }

    public void setCurrentSgmNotiz(String currentSgmNotiz) {
        this.currentSgmNotiz = currentSgmNotiz;
    }

    public static LinkedHashMap<Integer, String> getNotPruefDates() {
        return notPruefDates;
    }

    public static LinkedHashMap<Integer, String> getPruefDates() {
        return pruefDates;
    }

    public static LinkedHashMap<Integer, String> getSemester() {
        return semester;
    }

    public static LinkedHashMap<String, String> getPruefleistung() {
        return pruefleistung;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }
    
    public List<Sgmodul> getSgmodul() {
        
        List<Sgmodul> lsgm = em.createNamedQuery("Sgmodul.findAll")
                .getResultList();
        
        return lsgm;
    }     
    
    public void deleteSgmodul() {
        EntityManager em1 = JPAUtil.getEMF().createEntityManager();
        EntityManager em2 = JPAUtil.getEMF().createEntityManager();
        logger.log(Level.INFO, "Start deleteModul, selected: {0}", 
                selectedSgmodul.size());
        List<Sgmodul> lm;
        List<Modul> tempM = new ArrayList<Modul>();
	try{
            utx.begin();
            for(Sgmodul sgm : selectedSgmodul) { 
                lm = em2.createNamedQuery("Sgmodul.findByModid")
                        .setParameter("modid", sgm.getModID()).getResultList();
                if(lm.size() == 1){
                    logger.log(Level.INFO, "MODUL SOLL GELOESCHT WERDEN");
                    tempM.add(sgm.getModID());
                }
                em1.remove(em1.merge(sgm));
            }   
            utx.commit(); 
            em1.close();
            em2.close();
            
            if(!tempM.isEmpty()){
                utx.begin();
                logger.log(Level.INFO, "MODUL WIRD GELOESCHT");
                em2 = JPAUtil.getEMF().createEntityManager();
                for(Modul m : tempM){
                    em2.remove(em2.merge(m));  
                }
                em2.close();
                utx.commit(); 
            }
            this.init();
            logger.log(Level.INFO, "Success at deleteModul, deleted: {0}",
                    selectedSgmodul.size());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }   
    }    
    
    public void insertOrUpdateSgmodul(){
        logger.log(Level.INFO, "Start insertOrUpdateModul");
        EntityManager em1 = JPAUtil.getEMF().createEntityManager();
        EntityManager em2 = JPAUtil.getEMF().createEntityManager();

        Modul m;
        Sgmodul sgm;
        try {
            if(currentSgmodul == null){
                logger.log(Level.INFO, "Start insertModul");
                utx.begin(); 
                m = new Modul();
                m.setModName(currentModName);
                m.setModKuerzel(currentModKuerzel);
                m.setPcid(currentPcid);
                em1.persist(m);   
                utx.commit();
                
                utx.begin(); 
                sgm = new Sgmodul();
                sgm.setModID(m);
                sgm.setModGrp(currentModGroup);
                sgm.setModSem(currentModSemester);
                sgm.setNichtGeprueft(currentPruefDate);
                sgm.setPrID(currentPruefer);
                sgm.setPruefLeistung(currentPruefleistung);
                sgm.setSGMNotiz(currentSgmNotiz);
                sgm.setSgid(currentStudiengang);
                sgm.setZeitStempel(new Date());
                em2.persist(sgm); 
                utx.commit();
                logger.log(Level.INFO, "Success at insertModul");
            } else {
                logger.log(Level.INFO, "Start updateModul");
                utx.begin(); 
                m = em1.find(Modul.class, currentSgmodul.getModID().getModID());
                m.setModName(currentModName);
                m.setModKuerzel(currentModKuerzel);
                m.setPcid(currentPcid);
                em1.merge(m);   
                utx.commit();
                
                utx.begin(); 
                sgm = em2.find(Sgmodul.class, currentSgmodul.getSgmid());
                sgm.setModID(m);
                sgm.setModGrp(currentModGroup);
                sgm.setModSem(currentModSemester);
                sgm.setNichtGeprueft(currentPruefDate);
                sgm.setPrID(currentPruefer);
                sgm.setPruefLeistung(currentPruefleistung);
                sgm.setSGMNotiz(currentSgmNotiz);
                sgm.setSgid(currentStudiengang);
                sgm.setZeitStempel(new Date());
                em2.merge(sgm); 
                utx.commit();
                logger.log(Level.INFO, "Success at updateModul");
            }
            em1.close();
            em2.close();
            this.init();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }   
    
    public void editSgmodul(){
        dialogHeader = "Bearbeiten";
        currentSgmodul = selectedSgmodul.get(0);
        currentModName = currentSgmodul.getModID().getModName();
        currentModKuerzel = currentSgmodul.getModID().getModKuerzel();
        currentPcid = currentSgmodul.getModID().getPcid();
        currentModSemester = currentSgmodul.getModSem();
        currentPruefDate = currentSgmodul.getNichtGeprueft();
        currentPruefer = currentSgmodul.getPrID();
        currentPruefleistung = currentSgmodul.getPruefLeistung();    
        currentSgmNotiz = currentSgmodul.getSGMNotiz();
        currentStudiengang = currentSgmodul.getSgid();       
    }
    
    public void newSgmodul(){
        dialogHeader = "Neu";
        currentSgmodul = null;
        currentModName = null;
        currentModKuerzel = null;
        currentPcid = null;
        currentModSemester = 1;
        currentPruefDate = 0;
        currentPruefer = null;
        currentPruefleistung = null;    
        currentSgmNotiz = null;
        currentStudiengang = null;            
    }
    
    public Sgmodul getSgmodulBySgmid(int sgmid) {
        
        try{
            logger.log(Level.INFO, 
                    "Start getSgmodulBySgmid, sgmid: {0}", sgmid);
            currentSgmodul = (Sgmodul) em
                    .createNamedQuery("Sgmodul.findBySgmid")
                    .setParameter("sgmid", sgmid).getSingleResult();
            Logger.getLogger(SgmodulHandler.class.getName()).log(Level.INFO, 
                    "Success at getSgmodulBySgmid, sgmid: {0}", 
                    currentSgmodul.getSgmid());
            
            return currentSgmodul;
        }
        catch (Exception ex){
            Logger.getLogger(SgmodulHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }        
    } 
    
    public List<Sgmodul> getSgmodulBySg (Studiengang sg){
        
        try{
            Logger.getLogger(SgmodulHandler.class.getName()).log(Level.INFO, 
                    "Start getSgmodulBySg, sg: {0}", sg.getSgid());
            List<Sgmodul> lsgm = (List<Sgmodul>)em
                    .createNamedQuery("Sgmodul.findBySgid")
                    .setParameter("sgid", sg)
                    .getResultList();
            Logger.getLogger(SgmodulHandler.class.getName()).log(Level.INFO, 
                    "Success at getSgmodulBySg");
            
            return lsgm;
        }
        catch (Exception ex){
            Logger.getLogger(SgmodulHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public List<Sgmodul> getSgmodulBySgAndSem (Studiengang sg, int sem){
        
        try{
            Logger.getLogger(SgmodulHandler.class.getName()).log(Level.INFO, 
                    "Start getSgmodulBySgAndSem");
            List<Sgmodul> lsgm = (List<Sgmodul>)em
                    .createNamedQuery("Sgmodul.findBySgidAndSem")
                    .setParameter("sgid", sg)
                    .setParameter("sem", sem)
                    .getResultList();
            Logger.getLogger(SgmodulHandler.class.getName()).log(Level.INFO, 
                    "Success at getSgmodulBySgAndSem");
            
            return lsgm;
        }
        catch (Exception ex){
            Logger.getLogger(SgmodulHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }
    }    
    
    public String getAsString(Sgmodul sgm){
        String s = sgm.getModID().getModName() + 
                " bei " + sgm.getPrID().getPrVorname() +
                " " + sgm.getPrID().getPrName();
        return s;
    }
    
    public String getPruefDateAsString(Sgmodul sgm){
        String s = notPruefDates.get(sgm.getNichtGeprueft());
        return s;        
    }
}

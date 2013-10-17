package org.bielefeld.epp.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.bielefeld.epp.entity.Pruefer;
import org.bielefeld.epp.entity.Pruefperioden;
import org.bielefeld.epp.entity.Pruefplaneintrag;
import org.bielefeld.epp.entity.Pruefungsform;
import org.bielefeld.epp.entity.Raum;
import org.bielefeld.epp.entity.Sgmodul;
import org.bielefeld.epp.entity.Studiengang;
import org.bielefeld.epp.managedBean.PruefplaneintragHandler;
import org.bielefeld.epp.managedBean.SgmodulHandler;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class PruefplanKalenderHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung.PruefplanKalenderHandler");    
    private String[] time = {"8","9","10","11","12","13","14","15","16","17","18","19"};
    private static final LinkedHashMap<String, Integer> semester;
    static
    {
        semester = new LinkedHashMap<String, Integer>();
        semester.put("1. Semester", 1);
        semester.put("2. Semester", 2);
        semester.put("3. Semester", 3); 
        semester.put("4. Semester", 4);
        semester.put("5. Semester", 5);
        semester.put("6. Semester", 6); 
        semester.put("Alle Semester (Ü)", 7);
    }
    
    @ManagedProperty(value="#{pruefplanTabelleHandler}")
    private PruefplanTabelleHandler pth;   
    
    @PersistenceContext
    private EntityManager em; 
    
    private LinkedHashMap<String, Pruefplaneintrag> firstWeekPPE = new LinkedHashMap();
    private LinkedHashMap<String, Pruefplaneintrag> secondWeekPPE = new LinkedHashMap();
    
    private Studiengang firstWeekStudiengang, secondWeekStudiengang; //Studiengang der 1./2. Woche
    private Pruefperioden firstWeekPruefperiode, secondWeekPruefperiode; //Prüfperiode der 1./2. Woche
    private int firstWeekSemester, secondWeekSemester;   //Semester der 1./2. Woche

    private Pruefplaneintrag currentPPE;
    private int currentWeek;
    
    private Studiengang currentStudiengang;
    private int currentSemester;
    private Pruefperioden currentPruefperiode;
    private Sgmodul currentSgmodul;
    private Date currentPPEDatZeit;
    private Pruefer currentErstPruefID;
    private Pruefer currentZweitPruefID;
    private Raum currentRid;
    private int currentAnmeldeZahl;
    private Pruefungsform currentPfid;
    private int currentStatus;
    
    private ArrayList<Pruefplaneintrag> newConflicts;
    private Pruefplaneintrag selectedConflictPPE;
    
    private String minDate, maxDate;
    
    public PruefplanKalenderHandler() {}

    public PruefplanKalenderHandler(EntityManager em) {
        this.em = em;
    }    
    public String[] getTime() {
        return time;
    }

    public PruefplanTabelleHandler getPth() {
        return pth;
    }

    public void setPth(PruefplanTabelleHandler pth) {
        this.pth = pth;
    }

    public LinkedHashMap<String, Integer> getSemester() {
        return semester;
    }
    
    public Sgmodul getCurrentSgmodul() {
        return currentSgmodul;
    }

    public void setCurrentSgmodul(Sgmodul currentSgmodul) {
        this.currentSgmodul = currentSgmodul;
    }

    public Pruefperioden getCurrentPruefperiode() {
        return currentPruefperiode;
    }

    public void setCurrentPruefperiode(Pruefperioden currentPruefperiode) {
        this.currentPruefperiode = currentPruefperiode;
    }

    public Date getCurrentPPEDatZeit() {
        return currentPPEDatZeit;
    }

    public void setCurrentPPEDatZeit(Date currentPPEDatZeit) {
        this.currentPPEDatZeit = currentPPEDatZeit;
    }

    public Pruefer getCurrentErstPruefID() {
        return currentErstPruefID;
    }

    public void setCurrentErstPruefID(Pruefer currentErstPruefID) {
        this.currentErstPruefID = currentErstPruefID;
    }

    public Pruefer getCurrentZweitPruefID() {
        return currentZweitPruefID;
    }

    public void setCurrentZweitPruefID(Pruefer currentZweitPruefID) {
        this.currentZweitPruefID = currentZweitPruefID;
    }

    public Raum getCurrentRid() {
        return currentRid;
    }

    public void setCurrentRid(Raum currentRid) {
        this.currentRid = currentRid;
    }

    public int getCurrentAnmeldeZahl() {
        return currentAnmeldeZahl;
    }

    public void setCurrentAnmeldeZahl(int currentAnmeldeZahl) {
        this.currentAnmeldeZahl = currentAnmeldeZahl;
    }

    public Pruefungsform getCurrentPfid() {
        return currentPfid;
    }

    public void setCurrentPfid(Pruefungsform currentPfid) {
        this.currentPfid = currentPfid;
    }
    
    public Pruefplaneintrag getCurrentPPE() {
        return currentPPE;
    }  
    
    public void setCurrentPPE(Pruefplaneintrag currentPPE) {
        this.currentPPE = currentPPE;
    }      

    public int getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
    }

    public Studiengang getCurrentStudiengang() {
        return currentStudiengang;
    }

    public void setCurrentStudiengang(Studiengang currentStudiengang) {
        this.currentStudiengang = currentStudiengang;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }
    
/*------------------------Studiengänge der 1. und 2. PW-----------------------*/    
    public Studiengang getFirstWeekStudiengang() {
        return firstWeekStudiengang;
    }

    public void setFirstWeekStudiengang(Studiengang firstWeekStudiengang) {
        this.firstWeekStudiengang = firstWeekStudiengang;
    }    

    public Studiengang getSecondWeekStudiengang() {
        return secondWeekStudiengang;
    }

    public void setSecondWeekStudiengang (Studiengang secondWeekStudiengang) {
        this.secondWeekStudiengang = secondWeekStudiengang;
    }
/*-----------------------------ENDE Studiengänge------------------------------*/ 
/*------------------------Prüfperioden der 1. und 2. PW-----------------------*/    
    public Pruefperioden getFirstWeekPruefperiode() {
        return firstWeekPruefperiode;
    }

    public void setFirstWeekPruefperiode(Pruefperioden firstWeekPruefperiode) {
        this.firstWeekPruefperiode = firstWeekPruefperiode;
    }

    public Pruefperioden getSecondWeekPruefperiode() {
        return secondWeekPruefperiode;
    }

    public void setSecondWeekPruefperiode(Pruefperioden secondWeekPruefperiode) {
        this.secondWeekPruefperiode = secondWeekPruefperiode;
    }    
/*-----------------------------ENDE Prüfperioden------------------------------*/     
/*--------------------------Semester der 1. und 2. PW-------------------------*/
    public int getFirstWeekSemester() {
        return firstWeekSemester;
    }

    public void setFirstWeekSemester(int firstWeekSemester) {
        this.firstWeekSemester = firstWeekSemester;
    }

    public int getSecondWeekSemester() {
        return secondWeekSemester;
    }

    public void setSecondWeekSemester(int secondWeekSemester) {
        this.secondWeekSemester = secondWeekSemester;
    }
/*--------------------------------ENDE Semester-------------------------------*/ 

/*--------------------------Konflikte mit currentPPE--------------------------*/
    public ArrayList<Pruefplaneintrag> getNewConflicts() {
        return newConflicts;
    }

    public void setNewConflicts(ArrayList<Pruefplaneintrag> newConflicts) {
        this.newConflicts = newConflicts;
    }

    public Pruefplaneintrag getSelectedConflictPPE() {
        return selectedConflictPPE;
    }

    public void setSelectedConflictPPE(Pruefplaneintrag selectedConflictPPE) {
        this.selectedConflictPPE = selectedConflictPPE;
    }
/*--------------------------------ENDE Konflikte------------------------------*/ 

/*-------------------------Termine der 1. und 2. Woche------------------------*/     
    public String findPPDates(int w, int d){
        switch(w){
            case 1: currentPruefperiode = firstWeekPruefperiode; 
                    break;
            case 2: 
                    currentPruefperiode = secondWeekPruefperiode; 
                    break;
        }        
        
        try{        
            GregorianCalendar gc = new GregorianCalendar();

            gc.setFirstDayOfWeek(Calendar.MONDAY);
            gc.set(Calendar.WEEK_OF_YEAR, currentPruefperiode.getPPKw()+1);
            gc.set(Calendar.YEAR, currentPruefperiode.getPPJahr());
            gc.set(Calendar.DAY_OF_WEEK, d+1);
      
            return (gc.get(Calendar.DATE))+"."+(gc.get(Calendar.MONTH)+1)+"."+gc.get(Calendar.YEAR);
        }
        catch (Exception e){
            Logger.getLogger(PruefplanKalenderHandler.class.getName())
                    .log(Level.WARNING, 
                    "Kalender nicht initialisiert");
            return "...";
        }
    }  
    public GregorianCalendar findPPDatesGC(int w, int d, String t){
        switch(w){
            case 1: currentPruefperiode = firstWeekPruefperiode; 
                    break;
            case 2: 
                    currentPruefperiode = secondWeekPruefperiode; 
                    break;
        }        
        try{
            
            GregorianCalendar gc = new GregorianCalendar();

            gc.setFirstDayOfWeek(Calendar.MONDAY);
            gc.set(Calendar.WEEK_OF_YEAR, currentPruefperiode.getPPKw()+1);
            gc.set(Calendar.YEAR, currentPruefperiode.getPPJahr());
            gc.set(Calendar.DAY_OF_WEEK, d+1);
            gc.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t));
            gc.set(Calendar.MINUTE, 0);  
            gc.set(Calendar.SECOND, 0);  
            
            return gc;
        }
        catch (Exception e){
            logger.log(Level.INFO, "Error at findPPDatesGC");              
            return null;
        }
    }      
/*--------------------------------ENDE Termine--------------------------------*/   
    public void updateMinMaxDate(){
        GregorianCalendar gc = new GregorianCalendar();
        
        gc.setFirstDayOfWeek(Calendar.MONDAY);
        gc.set(Calendar.WEEK_OF_YEAR, currentPruefperiode.getPPKw()+1);
        gc.set(Calendar.YEAR, currentPruefperiode.getPPJahr());
        gc.set(Calendar.DAY_OF_WEEK, 2);

        minDate = (gc.get(Calendar.DATE))+"/"+(gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR)+" 8:00"; 
        
        gc.set(Calendar.DAY_OF_WEEK, 6);
        
        maxDate = (gc.get(Calendar.DATE))+"/"+(gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR)+" 18:00";
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }
    
/*-------------------------Edit Pruefplaneintrag in pptable-------------------*/
    public void editPPE(){
        try{
            currentPPE = pth.getSelectedPPEs().get(0);

            currentSgmodul = currentPPE.getSgmid();
            currentPruefperiode = currentPPE.getPruefPeriode();
            currentPPEDatZeit = currentPPE.getPPEDatZeit();
            currentErstPruefID = currentPPE.getErstPruefID();
            currentZweitPruefID = currentPPE.getZweitPruefID();
            currentRid = currentPPE.getRid();
            currentAnmeldeZahl = currentPPE.getAnmeldeZahl();
            currentPfid = currentPPE.getPfid();  
            
            currentStudiengang = currentPPE.getSgmid().getSgid();
            currentSemester = currentPPE.getSgmid().getModSem();
            this.updateMinMaxDate();
            try{
                newConflicts = new ArrayList(currentPPE.getExistingConflicts());
            } catch (Exception ex){
                logger.log(Level.INFO, "Existing ppe has no existing conflicts");                    
                newConflicts = new ArrayList();
            }
        } catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
        }
    }     
    public void newPPE(int w, int d, String t){
        try{
            currentPPE = findPPEByTimeAndDay(w,d,t);
            SgmodulHandler sgmh = new SgmodulHandler(em);
            if(currentPPE == null) {
                logger.log(Level.INFO, "Eintrag existiert nicht");
                currentSemester = firstWeekSemester;
                currentStudiengang = firstWeekStudiengang;
                currentWeek = 1;
                if(w == 2){
                    currentWeek = 2;
                    currentStudiengang = secondWeekStudiengang;
                    currentSemester = secondWeekSemester;
                }    
                try {
                    if(currentSemester == 7){
                        currentSgmodul = sgmh.getSgmodulBySg(currentStudiengang)
                                .get(0);
                    } else {
                        currentSgmodul = sgmh.getSgmodulBySgAndSem(currentStudiengang, 
                                currentSemester).get(0);
                    }
                    currentErstPruefID = currentSgmodul.getPrID();
                } catch (IndexOutOfBoundsException iobe){
                    currentSgmodul = new Sgmodul();
                    currentErstPruefID = null;
                    logger.log(Level.INFO, "Warning!!! Can't get Sgmodul at newPPE");                 
                }
                currentPruefperiode = null;
                currentPPEDatZeit = (findPPDatesGC(w,d,t).getTime());
                currentZweitPruefID = null;
                currentRid = null;
                currentAnmeldeZahl = 0;
                currentPfid = null; 
                newConflicts = null;
            } else {
                currentSgmodul = currentPPE.getSgmid();
                currentPruefperiode = currentPPE.getPruefPeriode();
                currentPPEDatZeit = currentPPE.getPPEDatZeit();
                currentErstPruefID = currentPPE.getErstPruefID();
                currentZweitPruefID = currentPPE.getZweitPruefID();
                currentRid = currentPPE.getRid();
                currentAnmeldeZahl = currentPPE.getAnmeldeZahl();
                currentPfid = currentPPE.getPfid();  
                try{
                    newConflicts = new ArrayList(currentPPE.getExistingConflicts());
                    selectedConflictPPE = null;
                } catch (Exception ex){
                    logger.log(Level.INFO, "Existing ppe has no existing conflicts");                    
                    newConflicts = new ArrayList();
                }
            }
            this.updateMinMaxDate();
        } catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    public void newEmptyPPE(){
        pth.setSelectedPPEs(null);
        currentSemester = 0;
        currentStudiengang = null;
        currentSgmodul = null;
        currentPruefperiode = null;
        currentPPEDatZeit = null;
        currentErstPruefID = null;
        currentZweitPruefID = null;
        currentRid = null;
        currentAnmeldeZahl = 0;
        currentPfid = null;
    }
   
    public void updateWeek(int w) {
        List<Pruefplaneintrag> ppeList;
        switch(w){
            case 1: currentPruefperiode = firstWeekPruefperiode; 
                    currentSemester = firstWeekSemester;
                    currentStudiengang = firstWeekStudiengang;
                    firstWeekPPE = new LinkedHashMap<String, Pruefplaneintrag>();
                    break;
            case 2: 
                    currentPruefperiode = secondWeekPruefperiode; 
                    currentSemester = secondWeekSemester;
                    currentStudiengang = secondWeekStudiengang;
                    secondWeekPPE = new LinkedHashMap<String, Pruefplaneintrag>();
                    break;
        }        
        if (currentSemester == 7){
            ppeList =
                    em.createNamedQuery("Pruefplaneintrag.findPPEintraegeAllSem")
                    .setParameter("prPeID", currentPruefperiode)                        
                    .setParameter("sgid", currentStudiengang)
                    .getResultList();                        
        } else{
            ppeList =
                    em.createNamedQuery("Pruefplaneintrag.findPPEintraege")
                    .setParameter("prPeID", currentPruefperiode)                        
                    .setParameter("semester", currentSemester)
                    .setParameter("sgid", currentStudiengang)
                    .getResultList();            
        }
        
        
        String key;
        Pruefperioden pp;
        for(int t = 8; t < 20; t++) {
            for(int d = 1; d < 6; d++) {
                for (Iterator<Pruefplaneintrag> it = ppeList.iterator(); it.hasNext();){
                    Pruefplaneintrag ppe = it.next();
                    pp = ppe.getPruefPeriode();
                    Date date = ppe.getPPEDatZeit();
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(date);
                    if(gc.get(Calendar.HOUR_OF_DAY)==t && gc.get(Calendar.DAY_OF_WEEK)-1==d){
                        key = "t:"+t+",d:"+d;
                        if(pp.getPPKw() < pp.getPPotherKw()) {
                            logger.log(Level.INFO, 
                            "ppe found, key: {0} ", key);
                            firstWeekPPE.put(key, ppe);
                        } else {
                            secondWeekPPE.put(key, ppe);
                        }
                    }
                }                
            }
        }
    }

    public void updateBothWeeks(){
        List<Pruefplaneintrag> ppeList = new ArrayList<Pruefplaneintrag>();
        firstWeekPPE = new LinkedHashMap<String, Pruefplaneintrag>();
        if(firstWeekStudiengang != null && firstWeekPruefperiode != null){
            if (firstWeekSemester == 7){
                ppeList =
                        em.createNamedQuery("Pruefplaneintrag.findPPEintraegeAllSem")
                        .setParameter("prPeID", firstWeekPruefperiode)                        
                        .setParameter("sgid", firstWeekStudiengang)
                        .getResultList();                        
            } else{
                ppeList =
                        em.createNamedQuery("Pruefplaneintrag.findPPEintraege")
                        .setParameter("prPeID", firstWeekPruefperiode)                        
                        .setParameter("semester", firstWeekSemester)
                        .setParameter("sgid", firstWeekStudiengang)
                        .getResultList();            
            }
        }
        
        
        String key;
        Pruefperioden pp;
        if(!ppeList.isEmpty()){
            for(int t = 8; t < 20; t++) {
                for(int d = 1; d < 6; d++) {
                    for (Iterator<Pruefplaneintrag> it = ppeList.iterator(); it.hasNext();){
                        Pruefplaneintrag ppe = it.next();
                        pp = ppe.getPruefPeriode();
                        Date date = ppe.getPPEDatZeit();
                        GregorianCalendar gc = new GregorianCalendar();
                        gc.setTime(date);
                        if(gc.get(Calendar.HOUR_OF_DAY)==t && gc.get(Calendar.DAY_OF_WEEK)-1==d){
                            key = "t:"+t+",d:"+d;
                            if(pp.getPPKw() < pp.getPPotherKw()) {
                                logger.log(Level.INFO, "ppe found, key: {0} ", key);
                                firstWeekPPE.put(key, ppe);
                            } 
                        }
                    }                
                }
            }
        }
        
        secondWeekPPE = new LinkedHashMap<String, Pruefplaneintrag>();
        
        if(secondWeekStudiengang != null && secondWeekPruefperiode != null){
            if (secondWeekSemester == 7){
                ppeList =
                        em.createNamedQuery("Pruefplaneintrag.findPPEintraegeAllSem")
                        .setParameter("prPeID", secondWeekPruefperiode)                        
                        .setParameter("sgid", secondWeekStudiengang)
                        .getResultList();                        
            } else{
                ppeList =
                        em.createNamedQuery("Pruefplaneintrag.findPPEintraege")
                        .setParameter("prPeID", secondWeekPruefperiode)                        
                        .setParameter("semester", secondWeekSemester)
                        .setParameter("sgid", secondWeekStudiengang)
                        .getResultList();            
            }
        }
        
        
        if(!ppeList.isEmpty()){
            for(int t = 8; t < 20; t++) {
                for(int d = 1; d < 6; d++) {
                    for (Iterator<Pruefplaneintrag> it = ppeList.iterator(); it.hasNext();){
                        Pruefplaneintrag ppe = it.next();
                        pp = ppe.getPruefPeriode();
                        Date date = ppe.getPPEDatZeit();
                        GregorianCalendar gc = new GregorianCalendar();
                        gc.setTime(date);
                        if(gc.get(Calendar.HOUR_OF_DAY)==t && gc.get(Calendar.DAY_OF_WEEK)-1==d){
                            key = "t:"+t+",d:"+d;
                            if(pp.getPPKw() > pp.getPPotherKw()) {
                                logger.log(Level.INFO, 
                                "ppe found, key: {0} ", key);
                                secondWeekPPE.put(key, ppe);
                            } 
                        }
                    }                
                }
            }
        }
    }
    
    public Pruefplaneintrag findPPEByTimeAndDay (int w, int d, String t){
        String key = "t:"+t+",d:"+d;
        try {
            switch(w){
                case 1: return firstWeekPPE.get(key);
                case 2: return secondWeekPPE.get(key);
                default:return null;
            }        
        } catch (Exception ex) {
            return null;
        }
    }
    
    public String findPPEByTimeAndDayString (int w, int d, String t){
        String key = "t:"+t+",d:"+d;
        Pruefplaneintrag ppe;
        try {
            switch(w){
                case 1: ppe = firstWeekPPE.get(key);break;
                case 2: ppe = secondWeekPPE.get(key);break;
                default:return null;
            }    

            GregorianCalendar gc = new GregorianCalendar();
            DateFormat df = new SimpleDateFormat("H:mm");
            
            Date startDate = ppe.getPPEDatZeit();
            gc.setTime(startDate);
            String[] pf = ppe.getPfid().getPForm().split("_");
            int pfLength = Integer.parseInt(pf[1]);
            
            gc.add(Calendar.MINUTE, pfLength);
            Date endDate = gc.getTime();
            
            String s =  ppe.getSgmid().getModID().getModKuerzel()+"<br/>"+
                        ppe.getErstPruefID().getPrKurz()+"<br/>"+
                        df.format(startDate) + " - " + df.format(endDate);
            return s;
        } catch (Exception ex) {
            return null;
        }
    }  

    public String findPPEColor (int w, int d, String t){
        String key = "t:"+t+",d:"+d;
        String color = "green";
        try {
            switch(w){
                case 1: 
                    if(!firstWeekPPE.get(key).getExistingConflicts().isEmpty()){
                        color = "red";
                    } break;                  
                case 2: 
                    if(!secondWeekPPE.get(key).getExistingConflicts().isEmpty()){
                        color = "red";
                    } break;
            }  
            return color;
            
        } catch (Exception ex) {
            return "none";
        }
    }    

    public boolean findConflicts(){
        logger.log(Level.INFO, "Start findConflicts");  
        if(
            currentSemester != 0 &&
            currentStudiengang != null &&
            currentSgmodul != null &&
            currentPruefperiode != null &&
            currentPPEDatZeit != null &&
            currentErstPruefID != null &&
            currentRid != null &&
            currentPfid != null   
        ){
            RequestContext context = RequestContext.getCurrentInstance();  
            newConflicts = new ArrayList<Pruefplaneintrag>();    

            boolean conflict = true;

            GregorianCalendar gcStart = new GregorianCalendar();
            GregorianCalendar gcStartOther = new GregorianCalendar();

            Date    startTime = currentPPEDatZeit, /* Start time of current ppe */
                    startTimeOther,
                    endTime,
                    endTimeOther;

            String[] pf = currentPfid.getPForm().split("_");
            int pLength = Integer.parseInt(pf[1]);        
            int pLengthOther;

            PruefplaneintragHandler ppeh = new PruefplaneintragHandler(em);
            List<Pruefplaneintrag> allPPE = ppeh.findByPruefperiode(
                    currentPruefperiode 
            );

            if(allPPE.isEmpty()) { 
                conflict = false;
                context.addCallbackParam("conflict", conflict);
                return conflict;            
            }

            gcStart.setTime(startTime);
            gcStart.add(Calendar.MINUTE, pLength);
            endTime = gcStart.getTime(); /* End time of current ppe */

            if(currentPPE!=null){allPPE.remove(currentPPE);}

            for (Iterator<Pruefplaneintrag> it = allPPE.iterator(); it.hasNext();){
                Pruefplaneintrag ppe = it.next();

                startTimeOther = ppe.getPPEDatZeit(); /* Start time of other ppe */
                pf = ppe.getPfid().getPForm().split("_");
                pLengthOther = Integer.parseInt(pf[1]); /* Length of other ppe */            

                gcStartOther.setTime(startTimeOther);
                if(gcStartOther.get(Calendar.DAY_OF_WEEK) != gcStart.get(Calendar.DAY_OF_WEEK)) { continue; }
                gcStartOther.add(Calendar.MINUTE, pLengthOther);
                endTimeOther = gcStartOther.getTime(); /* End time of other ppe */

                long diffInMins1 = (startTime.getTime() - endTimeOther.getTime()) / 60000;
                long diffInMins2 = (startTimeOther.getTime() - endTime.getTime()) / 60000;
                if(startTime.after(startTimeOther)){
                    if(diffInMins1 < 30){
                        if((currentStudiengang.getSgid() == 7 || 
                                ppe.getSgmid().getSgid().getSgid() == 7 ||
                                currentStudiengang.equals(ppe.getSgmid().getSgid())) &&
                                currentSemester == ppe.getSgmid().getModSem()){
                            newConflicts.add(ppe);
                        } else if(currentErstPruefID.equals(ppe.getErstPruefID())){
                            newConflicts.add(ppe);
                        } else if(currentRid.equals(ppe.getRid())){
                            newConflicts.add(ppe);
                        }
                    }
                } else if (startTime.before(startTimeOther)){
                    if(diffInMins2 < 30){
                        if((currentStudiengang.getSgid() == 7 || 
                                ppe.getSgmid().getSgid().getSgid() == 7 ||
                                currentStudiengang.equals(ppe.getSgmid().getSgid())) &&
                                currentSemester == ppe.getSgmid().getModSem()){
                            newConflicts.add(ppe);
                        } else if(currentErstPruefID.equals(ppe.getErstPruefID())){
                            newConflicts.add(ppe);
                        } else if(currentRid.equals(ppe.getRid())){
                            newConflicts.add(ppe);
                        }
                    }
                } else if (startTime.equals(startTimeOther)){
                    if((currentStudiengang.getSgid() == 7 || 
                            ppe.getSgmid().getSgid().getSgid() == 7 ||
                            currentStudiengang.equals(ppe.getSgmid().getSgid())) &&
                            currentSemester == ppe.getSgmid().getModSem()){
                        newConflicts.add(ppe);
                    } else if(currentErstPruefID.equals(ppe.getErstPruefID())){
                        newConflicts.add(ppe);
                    } else if(currentRid.equals(ppe.getRid())){
                        newConflicts.add(ppe);
                    }
                }
            }
            if(newConflicts.isEmpty()) { 
                conflict = false; 
            }

            context.addCallbackParam("conflict", conflict);
            return conflict;
        } else {
            return true;
        }
    }
    
    public List<Sgmodul> getCurrentModule(){
        SgmodulHandler sgmh = new SgmodulHandler(em);
        if(currentStudiengang!=null){
            if(currentSemester == 7){
                return sgmh.getSgmodulBySg(currentStudiengang);
            }
            return sgmh.getSgmodulBySgAndSem(currentStudiengang,currentSemester);
        }
        return null;
    }
    
    public void updateSgmodul(){
        try{
            logger.log(Level.INFO, "Start updateSgmodul");
            currentSgmodul = this.getCurrentModule().get(0);
            logger.log(Level.INFO, "Success at updateSgmodul");            
        } catch (IndexOutOfBoundsException iobe) {
            logger.log(Level.SEVERE, "Error at updateSgmodul");            
            currentSgmodul = null;
        }
    }
    public void updateErstpruefer(){
        try{
            logger.log(Level.INFO, "Start updateErstpruefer");
            currentErstPruefID = currentSgmodul.getPrID();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error at updateErstpruefer");            
            currentErstPruefID = null;
        }
    } 

    public void switchPPE(){
        logger.log(Level.SEVERE, "Switch from: {0} to: {1}", 
                new Object[]{currentPPE.getPpid(), selectedConflictPPE.getPpid()});     
        if(selectedConflictPPE != null){
            currentPPE = selectedConflictPPE;
            currentSgmodul = currentPPE.getSgmid();
            currentPruefperiode = currentPPE.getPruefPeriode();
            currentPPEDatZeit = currentPPE.getPPEDatZeit();
            currentErstPruefID = currentPPE.getErstPruefID();
            currentZweitPruefID = currentPPE.getZweitPruefID();
            currentRid = currentPPE.getRid();
            currentAnmeldeZahl = currentPPE.getAnmeldeZahl();
            currentPfid = currentPPE.getPfid();  
            
            currentStudiengang = currentPPE.getSgmid().getSgid();
            currentSemester = currentPPE.getSgmid().getModSem();
            try{
                newConflicts = new ArrayList(currentPPE.getExistingConflicts());
            } catch (Exception ex){
                logger.log(Level.INFO, "Existing ppe has no existing conflicts");                    
                newConflicts = new ArrayList();
            }  
        }
    }
    
    public List<Pruefplaneintrag> getExistingConflicts(){
        try{
            return new ArrayList(currentPPE.getExistingConflicts());
        } catch (Exception ex){
            return new ArrayList<Pruefplaneintrag>();
        }
    }  
    
    public boolean render(){
        try{
            return !currentPPE.getExistingConflicts().isEmpty();
        }catch (Exception ex){
            logger.log(Level.INFO, "ppe is new, no existing conflicts");
            return false;
        }
    }
    
    public boolean renderPPE(int w, int d, String time){
        return this.findPPEByTimeAndDay(w,d,time) != null;
    }
}

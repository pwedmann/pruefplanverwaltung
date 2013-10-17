package org.bielefeld.epp.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.bielefeld.epp.entity.Pruefer;
import org.bielefeld.epp.entity.Pruefperioden;
import org.bielefeld.epp.entity.Pruefplaneintrag;
import org.bielefeld.epp.entity.Studiengang;

@ManagedBean
@ViewScoped
public class PrintHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung");
    
    private Pruefperioden currentPruefperiode;
    private Studiengang currentStudiengang;
    private List<Pruefplaneintrag> allPruefungen1;
    private List<Pruefplaneintrag> allPruefungen2;

    private List<String> tableHeader;
    private List<String> dayLabel;
    private List<String> border;
    
    @PostConstruct
    private void init(){
        dayLabel = new ArrayList<String>();
        border = new ArrayList<String>();
        this.getPruefungen();
    }     
    
    @PersistenceContext
    private EntityManager em;    

    public Pruefperioden getCurrentPruefperiode() {
        return currentPruefperiode;
    }

    public void setCurrentPruefperiode(Pruefperioden currentPruefperiode) {
        this.currentPruefperiode = currentPruefperiode;
    }

    public Studiengang getCurrentStudiengang() {
        return currentStudiengang;
    }

    public void setCurrentStudiengang(Studiengang currentStudiengang) {
        this.currentStudiengang = currentStudiengang;
    }

    public List<Pruefplaneintrag> getAllPruefungen(int w) {
        return w == 1 ? allPruefungen1 : allPruefungen2;
    }
    
    public void getPruefungen(){
        logger.log(Level.INFO, "START getPruefungen");
        try{
            
            allPruefungen1 = em
                    .createNamedQuery("Pruefplaneintrag.findPPEintraegeAllSem")
                    .setParameter("prPeID", currentPruefperiode)
                    .setParameter("sgid", currentStudiengang)
                    .getResultList();
            
            Collections.sort(allPruefungen1, new PruefplaneintragComparator());
            
            Pruefperioden p = (Pruefperioden)em
                    .createNamedQuery("Pruefperioden.findByWeeksAndYear")
                    .setParameter("pPKw", currentPruefperiode.getPPotherKw())
                    .setParameter("pPotherKw", currentPruefperiode.getPPKw())
                    .setParameter("pPJahr", currentPruefperiode.getPPJahr())
                    .getSingleResult();      
            allPruefungen2 = em
                    .createNamedQuery("Pruefplaneintrag.findPPEintraegeAllSem")
                    .setParameter("prPeID", p)
                    .setParameter("sgid", currentStudiengang)
                    .getResultList(); 
            Collections.sort(allPruefungen2, new PruefplaneintragComparator());
            
            logger.log(Level.INFO, "WOCHE 1: {0}", allPruefungen1.size());
            logger.log(Level.INFO, "WOCHE 2: {0}", allPruefungen2.size());
        } catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
            allPruefungen1 = null;
            allPruefungen2 = null;
        }
    }
    
    public void updatePrint(){
        tableHeader = new ArrayList<String>();
        String t;
        
        t = currentPruefperiode.getPruefSemester() + 
            currentPruefperiode.getPPJahr();
        tableHeader.add(t);
        t = currentPruefperiode.getPruefTermin().equals("T1") ?
            "1. Termin" : "2. Termin";
        tableHeader.add(t);
        
        GregorianCalendar gc = new GregorianCalendar();

        gc.setFirstDayOfWeek(Calendar.MONDAY);
        gc.set(Calendar.WEEK_OF_YEAR, currentPruefperiode.getPPKw()+1);
        gc.set(Calendar.YEAR, currentPruefperiode.getPPJahr());    
        gc.set(Calendar.DAY_OF_WEEK, 2);
        gc.set(Calendar.HOUR_OF_DAY, 8);
        gc.set(Calendar.MINUTE, 0);  
        gc.set(Calendar.SECOND, 0);           
        
        Date pruefperiodeStart = gc.getTime();
        gc.add(Calendar.DAY_OF_YEAR, 11);
        Date pruefperiodeEnd = gc.getTime();
        
        t = DateFormat
                .getDateInstance(DateFormat.SHORT)
                .format(pruefperiodeStart) + 
            " - " +
            DateFormat
                .getDateInstance(DateFormat.SHORT)
                .format(pruefperiodeEnd);
        tableHeader.add(t);
        this.init();
    }
    
    public String getTableHeader(int n){
        try{
            return tableHeader.get(n);
        } catch (Exception ex){
            return null;
        }
    }
    
    public String getDate(Pruefplaneintrag ppe){
        GregorianCalendar gc = new GregorianCalendar();
        Date d = ppe.getPPEDatZeit();
        gc.setTime(d);
        String key = gc.get(Calendar.DATE)+"/"+(gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR);
        if(!dayLabel.contains(key)){
            dayLabel.add(key);
            return "<strong>" + gc.getDisplayName(Calendar.DAY_OF_WEEK, 
                    Calendar.LONG, Locale.GERMAN) + 
                    "</strong> " +
                    new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
                    .format(d);            
        }
        return null;
    }
    
    public String getModKurz(Pruefplaneintrag ppe){
        return ppe.getSgmid().getModID().getModKuerzel();
    }
    public String getModul(Pruefplaneintrag ppe){
        return ppe.getSgmid().getModID().getModName();
    }    
    public String getCode(Pruefplaneintrag ppe){
        return ppe.getSgmid().getModID().getPcid().getPrCode()+"";
    }    
    public String getPruefer(Pruefplaneintrag ppe){
        String pruefer = "";
        
        Pruefer e = ppe.getErstPruefID();
        Pruefer z = ppe.getZweitPruefID();
        
        if(e!=null){
            pruefer += e.getPrName();
        } 
        if(z!=null){
            pruefer += " / "+z.getPrName();
        }
        return pruefer;
    }    
    public String getForm(Pruefplaneintrag ppe){
        try{
            return ppe.getSgmid().getModID().getPcid().getPrForm();
        } catch (Exception ex){
            return " ";
        }    
    }    
    public String getDauer(Pruefplaneintrag ppe){
        try{
            return ppe.getPfid().getPForm().split("_")[1];
        } catch (Exception ex){
            return "";
        }    }    
    public String getZeit(Pruefplaneintrag ppe){
            return new SimpleDateFormat("HH:mm", Locale.GERMAN)
                    .format(ppe.getPPEDatZeit());    
    }    
    public String getRaum(Pruefplaneintrag ppe){
        try{
            return ppe.getRid().getRName();
        } catch (Exception ex){
            return " ";
        }
    }    
    public String getAnm(Pruefplaneintrag ppe){
        return ppe.getAnmeldeZahl()+"";
    }    
    public String getBorderTop(Pruefplaneintrag ppe){
        GregorianCalendar gc = new GregorianCalendar();
        Date d = ppe.getPPEDatZeit();
        gc.setTime(d);
        String key = gc.get(Calendar.DATE)+"/"+(gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR);
        if(!border.contains(key)){
            border.add(key);
            return "solid 1 px";           
        }
        return "none";        
    }
    public String getBorderBottom(Pruefplaneintrag ppe, int w){
        if(this.getAllPruefungen(w).lastIndexOf(ppe) == 
                this.getAllPruefungen(w).size()-1){
            return "solid 1 px";
        }
        return "none";
    }    
}

package org.bielefeld.epp.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
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


public class ScheduleController implements Serializable {
    
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
    

	private ScheduleModel eventModel;
	
	private ScheduleEvent event = new DefaultScheduleEvent();

    public Pruefperioden getCurrentPruefperiode() {
        return currentPruefperiode;
    }

    public void setCurrentPruefperiode(Pruefperioden currentPruefperiode) {
        this.currentPruefperiode = currentPruefperiode;
    }
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
    
//   public void updateWeek(int w) {
//        List<Pruefplaneintrag> ppeList;
//        switch(w){
//            case 1: currentPruefperiode = firstWeekPruefperiode; 
//                    currentSemester = firstWeekSemester;
//                    currentStudiengang = firstWeekStudiengang;
//                    firstWeekPPE = new LinkedHashMap<String, Pruefplaneintrag>();
//                    break;
//            case 2: 
//                    currentPruefperiode = secondWeekPruefperiode; 
//                    currentSemester = secondWeekSemester;
//                    currentStudiengang = secondWeekStudiengang;
//                    secondWeekPPE = new LinkedHashMap<String, Pruefplaneintrag>();
//                    break;
//        }        
//        if (currentSemester == 7){
//            ppeList =
//                    em.createNamedQuery("Pruefplaneintrag.findPPEintraegeAllSem")
//                    .setParameter("prPeID", currentPruefperiode)                        
//                    .setParameter("sgid", currentStudiengang)
//                    .getResultList();                        
//        } else{
//            ppeList =
//                    em.createNamedQuery("Pruefplaneintrag.findPPEintraege")
//                    .setParameter("prPeID", currentPruefperiode)                        
//                    .setParameter("semester", currentSemester)
//                    .setParameter("sgid", currentStudiengang)
//                    .getResultList();            
//        }
//        
//        
//        String key;
//        Pruefperioden pp;
//        for(int t = 8; t < 20; t++) {
//            for(int d = 1; d < 6; d++) {
//                for (Iterator<Pruefplaneintrag> it = ppeList.iterator(); it.hasNext();){
//                    Pruefplaneintrag ppe = it.next();
//                    pp = ppe.getPruefPeriode();
//                    Date date = ppe.getPPEDatZeit();
//                    GregorianCalendar gc = new GregorianCalendar();
//                    gc.setTime(date);
//                    if(gc.get(Calendar.HOUR_OF_DAY)==t && gc.get(Calendar.DAY_OF_WEEK)-1==d){
//                        key = "t:"+t+",d:"+d;
//                        if(pp.getPPKw() < pp.getPPotherKw()) {
//                            logger.log(Level.INFO, 
//                            "ppe found, key: {0} ", key);
//                            firstWeekPPE.put(key, ppe);
//                        } else {
//                            secondWeekPPE.put(key, ppe);
//                        }
//                    }
//                }                
//            }
//        }
//    }
        
    
    
    
    
	public ScheduleController() {
		eventModel = new DefaultScheduleModel();
		eventModel.addEvent(new DefaultScheduleEvent("Champions League Match", previousDay8Pm(), previousDay11Pm()));
		eventModel.addEvent(new DefaultScheduleEvent("Birthday Party", today1Pm(), today6Pm()));
		eventModel.addEvent(new DefaultScheduleEvent("Breakfast at Tiffanys", nextDay9Am(), nextDay11Am()));
		eventModel.addEvent(new DefaultScheduleEvent("Plant the new garden stuff", theDayAfter3Pm(), fourDaysLater3pm()));
                   eventModel.addEvent(new DefaultScheduleEvent("Test", termini1(1), termini2(1)));

	}
	
	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);	//set random day of month
		
		return date.getTime();
	}
	
//	public Date getInitialDate() {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
//		
//		return calendar.getTime();
//	}
        public Date getInitialDate2() {
            currentPruefperiode = firstWeekPruefperiode; 
            if(currentPruefperiode == null){             
		GregorianCalendar calendar = new GregorianCalendar();
		//calendar.set(calendar.get(Calendar.YEAR), Calendar.WEEK_OF_YEAR, calendar.get(Calendar.DAY_OF_WEEK), 2013, 43, 2);
                   calendar.setFirstDayOfWeek(Calendar.MONDAY);
                   calendar.set(Calendar.YEAR,2013);
                   calendar.set(Calendar.WEEK_OF_YEAR,43);
                   calendar.set(Calendar.DAY_OF_WEEK,3);
                   return calendar.getTime();
            }else{               		        
		GregorianCalendar calendar = new GregorianCalendar();
		//calendar.set(calendar.get(Calendar.YEAR), Calendar.WEEK_OF_YEAR, calendar.get(Calendar.DAY_OF_WEEK), 2013, 43, 2);
                   calendar.setFirstDayOfWeek(Calendar.MONDAY);
                   calendar.set(Calendar.YEAR,currentPruefperiode.getPPJahr());
                   calendar.set(Calendar.WEEK_OF_YEAR,currentPruefperiode.getPPKw()-1);
                   calendar.set(Calendar.DAY_OF_WEEK,1);                       		
		return calendar.getTime();
            }
	}   
         private Date termini1(int d) {
             currentPruefperiode = firstWeekPruefperiode; 
            if(currentPruefperiode == null){             
		GregorianCalendar gc = new GregorianCalendar();
                   gc.setFirstDayOfWeek(Calendar.MONDAY);           
                   gc.set(Calendar.YEAR,2013);
                   gc.set(Calendar.WEEK_OF_YEAR, 43);
                   gc.set(Calendar.DAY_OF_WEEK, 2);
                   gc.set(Calendar.HOUR_OF_DAY, 14);
                   gc.set(Calendar.MINUTE, 0);  
                   gc.set(Calendar.SECOND, 0);  
                   return gc.getTime();
            }else{               		   
                GregorianCalendar gc = new GregorianCalendar();

                gc.setFirstDayOfWeek(Calendar.MONDAY);           
                gc.set(Calendar.YEAR, currentPruefperiode.getPPJahr());
                gc.set(Calendar.WEEK_OF_YEAR, currentPruefperiode.getPPKw()-1);
                gc.set(Calendar.DAY_OF_WEEK, 3);
                gc.set(Calendar.HOUR_OF_DAY, 14);
                gc.set(Calendar.MINUTE, 0);  
                gc.set(Calendar.SECOND, 0);  

                Date mytermini1 = gc.getTime();
                return mytermini1;
            }
	}
    	private Date termini2(int d) {
            currentPruefperiode = firstWeekPruefperiode; 
            if(currentPruefperiode == null){             
		GregorianCalendar gc = new GregorianCalendar();
                   gc.setFirstDayOfWeek(Calendar.MONDAY);           
                   gc.set(Calendar.YEAR,2013);
                   gc.set(Calendar.WEEK_OF_YEAR, 43);
                   gc.set(Calendar.DAY_OF_WEEK, 2);
                   gc.set(Calendar.HOUR_OF_DAY, 15);
                   gc.set(Calendar.MINUTE, 0);  
                   gc.set(Calendar.SECOND, 0);  
                   return gc.getTime();
                
            }else{               		   
                GregorianCalendar gc = new GregorianCalendar();

                gc.setFirstDayOfWeek(Calendar.MONDAY);           
                gc.set(Calendar.YEAR, currentPruefperiode.getPPJahr());
                gc.set(Calendar.WEEK_OF_YEAR, currentPruefperiode.getPPKw()-1);
                gc.set(Calendar.DAY_OF_WEEK,3);
                gc.set(Calendar.HOUR_OF_DAY, 15);
                gc.set(Calendar.MINUTE, 0);  
                gc.set(Calendar.SECOND, 0);  
                Date mytermini2 = gc.getTime();
                return mytermini2;
            }
	}
        
        
                public Date getInitialDate() {
                   currentPruefperiode = firstWeekPruefperiode; 
		GregorianCalendar calendar = new GregorianCalendar();
		//calendar.set(calendar.get(Calendar.YEAR), Calendar.WEEK_OF_YEAR, calendar.get(Calendar.DAY_OF_WEEK), 2013, 43, 2);
                   calendar.setFirstDayOfWeek(Calendar.MONDAY);
                   calendar.set(Calendar.YEAR,2013);
                   calendar.set(Calendar.WEEK_OF_YEAR,4+1);
                   calendar.set(Calendar.DAY_OF_WEEK,1);
                           
		
		return calendar.getTime();
	}
	
	public ScheduleModel getEventModel() {
		return eventModel;
	}
	
	private Calendar today() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.FEBRUARY), calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar;
	}
    
	
	private Date previousDay8Pm() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
		t.set(Calendar.HOUR, 8);
		
		return t.getTime();
	}
	
	private Date previousDay11Pm() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
		t.set(Calendar.HOUR, 11);
		
		return t.getTime();
	}
	
	private Date today1Pm() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.HOUR, 1);
		
		return t.getTime();
	}
	
	private Date theDayAfter3Pm() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);		
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.HOUR, 3);
		
		return t.getTime();
	}

	private Date today6Pm() {
		Calendar t = (Calendar) today().clone(); 
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.HOUR, 6);
		
		return t.getTime();
	}
	
	private Date nextDay9Am() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.AM);
		t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
		t.set(Calendar.HOUR, 9);
		
		return t.getTime();
	}
	
	private Date nextDay11Am() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.AM);
		t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
		t.set(Calendar.HOUR, 11);
		
		return t.getTime();
	}
	
	private Date fourDaysLater3pm() {
		Calendar t = (Calendar) today().clone(); 
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
		t.set(Calendar.HOUR, 3);
		
		return t.getTime();
	}
	
	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}
	
	public void addEvent(ActionEvent actionEvent) {
		if(event.getId() == null) {
            eventModel.addEvent(event);
        }
		else {
            eventModel.updateEvent(event);
        }
		
		event = new DefaultScheduleEvent();
	}
	
	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}
	
	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
	}
	
	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
		
		addMessage(message);
	}
	
	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
		
		addMessage(message);
	}
	
	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}

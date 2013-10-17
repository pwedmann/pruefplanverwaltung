/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bielefeld.epp.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.bielefeld.epp.entity.Pruefer;
import org.bielefeld.epp.entity.Pruefperioden;
import org.bielefeld.epp.entity.Pruefplaneintrag;
import org.bielefeld.epp.entity.Studiengang;
import org.bielefeld.epp.managedBean.PruefperiodenHandler;
import org.bielefeld.epp.managedBean.StudiengangHandler;

@ManagedBean
@ViewScoped
public class PruefplanTabelleHandler implements Serializable {
    private Pruefplaneintrag selectedPPE;
    private List<Pruefplaneintrag> selectedPPEs;
    
    private List<Pruefplaneintrag> filteredPPEs;
    private SelectItem[] studiengangOptions;
    private SelectItem[] pruefperiodenOptions;
    
    @PersistenceContext
    private EntityManager em;    
    
    @PostConstruct
    public void initOptions(){
        ArrayList<String> values;
        ArrayList<String> labels;
        StudiengangHandler sgh = new StudiengangHandler(em);
        List<Studiengang> lsg = sgh.getStudiengang();
        values = new ArrayList<String>(); 
        labels = new ArrayList<String>();
        for(int i = 0; i<lsg.size();i++){
            values.add(lsg.get(i).getSGName());
            labels.add(lsg.get(i).getSGKurz());
        }
        studiengangOptions = createFilterOptions(values, labels);    
        
        PruefperiodenHandler pph = new PruefperiodenHandler(em);
        List<Pruefperioden> lpp = pph.getPruefperiodenByWeek(1);
        values = new ArrayList<String>(); 
        labels = new ArrayList<String>();
        
        for(Pruefperioden pp : lpp){
            values.add(filterByPP(pp));
            labels.add(getPruefperiode(pp));
        }
        pruefperiodenOptions = createFilterOptions(values,labels);          
    }
    
    public Pruefplaneintrag getSelectedPPE() {
        return selectedPPE;
    }

    public void setSelectedPPE(Pruefplaneintrag selectedPPE) {
        this.selectedPPE = selectedPPE;
    }

    public List<Pruefplaneintrag> getSelectedPPEs() {
        return selectedPPEs;
    }

    public void setSelectedPPEs(List<Pruefplaneintrag> selectedPPEs) {
        this.selectedPPEs = selectedPPEs;
    }

    public List<Pruefplaneintrag> getFilteredPPEs() {
        return filteredPPEs;
    }

    public void setFilteredPPEs(List<Pruefplaneintrag> filteredPPEs) {
        this.filteredPPEs = filteredPPEs;
    }

    public String getPruefer(Pruefplaneintrag ppe) {
        String pruefer = "";
        
        Pruefer e = ppe.getErstPruefID();
        Pruefer z = ppe.getZweitPruefID();
        
        if(e!=null){
            pruefer += e.getPrVorname()+" "
                    + e.getPrName()+"<br/>";
        } 
        if(z!=null){
            pruefer += z.getPrVorname()+" "
                    + z.getPrName()+"<br/>";
        }
        return pruefer;
    }
    
    public String getDatZeit(Pruefplaneintrag ppe){
        return DateFormat
                .getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                .format(ppe.getPPEDatZeit());
    }
    
    public String getPruefperiode(Pruefperioden pp){
        String p = pp.getPruefSemester();
        if(p.equals("SoSe")){
            p = "Sommersemester '"+(pp.getPPJahr()-2000);
        } else {
            p = "Wintersemester '"+(pp.getPPJahr()-1-2000)+"/'"+(pp.getPPJahr()-2000);
        }
        return p+" "+pp.getPruefTermin();
    }     
    
    public String getPfDauer(Pruefplaneintrag ppe){
        LinkedHashMap<String, String> pforms = 
                new LinkedHashMap<String, String>();
        pforms.put("K", "Klausur");
        pforms.put("M", "mündl. Prüfung");
        String[] pf = ppe.getPfid().getPForm().split("_");
        String pform = pforms.get(pf[0]);
        int pfLength = Integer.parseInt(pf[1]);
        
        return pform + " (" + pfLength + ")";
    }
    
/*-------------------------------Filters--------------------------------------*/   
    private SelectItem[] createFilterOptions(ArrayList<String> values, ArrayList<String> labels)  {  
        SelectItem[] options = new SelectItem[values.size() + 1];  
  
        options[0] = new SelectItem("", "Alle");  
        for(int i = 0; i < values.size(); i++) {  
            options[i + 1] = new SelectItem(values.get(i), labels.get(i));  
        }  
  
        return options;  
    } 
    
    public SelectItem[] getStudiengangOptions(){
        return studiengangOptions;
    }

    public SelectItem[] getPruefperiodenOptions() {
        return pruefperiodenOptions;
    }
    
    public void clearFilters (){
        filteredPPEs = null;
    }
    public String filterBy(Pruefplaneintrag ppe){
        String filter;
        Pruefperioden pp = ppe.getPruefPeriode();
        filter = pp.getPruefSemester() + 
                 pp.getPruefTermin() + 
                 pp.getPPJahr();
        return filter;
    }  
    public String filterByPP(Pruefperioden pp){
        String filter;
        filter = pp.getPruefSemester() + 
                 pp.getPruefTermin() + 
                 pp.getPPJahr();
        return filter;
    }     
}

package org.bielefeld.epp.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "pruefplaneintrag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruefplaneintrag.findAll", query = "SELECT p FROM Pruefplaneintrag p"),
    @NamedQuery(name = "Pruefplaneintrag.findByPpid", query = "SELECT p FROM Pruefplaneintrag p WHERE p.ppid = :ppid"),
    @NamedQuery(name = "Pruefplaneintrag.findByPPEDatZeit", query = "SELECT p FROM Pruefplaneintrag p WHERE p.pPEDatZeit = :pPEDatZeit"),
    @NamedQuery(name = "Pruefplaneintrag.findByPPTermin", query = "SELECT p FROM Pruefplaneintrag p WHERE p.pPTermin = :pPTermin"),
    @NamedQuery(name = "Pruefplaneintrag.findByErstPruefID", query = "SELECT p FROM Pruefplaneintrag p WHERE p.erstPruefID = :erstPruefID"),
    @NamedQuery(name = "Pruefplaneintrag.findByZweitPruefID", query = "SELECT p FROM Pruefplaneintrag p WHERE p.zweitPruefID = :zweitPruefID"),
    @NamedQuery(name = "Pruefplaneintrag.findByPruefPeriode", query = "SELECT p FROM Pruefplaneintrag p WHERE p.pruefPeriode = :pruefPeriode"),
    @NamedQuery(name = "Pruefplaneintrag.findByAnmeldeZahl", query = "SELECT p FROM Pruefplaneintrag p WHERE p.anmeldeZahl = :anmeldeZahl"),
    @NamedQuery(name = "Pruefplaneintrag.findByAnzMdlPr", query = "SELECT p FROM Pruefplaneintrag p WHERE p.anzMdlPr = :anzMdlPr"),
    @NamedQuery(name = "Pruefplaneintrag.findByStatus", query = "SELECT p FROM Pruefplaneintrag p WHERE p.status = :status"),
    @NamedQuery(name = "Pruefplaneintrag.findByZeitStempel", query = "SELECT p FROM Pruefplaneintrag p WHERE p.zeitStempel = :zeitStempel"),
    @NamedQuery(name = "Pruefplaneintrag.findPPEintraege", query = "SELECT ppe FROM Pruefplaneintrag ppe "
                                                                  + "JOIN ppe.pruefPeriode pp "
                                                                  + "JOIN ppe.sgmid sgm "
                                                                  + "JOIN sgm.sgid sg "
                                                                  + "WHERE pp = :prPeID "
                                                                  + "AND sgm.modSem = :semester "
                                                                  + "AND sg = :sgid"),
    @NamedQuery(name = "Pruefplaneintrag.findPPEintraegeWithUeg", query = "SELECT ppe FROM Pruefplaneintrag ppe "
                                                                  + "JOIN ppe.pruefPeriode pp "
                                                                  + "JOIN ppe.sgmid sgm "
                                                                  + "JOIN sgm.sgid sg "
                                                                  + "WHERE pp = :prPeID "
                                                                  + "AND sgm.modSem = :semester "
                                                                  + "AND (sg = :sgid OR sg.sgid = 7)"),    
    @NamedQuery(name = "Pruefplaneintrag.findPPEintraegeAllSem", query = "SELECT ppe FROM Pruefplaneintrag ppe "
                                                                  + "JOIN ppe.pruefPeriode pp "
                                                                  + "JOIN ppe.sgmid sgm "
                                                                  + "JOIN sgm.sgid sg "
                                                                  + "WHERE pp = :prPeID "
                                                                  + "AND sg = :sgid"),
    @NamedQuery(name = "Pruefplaneintrag.findPPEintraegeAllSg", query = "SELECT ppe FROM Pruefplaneintrag ppe "
                                                                  + "JOIN ppe.pruefPeriode pp "
                                                                  + "JOIN ppe.sgmid sgm "
                                                                  + "JOIN sgm.sgid sg "
                                                                  + "WHERE pp = :prPeID "
                                                                  + "AND sgm.modSem = :semester"),      
    @NamedQuery(name = "Pruefplaneintrag.findPPEintraegeAll", query = "SELECT ppe FROM Pruefplaneintrag ppe "
                                                                  + "JOIN ppe.pruefPeriode pp "
                                                                  + "JOIN ppe.sgmid sgm "
                                                                  + "JOIN sgm.sgid sg "
                                                                  + "WHERE pp = :prPeID "
                                                                  + "AND sg = :sg")        
})
public class Pruefplaneintrag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PPID")
    private Integer ppid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PPEDatZeit")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pPEDatZeit;
    @Basic(optional = true)
    @Column(name = "PPTermin")
    private int pPTermin;
    @Basic(optional = true)
    @Column(name = "AnmeldeZahl")
    private int anmeldeZahl;
    @Basic(optional = true)
    @Column(name = "AnzMdlPr")
    private int anzMdlPr;
    @Basic(optional = true)
    @Column(name = "Status")
    private int status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zeitStempel")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zeitStempel = new Date();
    @JoinTable(name = "conflicts", joinColumns = {
        @JoinColumn(name = "current", referencedColumnName = "PPID")}, inverseJoinColumns = {
        @JoinColumn(name = "conflict", referencedColumnName = "PPID")})
    @ManyToMany
    private Collection<Pruefplaneintrag> existingConflicts;
    @ManyToMany(mappedBy = "existingConflicts")
    private Collection<Pruefplaneintrag> pruefplaneintragCollection1;    
    @JoinColumn(name = "SGMID", referencedColumnName = "SGMID")
    @ManyToOne(optional = false)
    private Sgmodul sgmid;
    @JoinColumn(name = "RID", referencedColumnName = "RID")
    @ManyToOne(optional = true)
    private Raum rid;
    @JoinColumn(name = "PFID", referencedColumnName = "PFID")
    @ManyToOne(optional = false)
    private Pruefungsform pfid;
    @JoinColumn(name = "PruefPeriode", referencedColumnName = "PrPeID")
    @ManyToOne(optional = false)
    private Pruefperioden pruefPeriode;
    @JoinColumn(name = "ErstPruefID", referencedColumnName = "PrID")
    @ManyToOne(optional = false)
    private Pruefer erstPruefID;
    @JoinColumn(name = "MaID", referencedColumnName = "MaID")
    @ManyToOne(optional = true)
    private Mitarbeiter maID;
    @JoinColumn(name = "ZweitPruefID", referencedColumnName = "PrID")
    @ManyToOne(optional = true)
    private Pruefer zweitPruefID;

    public Pruefplaneintrag() {
    }

    public Pruefplaneintrag(Integer ppid) {
        this.ppid = ppid;
    }

    public Pruefplaneintrag(Integer ppid, Date pPEDatZeit, int pPTermin, int anmeldeZahl, int anzMdlPr, int status, Date zeitStempel) {
        this.ppid = ppid;
        this.pPEDatZeit = pPEDatZeit;
        this.pPTermin = pPTermin;
        this.anmeldeZahl = anmeldeZahl;
        this.anzMdlPr = anzMdlPr;
        this.status = status;
        this.zeitStempel = zeitStempel;
    }

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

    public Date getPPEDatZeit() {
        return pPEDatZeit;
    }

    public void setPPEDatZeit(Date pPEDatZeit) {
        this.pPEDatZeit = pPEDatZeit;
    }

    public int getPPTermin() {
        return pPTermin;
    }

    public void setPPTermin(int pPTermin) {
        this.pPTermin = pPTermin;
    }

    public int getAnmeldeZahl() {
        return anmeldeZahl;
    }

    public void setAnmeldeZahl(int anmeldeZahl) {
        this.anmeldeZahl = anmeldeZahl;
    }

    public int getAnzMdlPr() {
        return anzMdlPr;
    }

    public void setAnzMdlPr(int anzMdlPr) {
        this.anzMdlPr = anzMdlPr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getZeitStempel() {
        return zeitStempel;
    }

    public void setZeitStempel(Date zeitStempel) {
        this.zeitStempel = zeitStempel;
    }
    
    @XmlTransient
    public Collection<Pruefplaneintrag> getExistingConflicts() {
        return existingConflicts;
    }

    public void setExistingConflicts(Collection<Pruefplaneintrag> existingConflicts) {
        this.existingConflicts = existingConflicts;
    }

    @XmlTransient
    public Collection<Pruefplaneintrag> getPruefplaneintragCollection1() {
        return pruefplaneintragCollection1;
    }

    public void setPruefplaneintragCollection1(Collection<Pruefplaneintrag> pruefplaneintragCollection1) {
        this.pruefplaneintragCollection1 = pruefplaneintragCollection1;
    }
    
    public Sgmodul getSgmid() {
        return sgmid;
    }

    public void setSgmid(Sgmodul sgmid) {
        this.sgmid = sgmid;
    }

    public Raum getRid() {
        return rid;
    }

    public void setRid(Raum rid) {
        this.rid = rid;
    }

    public Pruefungsform getPfid() {
        return pfid;
    }

    public void setPfid(Pruefungsform pfid) {
        this.pfid = pfid;
    }

    public Pruefperioden getPruefPeriode() {
        return pruefPeriode;
    }

    public void setPruefPeriode(Pruefperioden pruefPeriode) {
        this.pruefPeriode = pruefPeriode;
    }

    public Pruefer getErstPruefID() {
        return erstPruefID;
    }

    public void setErstPruefID(Pruefer erstPruefID) {
        this.erstPruefID = erstPruefID;
    }

    public Mitarbeiter getMaID() {
        return maID;
    }

    public void setMaID(Mitarbeiter maID) {
        this.maID = maID;
    }

    public Pruefer getZweitPruefID() {
        return zweitPruefID;
    }

    public void setZweitPruefID(Pruefer zweitPruefID) {
        this.zweitPruefID = zweitPruefID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ppid != null ? ppid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pruefplaneintrag)) {
            return false;
        }
        Pruefplaneintrag other = (Pruefplaneintrag) object;
        if ((this.ppid == null && other.ppid != null) || (this.ppid != null && !this.ppid.equals(other.ppid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pruefplaneintrag[ ppid=" + ppid + " ]";
    }
    
}

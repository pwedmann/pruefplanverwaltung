/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bielefeld.epp.entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 *
 * @author Itachi
 */

@Entity
@Table(name = "personen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personen.findAll", query = "SELECT p FROM Personen p ORDER BY p.pName, p.pVorname"),
    @NamedQuery(name = "Personen.findByPID", query = "SELECT p FROM Personen p WHERE p.pID = :pID"),
    @NamedQuery(name = "Personen.findByPName", query = "SELECT p FROM Personen p WHERE p.pName = :pName"),
    @NamedQuery(name = "Personen.findByPVorname", query = "SELECT p FROM Personen p WHERE p.pVorname = :pVorname"),
    @NamedQuery(name = "Personen.findByPTitel", query = "SELECT p FROM Personen p WHERE p.pTitel = :pTitel"),
    @NamedQuery(name = "Personen.findByPKurz", query = "SELECT p FROM Personen p WHERE p.pKurz = :pKurz")})



public class Personen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PID")
    private Integer pID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PName")
    private String pName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PVorname")
    private String pVorname;
    @Column(name = "PTitel")
    private String pTitel;
    @Column(name = "PKurz")
    private String pKurz;
    @Column(name = "IstPruefer")
    private String istPruefer;
    @Column(name = "SperrDatZeitVon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sperrDatZeitVon = new Date();
    @Column(name = "SperrDatZeitBis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sperrDatZeitBis = new Date();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pID")
    private Collection<Sgmodul> sgmodulCollection;
    
    public Personen() {
    }

    public Personen(Integer pID) {
        this.pID = pID;
    }
    
    public Personen(Integer pID, String pName, String pVorname, String pTitel, String pKurz) {
        this.pID = pID;
        this.pName = pName;
        this.pVorname = pVorname;
        this.pTitel = pTitel;
        this.pKurz = pKurz;
        this.sperrDatZeitVon = sperrDatZeitVon;
        this.sperrDatZeitBis = sperrDatZeitBis;
    }
    
    public Integer getPID() {
        return pID;
    }

    public void setPID(Integer pID) {
        this.pID = pID;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPVorname() {
        return pVorname;
    }

    public void setPVorname(String pVorname) {
        this.pVorname = pVorname;
    }

    public String getPTitel() {
        return pTitel;
    }

    public void setPTitel(String pTitel) {
        this.pTitel = pTitel;
    }

    public String getPKurz() {
        return pKurz;
    }

    public void setPKurz(String pKurz) {
        this.pKurz = pKurz;
    }
    public String getIstPruefer() {
        return istPruefer;
    }

    public void setIstPruefer(String istPruefer) {
        this.istPruefer = istPruefer;
    }
    public Date getSperrDatZeitVon() {
        return sperrDatZeitVon;
    }

    public void setSperrDatZeitVon(Date sperrDatZeitVon) {
        this.sperrDatZeitVon = sperrDatZeitVon;
    }

    public Date getSperrDatZeitBis() {
        return sperrDatZeitBis;
    }

    public void setSperrDatZeitBis(Date sperrDatZeitBis) {
        this.sperrDatZeitBis = sperrDatZeitBis;
    }
    @XmlTransient
    public Collection<Sgmodul> getSgmodulCollection() {
        return sgmodulCollection;
    }

    public void setSgmodulCollection(Collection<Sgmodul> sgmodulCollection) {
        this.sgmodulCollection = sgmodulCollection;
    }    
    
    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Personen[ pID=" + pID + " ]";
    }
}

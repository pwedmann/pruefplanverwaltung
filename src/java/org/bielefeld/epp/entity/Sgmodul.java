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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Александр
 */
@Entity
@Table(name = "sgmodul")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgmodul.findAll", query = "SELECT s FROM Sgmodul s ORDER BY s.modID.modName"),
    @NamedQuery(name = "Sgmodul.findBySgmid", query = "SELECT s FROM Sgmodul s WHERE s.sgmid = :sgmid"),
    @NamedQuery(name = "Sgmodul.findByModSem", query = "SELECT s FROM Sgmodul s WHERE s.modSem = :modSem"),
    @NamedQuery(name = "Sgmodul.findByPruefLeistung", query = "SELECT s FROM Sgmodul s WHERE s.pruefLeistung = :pruefLeistung"),
    @NamedQuery(name = "Sgmodul.findByNichtGeprueft", query = "SELECT s FROM Sgmodul s WHERE s.nichtGeprueft = :nichtGeprueft"),
    @NamedQuery(name = "Sgmodul.findByModGrp", query = "SELECT s FROM Sgmodul s WHERE s.modGrp = :modGrp"),
    @NamedQuery(name = "Sgmodul.findByZeitStempel", query = "SELECT s FROM Sgmodul s WHERE s.zeitStempel = :zeitStempel"),
    @NamedQuery(name = "Sgmodul.findBySgid", query = "SELECT s FROM Sgmodul s WHERE s.sgid = :sgid"),
    @NamedQuery(name = "Sgmodul.findByModid", query = "SELECT s FROM Sgmodul s WHERE s.modID = :modid"),
    @NamedQuery(name = "Sgmodul.findBySgidAndSem", query = "SELECT s FROM Sgmodul s WHERE s.sgid = :sgid AND s.modSem = :sem"),
    @NamedQuery(name = "Sgmodul.findBySGMNotiz", query = "SELECT s FROM Sgmodul s WHERE s.sGMNotiz = :sGMNotiz")})
public class Sgmodul implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SGMID")
    private Integer sgmid;
    @Basic(optional = false)
    @Column(name = "ModSem")
    private int modSem;
    @Basic(optional = false)
    @Column(name = "PruefLeistung")
    private String pruefLeistung;
    @Column(name = "nichtGeprueft")
    private int nichtGeprueft;
    @Column(name = "ModGrp")
    private int modGrp;
    @Column(name = "zeitStempel")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zeitStempel = new Date();
    @Column(name = "SGMNotiz")
    private String sGMNotiz;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgmid")
    private Collection<Pruefplaneintrag> pruefplaneintragCollection;
    @JoinColumn(name = "PrID", referencedColumnName = "PrID")
    @ManyToOne(optional = false)
    private Pruefer prID;
    @JoinColumn(name = "PID", referencedColumnName = "PID")
    @ManyToOne(optional = false)
    private Personen pID;
    @JoinColumn(name = "SGID", referencedColumnName = "SGID")
    @ManyToOne(optional = false)
    private Studiengang sgid;
    @JoinColumn(name = "ModID", referencedColumnName = "ModID")
    @ManyToOne(optional = false)
    private Modul modID;

    public Sgmodul() {
    }

    public Sgmodul(Integer sgmid) {
        this.sgmid = sgmid;
    }

    public Sgmodul(Integer sgmid, int modSem, String pruefLeistung, int nichtGeprueft, int modGrp, Date zeitStempel, String sGMNotiz) {
        this.sgmid = sgmid;
        this.modSem = modSem;
        this.pruefLeistung = pruefLeistung;
        this.nichtGeprueft = nichtGeprueft;
        this.modGrp = modGrp;
        this.zeitStempel = zeitStempel;
        this.sGMNotiz = sGMNotiz;
    }

    public Integer getSgmid() {
        return sgmid;
    }

    public void setSgmid(Integer sgmid) {
        this.sgmid = sgmid;
    }

    public int getModSem() {
        return modSem;
    }

    public void setModSem(int modSem) {
        this.modSem = modSem;
    }

    public String getPruefLeistung() {
        return pruefLeistung;
    }

    public void setPruefLeistung(String pruefLeistung) {
        this.pruefLeistung = pruefLeistung;
    }

    public int getNichtGeprueft() {
        return nichtGeprueft;
    }

    public void setNichtGeprueft(int nichtGeprueft) {
        this.nichtGeprueft = nichtGeprueft;
    }

    public int getModGrp() {
        return modGrp;
    }

    public void setModGrp(int modGrp) {
        this.modGrp = modGrp;
    }

    public Date getZeitStempel() {
        return zeitStempel;
    }

    public void setZeitStempel(Date zeitStempel) {
        this.zeitStempel = zeitStempel;
    }

    public String getSGMNotiz() {
        return sGMNotiz;
    }

    public void setSGMNotiz(String sGMNotiz) {
        this.sGMNotiz = sGMNotiz;
    }

    @XmlTransient
    public Collection<Pruefplaneintrag> getPruefplaneintragCollection() {
        return pruefplaneintragCollection;
    }

    public void setPruefplaneintragCollection(Collection<Pruefplaneintrag> pruefplaneintragCollection) {
        this.pruefplaneintragCollection = pruefplaneintragCollection;
    }

    public Pruefer getPrID() {
        return prID;
    }

    public void setPrID(Pruefer prID) {
        this.prID = prID;
    }
    public Personen getPID() {
        return pID;
    }

    public void setPID(Personen pID) {
        this.pID = pID;
    }

    public Studiengang getSgid() {
        return sgid;
    }

    public void setSgid(Studiengang sgid) {
        this.sgid = sgid;
    }

    public Modul getModID() {
        return modID;
    }

    public void setModID(Modul modID) {
        this.modID = modID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgmid != null ? sgmid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgmodul)) {
            return false;
        }
        Sgmodul other = (Sgmodul) object;
        if ((this.sgmid == null && other.sgmid != null) || (this.sgmid != null && !this.sgmid.equals(other.sgmid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Sgmodul[ sgmid=" + sgmid + " ]";
    }
    
}

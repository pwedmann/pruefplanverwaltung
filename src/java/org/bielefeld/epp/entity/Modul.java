/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bielefeld.epp.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Александр
 */
@Entity
@Table(name = "modul")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modul.findAll", query = "SELECT m FROM Modul m"),
    @NamedQuery(name = "Modul.findByModID", query = "SELECT m FROM Modul m WHERE m.modID = :modID"),
    @NamedQuery(name = "Modul.findByModName", query = "SELECT m FROM Modul m WHERE m.modName = :modName"),
    @NamedQuery(name = "Modul.findByPcid", query = "SELECT m FROM Modul m WHERE m.pcid = :pcid"),
    @NamedQuery(name = "Modul.findByModKuerzel", query = "SELECT m FROM Modul m WHERE m.modKuerzel = :modKuerzel")})
public class Modul implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ModID")
    private Integer modID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ModName")
    private String modName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ModKuerzel")
    private String modKuerzel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modID")
    private Collection<Sgmodul> sgmodulCollection;
    @JoinColumn(name = "PCID", referencedColumnName = "PCID")
    @ManyToOne(optional = true, cascade = CascadeType.REFRESH)
    private Pruefcodes pcid;

    public Modul() {
    }

    public Modul(Integer modID) {
        this.modID = modID;
    }

    public Modul(Integer modID, String modName, String modKuerzel) {
        this.modID = modID;
        this.modName = modName;
        this.modKuerzel = modKuerzel;
    }

    public Integer getModID() {
        return modID;
    }

    public void setModID(Integer modID) {
        this.modID = modID;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public String getModKuerzel() {
        return modKuerzel;
    }

    public void setModKuerzel(String modKuerzel) {
        this.modKuerzel = modKuerzel;
    }

    @XmlTransient
    public Collection<Sgmodul> getSgmodulCollection() {
        return sgmodulCollection;
    }

    public void setSgmodulCollection(Collection<Sgmodul> sgmodulCollection) {
        this.sgmodulCollection = sgmodulCollection;
    }

    public Pruefcodes getPcid() {
        return pcid;
    }

    public void setPcid(Pruefcodes pcid) {
        this.pcid = pcid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modID != null ? modID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modul)) {
            return false;
        }
        Modul other = (Modul) object;
        if ((this.modID == null && other.modID != null) || (this.modID != null && !this.modID.equals(other.modID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Modul[ modID=" + modID + " ]";
    }
    
}

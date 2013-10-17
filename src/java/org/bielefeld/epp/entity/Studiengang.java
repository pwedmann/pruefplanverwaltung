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
@Table(name = "studiengang")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Studiengang.findAll", query = "SELECT s FROM Studiengang s"),
    @NamedQuery(name = "Studiengang.findBySgid", query = "SELECT s FROM Studiengang s WHERE s.sgid = :sgid"),
    @NamedQuery(name = "Studiengang.findBySGName", query = "SELECT s FROM Studiengang s WHERE s.sGName = :sGName"),
    @NamedQuery(name = "Studiengang.findBySGKurz", query = "SELECT s FROM Studiengang s WHERE s.sGKurz = :sGKurz")})
public class Studiengang implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SGID")
    private Short sgid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SGName")
    private String sGName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SGKurz")
    private String sGKurz;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgid")
    private Collection<Sgmodul> sgmodulCollection;

    public Studiengang() {
    }

    public Studiengang(Short sgid) {
        this.sgid = sgid;
    }

    public Studiengang(Short sgid, String sGName, String sGKurz) {
        this.sgid = sgid;
        this.sGName = sGName;
        this.sGKurz = sGKurz;
    }

    public Short getSgid() {
        return sgid;
    }

    public void setSgid(Short sgid) {
        this.sgid = sgid;
    }

    public String getSGName() {
        return sGName;
    }

    public void setSGName(String sGName) {
        this.sGName = sGName;
    }

    public String getSGKurz() {
        return sGKurz;
    }

    public void setSGKurz(String sGKurz) {
        this.sGKurz = sGKurz;
    }

    @XmlTransient
    public Collection<Sgmodul> getSgmodulCollection() {
        return sgmodulCollection;
    }

    public void setSgmodulCollection(Collection<Sgmodul> sgmodulCollection) {
        this.sgmodulCollection = sgmodulCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgid != null ? sgid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studiengang)) {
            return false;
        }
        Studiengang other = (Studiengang) object;
        if ((this.sgid == null && other.sgid != null) || (this.sgid != null && !this.sgid.equals(other.sgid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Studiengang[ sgid=" + sgid + " ]";
    }
    
}

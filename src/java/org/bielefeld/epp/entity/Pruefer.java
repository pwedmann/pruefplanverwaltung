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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Александр
 */
@Entity
@Table(name = "pruefer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruefer.findAll", query = "SELECT p FROM Pruefer p ORDER BY p.prName, p.prVorname"),
    @NamedQuery(name = "Pruefer.findByPrID", query = "SELECT p FROM Pruefer p WHERE p.prID = :prID"),
    @NamedQuery(name = "Pruefer.findByPrName", query = "SELECT p FROM Pruefer p WHERE p.prName = :prName"),
    @NamedQuery(name = "Pruefer.findByPrVorname", query = "SELECT p FROM Pruefer p WHERE p.prVorname = :prVorname"),
    @NamedQuery(name = "Pruefer.findByPrTitel", query = "SELECT p FROM Pruefer p WHERE p.prTitel = :prTitel"),
    @NamedQuery(name = "Pruefer.findByPrKurz", query = "SELECT p FROM Pruefer p WHERE p.prKurz = :prKurz")})
public class Pruefer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PrID")
    private Integer prID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrName")
    private String prName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrVorname")
    private String prVorname;
    @Column(name = "PrTitel")
    private String prTitel;
    @Column(name = "PrKurz")
    private String prKurz;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prID")
    private Collection<Sgmodul> sgmodulCollection;

    public Pruefer() {
    }

    public Pruefer(Integer prID) {
        this.prID = prID;
    }

    public Pruefer(Integer prID, String prName, String prVorname, String prTitel, String prKurz) {
        this.prID = prID;
        this.prName = prName;
        this.prVorname = prVorname;
        this.prTitel = prTitel;
        this.prKurz = prKurz;
    }

    public Integer getPrID() {
        return prID;
    }

    public void setPrID(Integer prID) {
        this.prID = prID;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getPrVorname() {
        return prVorname;
    }

    public void setPrVorname(String prVorname) {
        this.prVorname = prVorname;
    }

    public String getPrTitel() {
        return prTitel;
    }

    public void setPrTitel(String prTitel) {
        this.prTitel = prTitel;
    }

    public String getPrKurz() {
        return prKurz;
    }

    public void setPrKurz(String prKurz) {
        this.prKurz = prKurz;
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
        hash += (prID != null ? prID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pruefer)) {
            return false;
        }
        Pruefer other = (Pruefer) object;
        if ((this.prID == null && other.prID != null) || (this.prID != null && !this.prID.equals(other.prID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Pruefer[ prID=" + prID + " ]";
    }
    
}

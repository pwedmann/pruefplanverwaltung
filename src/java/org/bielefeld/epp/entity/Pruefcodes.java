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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author A. Epp
 */
@Entity
@Table(name = "pruefcodes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruefcodes.findAll", query = "SELECT p FROM Pruefcodes p ORDER BY p.prCode"),
    @NamedQuery(name = "Pruefcodes.findByPcid", query = "SELECT p FROM Pruefcodes p WHERE p.pcid = :pcid"),
    @NamedQuery(name = "Pruefcodes.findByPrCode", query = "SELECT p FROM Pruefcodes p WHERE p.prCode = :prCode"),
    @NamedQuery(name = "Pruefcodes.findByPrAbschnitt", query = "SELECT p FROM Pruefcodes p WHERE p.prAbschnitt = :prAbschnitt"),
    @NamedQuery(name = "Pruefcodes.findByPrPflicht", query = "SELECT p FROM Pruefcodes p WHERE p.prPflicht = :prPflicht"),
    @NamedQuery(name = "Pruefcodes.findByPrArt", query = "SELECT p FROM Pruefcodes p WHERE p.prArt = :prArt"),
    @NamedQuery(name = "Pruefcodes.findByPrForm", query = "SELECT p FROM Pruefcodes p WHERE p.prForm = :prForm")})
public class Pruefcodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCID")
    private Integer pcid;
    @Column(name = "PrCode")
    private int prCode;
    @Column(name = "PrAbschnitt")
    private char prAbschnitt;
    @Column(name = "PrPflicht")
    private String prPflicht;
    @Column(name = "PrArt")
    private char prArt;
    @Column(name = "PrForm")
    private String prForm;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pcid")
    private Collection<Modul> modulCollection;

    public Pruefcodes() {
    }

    public Pruefcodes(Integer pcid) {
        this.pcid = pcid;
    }

    public Pruefcodes(Integer pcid, int prCode, char prAbschnitt, String prPflicht, char prArt, String prForm) {
        this.pcid = pcid;
        this.prCode = prCode;
        this.prAbschnitt = prAbschnitt;
        this.prPflicht = prPflicht;
        this.prArt = prArt;
        this.prForm = prForm;
    }

    public Integer getPcid() {
        return pcid;
    }

    public void setPcid(Integer pcid) {
        this.pcid = pcid;
    }

    public int getPrCode() {
        return prCode;
    }

    public void setPrCode(int prCode) {
        this.prCode = prCode;
    }

    public char getPrAbschnitt() {
        return prAbschnitt;
    }

    public void setPrAbschnitt(char prAbschnitt) {
        this.prAbschnitt = prAbschnitt;
    }

    public String getPrPflicht() {
        return prPflicht;
    }

    public void setPrPflicht(String prPflicht) {
        this.prPflicht = prPflicht;
    }

    public char getPrArt() {
        return prArt;
    }

    public void setPrArt(char prArt) {
        this.prArt = prArt;
    }

    public String getPrForm() {
        return prForm;
    }

    public void setPrForm(String prForm) {
        this.prForm = prForm;
    }

    @XmlTransient
    public Collection<Modul> getModulCollection() {
        return modulCollection;
    }

    public void setModulCollection(Collection<Modul> modulCollection) {
        this.modulCollection = modulCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pcid != null ? pcid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pruefcodes)) {
            return false;
        }
        Pruefcodes other = (Pruefcodes) object;
        if ((this.pcid == null && other.pcid != null) || (this.pcid != null && !this.pcid.equals(other.pcid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Pruefcodes[ pcid=" + pcid + " ]";
    }
    
}

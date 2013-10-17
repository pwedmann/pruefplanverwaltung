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
@Table(name = "raum")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Raum.findAll", query = "SELECT r FROM Raum r"),
    @NamedQuery(name = "Raum.findByRid", query = "SELECT r FROM Raum r WHERE r.rid = :rid"),
    @NamedQuery(name = "Raum.findByRName", query = "SELECT r FROM Raum r WHERE r.rName = :rName"),
    @NamedQuery(name = "Raum.findByKapazitaet", query = "SELECT r FROM Raum r WHERE r.kapazitaet = :kapazitaet"),
    @NamedQuery(name = "Raum.findByNachbarRaum", query = "SELECT r FROM Raum r WHERE r.nachbarRaum = :nachbarRaum")})
public class Raum implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RID")
    private Integer rid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RName")
    private String rName;
    @Column(name = "Kapazitaet")
    private int kapazitaet;
    @Column(name = "NachbarRaum")
    private boolean nachbarRaum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rid")
    private Collection<Pruefplaneintrag> pruefplaneintragCollection;

    public Raum() {
    }

    public Raum(Integer rid) {
        this.rid = rid;
    }

    public Raum(Integer rid, String rName, int kapazitaet, boolean nachbarRaum) {
        this.rid = rid;
        this.rName = rName;
        this.kapazitaet = kapazitaet;
        this.nachbarRaum = nachbarRaum;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRName() {
        return rName;
    }

    public void setRName(String rName) {
        this.rName = rName;
    }

    public int getKapazitaet() {
        return kapazitaet;
    }

    public void setKapazitaet(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public boolean getNachbarRaum() {
        return nachbarRaum;
    }

    public void setNachbarRaum(boolean nachbarRaum) {
        this.nachbarRaum = nachbarRaum;
    }

    @XmlTransient
    public Collection<Pruefplaneintrag> getPruefplaneintragCollection() {
        return pruefplaneintragCollection;
    }

    public void setPruefplaneintragCollection(Collection<Pruefplaneintrag> pruefplaneintragCollection) {
        this.pruefplaneintragCollection = pruefplaneintragCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rid != null ? rid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Raum)) {
            return false;
        }
        Raum other = (Raum) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Raum[ rid=" + rid + " ]";
    }
    
}

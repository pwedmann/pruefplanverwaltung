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
@Table(name = "benutzergruppe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Benutzergruppe.findAll", query = "SELECT b FROM Benutzergruppe b"),
    @NamedQuery(name = "Benutzergruppe.findByGroupID", query = "SELECT b FROM Benutzergruppe b WHERE b.groupID = :groupID"),
    @NamedQuery(name = "Benutzergruppe.findByBGName", query = "SELECT b FROM Benutzergruppe b WHERE b.bGName = :bGName"),
    @NamedQuery(name = "Benutzergruppe.findByBGRechte", query = "SELECT b FROM Benutzergruppe b WHERE b.bGRechte = :bGRechte")})
public class Benutzergruppe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GroupID")
    private Short groupID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BGName")
    private String bGName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BGRechte")
    private short bGRechte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupID")
    private Collection<Accounts> accountsCollection;

    public Benutzergruppe() {
    }

    public Benutzergruppe(Short groupID) {
        this.groupID = groupID;
    }

    public Benutzergruppe(Short groupID, String bGName, short bGRechte) {
        this.groupID = groupID;
        this.bGName = bGName;
        this.bGRechte = bGRechte;
    }

    public Short getGroupID() {
        return groupID;
    }

    public void setGroupID(Short groupID) {
        this.groupID = groupID;
    }

    public String getBGName() {
        return bGName;
    }

    public void setBGName(String bGName) {
        this.bGName = bGName;
    }

    public short getBGRechte() {
        return bGRechte;
    }

    public void setBGRechte(short bGRechte) {
        this.bGRechte = bGRechte;
    }

    @XmlTransient
    public Collection<Accounts> getAccountsCollection() {
        return accountsCollection;
    }

    public void setAccountsCollection(Collection<Accounts> accountsCollection) {
        this.accountsCollection = accountsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupID != null ? groupID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Benutzergruppe)) {
            return false;
        }
        Benutzergruppe other = (Benutzergruppe) object;
        if ((this.groupID == null && other.groupID != null) || (this.groupID != null && !this.groupID.equals(other.groupID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Benutzergruppe[ groupID=" + groupID + " ]";
    }
    
}

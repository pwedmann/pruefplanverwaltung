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
@Table(name = "accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Accounts a"),
    @NamedQuery(name = "Accounts.checkLogin", query = "SELECT a FROM Accounts a WHERE a.accName = :accName AND a.accPwd = :accPwd"),
    @NamedQuery(name = "Accounts.findByAccID", query = "SELECT a FROM Accounts a WHERE a.accID = :accID"),
    @NamedQuery(name = "Accounts.findByAccName", query = "SELECT a FROM Accounts a WHERE a.accName = :accName"),
    @NamedQuery(name = "Accounts.findByAccPwd", query = "SELECT a FROM Accounts a WHERE a.accPwd = :accPwd")})
public class Accounts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AccID")
    private Integer accID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccName")
    private String accName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccPwd")
    private String accPwd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accID")
    private Collection<Mitarbeiter> mitarbeiterCollection;
    @JoinColumn(name = "GroupID", referencedColumnName = "GroupID")
    @ManyToOne(optional = false)
    private Benutzergruppe groupID;

    public Accounts() {
    }

    public Accounts(Integer accID) {
        this.accID = accID;
    }

    public Accounts(Integer accID, String accName, String accPwd) {
        this.accID = accID;
        this.accName = accName;
        this.accPwd = accPwd;
    }

    public Integer getAccID() {
        return accID;
    }

    public void setAccID(Integer accID) {
        this.accID = accID;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccPwd() {
        return accPwd;
    }

    public void setAccPwd(String accPwd) {
        this.accPwd = accPwd;
    }

    @XmlTransient
    public Collection<Mitarbeiter> getMitarbeiterCollection() {
        return mitarbeiterCollection;
    }

    public void setMitarbeiterCollection(Collection<Mitarbeiter> mitarbeiterCollection) {
        this.mitarbeiterCollection = mitarbeiterCollection;
    }

    public Benutzergruppe getGroupID() {
        return groupID;
    }

    public void setGroupID(Benutzergruppe groupID) {
        this.groupID = groupID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accID != null ? accID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.accID == null && other.accID != null) || (this.accID != null && !this.accID.equals(other.accID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccID: "+accID;
    }
    
}

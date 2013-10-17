/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bielefeld.epp.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Александр
 */
@Entity
@Table(name = "pruefungsform")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruefungsform.findAll", query = "SELECT p FROM Pruefungsform p"),
    @NamedQuery(name = "Pruefungsform.findByPfid", query = "SELECT p FROM Pruefungsform p WHERE p.pfid = :pfid"),
    @NamedQuery(name = "Pruefungsform.findByPForm", query = "SELECT p FROM Pruefungsform p WHERE p.pForm = :pForm")})
public class Pruefungsform implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PFID")
    private Short pfid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PForm")
    private String pForm;

    public Pruefungsform() {
    }

    public Pruefungsform(Short pfid) {
        this.pfid = pfid;
    }

    public Pruefungsform(Short pfid, String pForm) {
        this.pfid = pfid;
        this.pForm = pForm;
    }

    public Short getPfid() {
        return pfid;
    }

    public void setPfid(Short pfid) {
        this.pfid = pfid;
    }

    public String getPForm() {
        return pForm;
    }

    public void setPForm(String pForm) {
        this.pForm = pForm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pfid != null ? pfid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pruefungsform)) {
            return false;
        }
        Pruefungsform other = (Pruefungsform) object;
        if ((this.pfid == null && other.pfid != null) || (this.pfid != null && !this.pfid.equals(other.pfid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bielefeld.epp.entity.Pruefungsform[ pfid=" + pfid + " ]";
    }
    
}

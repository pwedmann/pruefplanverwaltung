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
 * @author Александр
 */
@Entity
@Table(name = "pruefperioden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruefperioden.findAll", query = "SELECT p FROM Pruefperioden p ORDER BY p.prPeID"),
    @NamedQuery(name = "Pruefperioden.findAllForWeek1", query = "SELECT p FROM Pruefperioden p WHERE p.pPKw < p.pPotherKw ORDER BY p.pPJahr, p.pPKw"),
    @NamedQuery(name = "Pruefperioden.findAllForWeek2", query = "SELECT p FROM Pruefperioden p WHERE p.pPKw > p.pPotherKw ORDER BY p.pPJahr, p.pPKw"),
    @NamedQuery(name = "Pruefperioden.findByPrPeID", query = "SELECT p FROM Pruefperioden p WHERE p.prPeID = :prPeID"),
    @NamedQuery(name = "Pruefperioden.findByPPKw", query = "SELECT p FROM Pruefperioden p WHERE p.pPKw = :pPKw"),
    @NamedQuery(name = "Pruefperioden.findByPPWoche", query = "SELECT p FROM Pruefperioden p WHERE p.pPWoche = :pPWoche"),
    @NamedQuery(name = "Pruefperioden.findByPruefSemester", query = "SELECT p FROM Pruefperioden p WHERE p.pruefSemester = :pruefSemester"),
    @NamedQuery(name = "Pruefperioden.findByPruefTermin", query = "SELECT p FROM Pruefperioden p WHERE p.pruefTermin = :pruefTermin"),
    @NamedQuery(name = "Pruefperioden.findByPPJahr", query = "SELECT p FROM Pruefperioden p WHERE p.pPJahr = :pPJahr"),
    @NamedQuery(name = "Pruefperioden.findByWeeksAndYear", query = "SELECT p FROM Pruefperioden p WHERE p.pPotherKw = :pPotherKw AND p.pPKw = :pPKw AND p.pPJahr = :pPJahr"),
    @NamedQuery(name = "Pruefperioden.findByPPotherKw", query = "SELECT p FROM Pruefperioden p WHERE p.pPotherKw = :pPotherKw")})
public class Pruefperioden implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PrPeID")
    private Integer prPeID;
    @Column(name = "PPKw")
    private int pPKw;
    @Column(name = "PPWoche")
    private int pPWoche;
    @Column(name = "PruefSemester")
    private String pruefSemester;
    @Column(name = "PruefTermin")
    private String pruefTermin;
    @Column(name = "PPJahr")
    private int pPJahr;
    @Column(name = "PPotherKw")
    private int pPotherKw;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pruefPeriode")
    private Collection<Pruefplaneintrag> pruefplaneintragCollection;
    
    public Pruefperioden() {
    }

    public Pruefperioden(Integer prPeID, int pPKw, int pPWoche, String pruefSemester, String pruefTermin, int pPJahr, int pPotherKw) {
        this.prPeID = prPeID;
        this.pPKw = pPKw;
        this.pPWoche = pPWoche;
        this.pruefSemester = pruefSemester;
        this.pruefTermin = pruefTermin;
        this.pPJahr = pPJahr;
        this.pPotherKw = pPotherKw;
    }
    
    public Integer getPrPeID() {
        return prPeID;
    }

    public void setPrPeID(Integer prPeID) {
        this.prPeID = prPeID;
    }

    public int getPPKw() {
        return pPKw;
    }

    public void setPPKw(int pPKw) {
        this.pPKw = pPKw;
    }

    public int getPPWoche() {
        return pPWoche;
    }

    public void setPPWoche(int pPWoche) {
        this.pPWoche = pPWoche;
    }

    public String getPruefSemester() {
        return pruefSemester;
    }

    public void setPruefSemester(String pruefSemester) {
        this.pruefSemester = pruefSemester;
    }

    public String getPruefTermin() {
        return pruefTermin;
    }

    public void setPruefTermin(String pruefTermin) {
        this.pruefTermin = pruefTermin;
    }

    public int getPPJahr() {
        return pPJahr;
    }

    public void setPPJahr(int pPJahr) {
        this.pPJahr = pPJahr;
    }

    public int getPPotherKw() {
        return pPotherKw;
    }

    public void setPPotherKw(int pPotherKw) {
        this.pPotherKw = pPotherKw;
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
        return prPeID.toString() != null ? this.getClass().hashCode() + prPeID.toString().hashCode() : super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Pruefperioden && (prPeID.toString() != null) ? prPeID.toString().equals(((Pruefperioden) object).prPeID.toString()) : (object == this);
    }

    @Override
    public String toString() {
        return prPeID.toString();
    }
    
}

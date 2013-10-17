package org.bielefeld.epp.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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

@Entity
@Table(name = "mitarbeiter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mitarbeiter.SelectMitarbeiter", query = "SELECT m FROM Mitarbeiter m"),
    @NamedQuery(name = "Mitarbeiter.findByMaID", query = "SELECT m FROM Mitarbeiter m WHERE m.maID = :maID"),
    @NamedQuery(name = "Mitarbeiter.findByMaName", query = "SELECT m FROM Mitarbeiter m WHERE m.maName = :maName"),
    @NamedQuery(name = "Mitarbeiter.findByMaVorname", query = "SELECT m FROM Mitarbeiter m WHERE m.maVorname = :maVorname"),
    @NamedQuery(name = "Mitarbeiter.findByMaTitel", query = "SELECT m FROM Mitarbeiter m WHERE m.maTitel = :maTitel"),
    @NamedQuery(name = "Mitarbeiter.findByMaKurz", query = "SELECT m FROM Mitarbeiter m WHERE m.maKurz = :maKurz")})
public class Mitarbeiter implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaID")
    private Integer maID;
    @Column(name = "MaName")
    private String maName;
    @Column(name = "MaVorname")
    private String maVorname;
    @Column(name = "MaTitel")
    private String maTitel;
    @Column(name = "MaKurz")
    private String maKurz;
    @Column(name = "SperrDatZeitVon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sperrDatZeitVon = new Date();
    @Column(name = "SperrDatZeitBis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sperrDatZeitBis = new Date();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maID")
    private Collection<Pruefplaneintrag> pruefplaneintragCollection;
    @JoinColumn(name = "AccID", referencedColumnName = "AccID")
    @ManyToOne(optional = true)
    private Accounts accID;

    public Mitarbeiter() {
    }

    public Mitarbeiter(Integer maID) {
        this.maID = maID;
    }

    public Mitarbeiter(Integer maID, String maName, String maVorname, String maTitel, String maKurz, Date sperrDatZeitVon, Date sperrDatZeitBis) {
        this.maID = maID;
        this.maName = maName;
        this.maVorname = maVorname;
        this.maTitel = maTitel;
        this.maKurz = maKurz;
        this.sperrDatZeitVon = sperrDatZeitVon;
        this.sperrDatZeitBis = sperrDatZeitBis;
    }
    
    public Integer getMaID() {
        return maID;
    }

    public void setMaID(Integer maID) {
        this.maID = maID;
    }

    public String getMaName() {
        return maName;
    }

    public void setMaName(String maName) {
        this.maName = maName;
    }

    public String getMaVorname() {
        return maVorname;
    }

    public void setMaVorname(String maVorname) {
        this.maVorname = maVorname;
    }

    public String getMaTitel() {
        return maTitel;
    }

    public void setMaTitel(String maTitel) {
        this.maTitel = maTitel;
    }

    public String getMaKurz() {
        return maKurz;
    }

    public void setMaKurz(String maKurz) {
        this.maKurz = maKurz;
    }

    public Date getSperrDatZeitVon() {
        return sperrDatZeitVon;
    }

    public void setSperrDatZeitVon(Date sperrDatZeitVon) {
        this.sperrDatZeitVon = sperrDatZeitVon;
    }

    public Date getSperrDatZeitBis() {
        return sperrDatZeitBis;
    }

    public void setSperrDatZeitBis(Date sperrDatZeitBis) {
        this.sperrDatZeitBis = sperrDatZeitBis;
    }

    @XmlTransient
    public Collection<Pruefplaneintrag> getPruefplaneintragCollection() {
        return pruefplaneintragCollection;
    }

    public void setPruefplaneintragCollection(Collection<Pruefplaneintrag> pruefplaneintragCollection) {
        this.pruefplaneintragCollection = pruefplaneintragCollection;
    }

    public Accounts getAccID() {
        if (accID == null) {
            return new Accounts();
        }
        return accID;
    }

    public void setAccID(Accounts accID) {
        this.accID = accID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maID != null ? maID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Mitarbeiter)) {
            return false;
        }
        Mitarbeiter other = (Mitarbeiter) object;
        if ((this.maID == null && other.maID != null) || (this.maID != null && !this.maID.equals(other.maID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccID "+this.getAccID().getAccID()+": "+maName+", "+maVorname;
    }
    
}

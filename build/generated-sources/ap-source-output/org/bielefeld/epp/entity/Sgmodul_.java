package org.bielefeld.epp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Modul;
import org.bielefeld.epp.entity.Personen;
import org.bielefeld.epp.entity.Pruefer;
import org.bielefeld.epp.entity.Pruefplaneintrag;
import org.bielefeld.epp.entity.Studiengang;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Sgmodul.class)
public class Sgmodul_ { 

    public static volatile SingularAttribute<Sgmodul, Integer> modSem;
    public static volatile SingularAttribute<Sgmodul, Pruefer> prID;
    public static volatile SingularAttribute<Sgmodul, Date> zeitStempel;
    public static volatile SingularAttribute<Sgmodul, Personen> pID;
    public static volatile SingularAttribute<Sgmodul, Modul> modID;
    public static volatile SingularAttribute<Sgmodul, Integer> sgmid;
    public static volatile SingularAttribute<Sgmodul, String> sGMNotiz;
    public static volatile CollectionAttribute<Sgmodul, Pruefplaneintrag> pruefplaneintragCollection;
    public static volatile SingularAttribute<Sgmodul, Studiengang> sgid;
    public static volatile SingularAttribute<Sgmodul, String> pruefLeistung;
    public static volatile SingularAttribute<Sgmodul, Integer> nichtGeprueft;
    public static volatile SingularAttribute<Sgmodul, Integer> modGrp;

}
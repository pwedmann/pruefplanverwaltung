package org.bielefeld.epp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Accounts;
import org.bielefeld.epp.entity.Pruefplaneintrag;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Mitarbeiter.class)
public class Mitarbeiter_ { 

    public static volatile SingularAttribute<Mitarbeiter, Integer> maID;
    public static volatile SingularAttribute<Mitarbeiter, String> maKurz;
    public static volatile SingularAttribute<Mitarbeiter, String> maVorname;
    public static volatile SingularAttribute<Mitarbeiter, String> maName;
    public static volatile CollectionAttribute<Mitarbeiter, Pruefplaneintrag> pruefplaneintragCollection;
    public static volatile SingularAttribute<Mitarbeiter, String> maTitel;
    public static volatile SingularAttribute<Mitarbeiter, Date> sperrDatZeitVon;
    public static volatile SingularAttribute<Mitarbeiter, Date> sperrDatZeitBis;
    public static volatile SingularAttribute<Mitarbeiter, Accounts> accID;

}
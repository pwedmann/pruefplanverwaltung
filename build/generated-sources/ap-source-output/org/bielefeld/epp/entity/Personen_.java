package org.bielefeld.epp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Sgmodul;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Personen.class)
public class Personen_ { 

    public static volatile SingularAttribute<Personen, String> pVorname;
    public static volatile SingularAttribute<Personen, String> pKurz;
    public static volatile SingularAttribute<Personen, Integer> pID;
    public static volatile SingularAttribute<Personen, String> pName;
    public static volatile SingularAttribute<Personen, String> istPruefer;
    public static volatile CollectionAttribute<Personen, Sgmodul> sgmodulCollection;
    public static volatile SingularAttribute<Personen, Date> sperrDatZeitVon;
    public static volatile SingularAttribute<Personen, Date> sperrDatZeitBis;
    public static volatile SingularAttribute<Personen, String> pTitel;

}
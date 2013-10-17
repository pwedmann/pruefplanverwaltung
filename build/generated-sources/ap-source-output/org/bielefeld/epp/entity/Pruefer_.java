package org.bielefeld.epp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Sgmodul;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Pruefer.class)
public class Pruefer_ { 

    public static volatile SingularAttribute<Pruefer, Integer> prID;
    public static volatile CollectionAttribute<Pruefer, Sgmodul> sgmodulCollection;
    public static volatile SingularAttribute<Pruefer, String> prKurz;
    public static volatile SingularAttribute<Pruefer, String> prName;
    public static volatile SingularAttribute<Pruefer, String> prVorname;
    public static volatile SingularAttribute<Pruefer, String> prTitel;

}
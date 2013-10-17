package org.bielefeld.epp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Pruefcodes;
import org.bielefeld.epp.entity.Sgmodul;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Modul.class)
public class Modul_ { 

    public static volatile SingularAttribute<Modul, Integer> modID;
    public static volatile CollectionAttribute<Modul, Sgmodul> sgmodulCollection;
    public static volatile SingularAttribute<Modul, Pruefcodes> pcid;
    public static volatile SingularAttribute<Modul, String> modKuerzel;
    public static volatile SingularAttribute<Modul, String> modName;

}
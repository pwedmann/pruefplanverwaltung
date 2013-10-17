package org.bielefeld.epp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Pruefplaneintrag;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Raum.class)
public class Raum_ { 

    public static volatile SingularAttribute<Raum, String> rName;
    public static volatile SingularAttribute<Raum, Integer> kapazitaet;
    public static volatile SingularAttribute<Raum, Integer> rid;
    public static volatile CollectionAttribute<Raum, Pruefplaneintrag> pruefplaneintragCollection;
    public static volatile SingularAttribute<Raum, Boolean> nachbarRaum;

}
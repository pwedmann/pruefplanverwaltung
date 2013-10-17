package org.bielefeld.epp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Pruefplaneintrag;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Pruefperioden.class)
public class Pruefperioden_ { 

    public static volatile SingularAttribute<Pruefperioden, String> pruefSemester;
    public static volatile SingularAttribute<Pruefperioden, Integer> pPJahr;
    public static volatile SingularAttribute<Pruefperioden, Integer> pPKw;
    public static volatile SingularAttribute<Pruefperioden, String> pruefTermin;
    public static volatile CollectionAttribute<Pruefperioden, Pruefplaneintrag> pruefplaneintragCollection;
    public static volatile SingularAttribute<Pruefperioden, Integer> pPWoche;
    public static volatile SingularAttribute<Pruefperioden, Integer> pPotherKw;
    public static volatile SingularAttribute<Pruefperioden, Integer> prPeID;

}
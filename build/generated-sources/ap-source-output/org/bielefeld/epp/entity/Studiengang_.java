package org.bielefeld.epp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Sgmodul;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Studiengang.class)
public class Studiengang_ { 

    public static volatile SingularAttribute<Studiengang, String> sGKurz;
    public static volatile CollectionAttribute<Studiengang, Sgmodul> sgmodulCollection;
    public static volatile SingularAttribute<Studiengang, Short> sgid;
    public static volatile SingularAttribute<Studiengang, String> sGName;

}
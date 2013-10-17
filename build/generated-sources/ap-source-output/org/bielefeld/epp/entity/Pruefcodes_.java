package org.bielefeld.epp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Modul;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Pruefcodes.class)
public class Pruefcodes_ { 

    public static volatile SingularAttribute<Pruefcodes, Character> prAbschnitt;
    public static volatile SingularAttribute<Pruefcodes, Integer> prCode;
    public static volatile CollectionAttribute<Pruefcodes, Modul> modulCollection;
    public static volatile SingularAttribute<Pruefcodes, Integer> pcid;
    public static volatile SingularAttribute<Pruefcodes, String> prForm;
    public static volatile SingularAttribute<Pruefcodes, String> prPflicht;
    public static volatile SingularAttribute<Pruefcodes, Character> prArt;

}
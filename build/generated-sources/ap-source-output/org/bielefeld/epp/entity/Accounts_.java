package org.bielefeld.epp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Benutzergruppe;
import org.bielefeld.epp.entity.Mitarbeiter;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Accounts.class)
public class Accounts_ { 

    public static volatile SingularAttribute<Accounts, Benutzergruppe> groupID;
    public static volatile SingularAttribute<Accounts, String> accName;
    public static volatile SingularAttribute<Accounts, String> accPwd;
    public static volatile CollectionAttribute<Accounts, Mitarbeiter> mitarbeiterCollection;
    public static volatile SingularAttribute<Accounts, Integer> accID;

}
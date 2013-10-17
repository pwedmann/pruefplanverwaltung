package org.bielefeld.epp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Accounts;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Benutzergruppe.class)
public class Benutzergruppe_ { 

    public static volatile SingularAttribute<Benutzergruppe, Short> groupID;
    public static volatile CollectionAttribute<Benutzergruppe, Accounts> accountsCollection;
    public static volatile SingularAttribute<Benutzergruppe, String> bGName;
    public static volatile SingularAttribute<Benutzergruppe, Short> bGRechte;

}
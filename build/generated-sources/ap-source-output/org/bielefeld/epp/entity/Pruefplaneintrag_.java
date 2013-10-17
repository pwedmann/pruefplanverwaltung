package org.bielefeld.epp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.bielefeld.epp.entity.Mitarbeiter;
import org.bielefeld.epp.entity.Pruefer;
import org.bielefeld.epp.entity.Pruefperioden;
import org.bielefeld.epp.entity.Pruefplaneintrag;
import org.bielefeld.epp.entity.Pruefungsform;
import org.bielefeld.epp.entity.Raum;
import org.bielefeld.epp.entity.Sgmodul;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-10-17T22:04:12")
@StaticMetamodel(Pruefplaneintrag.class)
public class Pruefplaneintrag_ { 

    public static volatile SingularAttribute<Pruefplaneintrag, Pruefer> erstPruefID;
    public static volatile CollectionAttribute<Pruefplaneintrag, Pruefplaneintrag> existingConflicts;
    public static volatile SingularAttribute<Pruefplaneintrag, Integer> ppid;
    public static volatile SingularAttribute<Pruefplaneintrag, Date> pPEDatZeit;
    public static volatile SingularAttribute<Pruefplaneintrag, Integer> status;
    public static volatile SingularAttribute<Pruefplaneintrag, Sgmodul> sgmid;
    public static volatile SingularAttribute<Pruefplaneintrag, Mitarbeiter> maID;
    public static volatile SingularAttribute<Pruefplaneintrag, Integer> anmeldeZahl;
    public static volatile SingularAttribute<Pruefplaneintrag, Pruefer> zweitPruefID;
    public static volatile SingularAttribute<Pruefplaneintrag, Integer> pPTermin;
    public static volatile SingularAttribute<Pruefplaneintrag, Date> zeitStempel;
    public static volatile SingularAttribute<Pruefplaneintrag, Raum> rid;
    public static volatile SingularAttribute<Pruefplaneintrag, Pruefungsform> pfid;
    public static volatile SingularAttribute<Pruefplaneintrag, Pruefperioden> pruefPeriode;
    public static volatile SingularAttribute<Pruefplaneintrag, Integer> anzMdlPr;
    public static volatile CollectionAttribute<Pruefplaneintrag, Pruefplaneintrag> pruefplaneintragCollection1;

}
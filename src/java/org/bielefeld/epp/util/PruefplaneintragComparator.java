package org.bielefeld.epp.util;

import java.util.Comparator;
import org.bielefeld.epp.entity.Pruefplaneintrag;

public class PruefplaneintragComparator  implements Comparator<Pruefplaneintrag> {
    @Override
    public int compare(Pruefplaneintrag ppe1, Pruefplaneintrag ppe2) {
        return ppe1.getPPEDatZeit().compareTo(ppe2.getPPEDatZeit());
    }
}
package org.bielefeld.epp.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.bielefeld.epp.entity.Pruefperioden;
import org.bielefeld.epp.managedBean.PruefperiodenHandler;
import org.bielefeld.epp.util.JPAUtil;

@FacesConverter(forClass=org.bielefeld.epp.entity.Pruefperioden.class)
public class PruefperiodenConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                PruefperiodenHandler pph = new PruefperiodenHandler(JPAUtil.getEM());
                return pph.getPruefperiodenByPrPeID(Integer.valueOf(value));
            } catch (Exception ex) {
                Logger.getLogger(PruefperiodenConverter.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Pruefperioden) value).getPrPeID());
        }
        
    }
}

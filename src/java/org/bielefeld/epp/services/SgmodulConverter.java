package org.bielefeld.epp.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.bielefeld.epp.entity.Sgmodul;
import org.bielefeld.epp.managedBean.SgmodulHandler;
import org.bielefeld.epp.util.JPAUtil;

@FacesConverter(forClass=org.bielefeld.epp.entity.Sgmodul.class)
public class SgmodulConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                SgmodulHandler sgmh = new SgmodulHandler(JPAUtil.getEM());
                return sgmh.getSgmodulBySgmid(Integer.valueOf(value));
            } catch (Exception ex) {
                Logger.getLogger(SgmodulConverter.class.getName())
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
            return String.valueOf(((Sgmodul) value).getSgmid());
        }
    }
}

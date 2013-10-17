package org.bielefeld.epp.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.bielefeld.epp.entity.Studiengang;
import org.bielefeld.epp.managedBean.StudiengangHandler;
import org.bielefeld.epp.util.JPAUtil;

@FacesConverter(forClass=org.bielefeld.epp.entity.Studiengang.class)
public class StudiengangConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                StudiengangHandler sgh = new StudiengangHandler(JPAUtil.getEM());
                return sgh.getStudiengangBySgid(Integer.valueOf(value));
            } catch (Exception ex) {
                Logger.getLogger(StudiengangConverter.class.getName())
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
            return String.valueOf(((Studiengang) value).getSgid());
        }
    }
}

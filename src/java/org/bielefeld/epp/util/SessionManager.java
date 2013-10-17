package org.bielefeld.epp.util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.bielefeld.epp.entity.Accounts;
import org.bielefeld.epp.entity.Benutzergruppe;

@SessionScoped
@ManagedBean(name="sessionManager")
public class SessionManager implements Serializable{
    
    private static final Logger logger = Logger.getLogger(SessionManager.class.getCanonicalName());
    
    private String accName;
    private String accPwd;
    private Accounts currentUser;
    private Benutzergruppe currentGroup;
    
    private String navi;
    private String content;
    
    @PersistenceContext
    private EntityManager em;
    
    public SessionManager() {}
/*---------------------------Login/Logout-------------------------------------*/    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        logger.log(Level.INFO, "Start login");
        try{
            currentUser = (Accounts)em.createNamedQuery("Accounts.checkLogin").
                            setParameter("accName", accName).
                            setParameter("accPwd", accPwd).
                            getSingleResult();
            currentGroup = currentUser.getGroupID();
            if(currentGroup.getGroupID()==1){
                navi = "../app/navi_admin.xhtml";
                content = "app/overview_admin.xhtml";
                logger.log(Level.INFO, "login as admin");
                return "app/overview_admin.xhtml?faces-redirect=true";
            } else{
                navi = "../app/navi_standard.xhtml";
                content = "app/overview_standard.xhtml";
                logger.log(Level.INFO, "login as user");
                return "app/overview_standard.xhtml?faces-redirect=true";
            }
        } catch (Exception ex) {
            context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Account nicht gefunden", "")); 
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
    }   
    public String logout() {
        logger.log(Level.INFO, "Start logout");
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO,"Aufwiedersehen!", 
                "Bis zum nächsten Mal!"));  
    	return "/login.xhtml?faces-redirect=true";
    }
    
    public boolean isLoggedIn(){
        return currentUser != null;
    }
/*----------------------------------------------------------------------------*/    
    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccPwd() {
        return accPwd;
    }

    public void setAccPwd(String accPwd) {
        this.accPwd = accPwd;
    }

    public Accounts getAccount() {
        return currentUser;
    }

    public String getNavi() {
        return navi;
    }

    public String getContent() {
        return content;
    }
    
}

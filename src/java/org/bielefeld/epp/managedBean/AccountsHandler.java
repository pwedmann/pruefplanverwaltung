package org.bielefeld.epp.managedBean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.bielefeld.epp.entity.Accounts;
import org.bielefeld.epp.entity.Benutzergruppe;

@ManagedBean
@ViewScoped
public class AccountsHandler implements Serializable {
    private static final Logger logger = 
            Logger.getLogger("org.bielefeld.epp.pruefplanverwaltung");
    private List<Accounts> selectedAccounts;
    private List<Accounts> filteredAccounts;
    private List<Accounts> allAccounts;
    
    private Accounts currentAccounts;
    
    private int currentAccId;
    private String currentAccName;
    private String currentPassword;
    private String currentPasswordConfirm;
    private Benutzergruppe currentBenutzergruppe;
    
    private String dialogHeader;

    @PostConstruct
    private void init(){
        allAccounts = this.getAccounts();
    }
    
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    
    public AccountsHandler(){}

    public AccountsHandler(EntityManager em){
        this.em = em;    
    }    

    public int getCurrentAccId() {
        return currentAccId;
    }

    public void setCurrentAccId(int currentAccId) {
        this.currentAccId = currentAccId;
    }

    public String getCurrentAccName() {
        return currentAccName;
    }

    public void setCurrentAccName(String currentAccName) {
        this.currentAccName = currentAccName;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getCurrentPasswordConfirm() {
        return currentPasswordConfirm;
    }

    public void setCurrentPasswordConfirm(String currentPasswordConfirm) {
        this.currentPasswordConfirm = currentPasswordConfirm;
    }

    public Benutzergruppe getCurrentBenutzergruppe() {
        return currentBenutzergruppe;
    }

    public void setCurrentBenutzergruppe(Benutzergruppe currentBenutzergruppe) {
        this.currentBenutzergruppe = currentBenutzergruppe;
    }

    public Accounts getCurrentAccounts() {
        return currentAccounts;
    }

    public void setCurrentAccounts(Accounts currentAccounts) {
        this.currentAccounts = currentAccounts;
    }

    public List<Accounts> getAllAccounts() {
        return allAccounts;
    }

    public void setAllAccounts(List<Accounts> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public List<Accounts> getSelectedAccounts() {
        return selectedAccounts;
    }

    public void setSelectedAccounts(List<Accounts> selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }

    public List<Accounts> getFilteredAccounts() {
        return filteredAccounts;
    }

    public void setFilteredAccounts(List<Accounts> filteredAccounts) {
        this.filteredAccounts = filteredAccounts;
    }
    
     public List<Accounts> getAccounts() {
        List<Accounts> la;
        try {
            logger.log(Level.INFO, "Start getAccounts");
            la = em.createNamedQuery("Accounts.findAll").
                getResultList();
            return la;
        }
        catch (Exception ex){
            Logger.getLogger(AccountsHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
            return null;
        }
    }  

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }
     
    public void deleteAccounts() {
        logger.log(Level.INFO, "Start deleteAccounts, selected: {0}", selectedAccounts.size());
	try{
            utx.begin();
            for(Accounts accounts : selectedAccounts) { 
                em.remove(em.merge(accounts));
            }             
            utx.commit(); 
            this.init();
            logger.log(Level.INFO, "Success at deleteAccounts, deleted: {0}", selectedAccounts.size());
        } catch (Exception ex) {
            Logger.getLogger(AccountsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }    
    
    public void insertOrUpdateAccounts(){
        logger.log(Level.INFO, "Start insertOrUpdateAccounts");
        Accounts a;
        try {
            utx.begin(); 
            if(currentAccounts == null){
                a = new Accounts();
                a.setAccName(currentAccName);
                if(currentPassword != null && !currentPassword.equals("")){
                    a.setAccPwd(currentPassword);
                } else {
                   a.setAccPwd("123"); 
                }                
                a.setGroupID(currentBenutzergruppe);
                em.persist(a);                 
            } else {
                a = em.find(Accounts.class, currentAccounts.getAccID());
                a.setAccName(currentAccName);
                if(currentPassword != null && !currentPassword.equals("")){
                    a.setAccPwd(currentPassword);
                }
                a.setGroupID(currentBenutzergruppe);
                em.merge(a);                
            }
            utx.commit(); 
            this.init();
            logger.log(Level.INFO, "Success at insertOrUpdateAccounts");
        } catch (Exception ex) {
            Logger.getLogger(AccountsHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }   
    
    public void editAccounts(){
        dialogHeader = "Bearbeiten";
        currentAccounts = selectedAccounts.get(0);
        currentAccId = currentAccounts.getAccID();    
        currentAccName = currentAccounts.getAccName();
        currentPassword = currentAccounts.getAccPwd();
        currentBenutzergruppe = currentAccounts.getGroupID();
    }
    
    public void newAccounts(){
        dialogHeader = "Neu";
        currentAccounts = null;
        currentAccName = null;
        currentAccId = 0;
        currentPassword = null;
        currentPasswordConfirm = null;
        currentBenutzergruppe = null;
    }   
}

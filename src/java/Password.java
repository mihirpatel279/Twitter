/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Mihir
 */
@Named(value = "password")
@RequestScoped
public class Password {

    /**
     * Creates a new instance of Password
     */
    private String curr_p;
    private String new_p;
    private String loginId =  Login.userid;
     private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
     
     
     
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    
    
    public String getCurr_p() {
        return curr_p;
    }

    public void setCurr_p(String curr_p) {
        this.curr_p = curr_p;
    }

    public String getNew_p() {
        return new_p;
    }

    public void setNew_p(String new_p) {
        this.new_p = new_p;
    }
    
    
    public String Update()
    {
        if(curr_p.equals(new_p))
        {
        
            curr_p = "";
            new_p = "";
            this.message = "Current and new Password Can not be same ";
           return "PasswordUpd";
        }
        else 
        {
            
        
        String sql2 = "select * from t_user_details where user_loginId='" + loginId + "' and user_password = '" + curr_p + "'";
                    if(Database.IsThereAnyRecords(sql2))
                    { 
                       
                    String sqlQuery = "Update t_user_details set user_password = '" + new_p + "' where user_loginId = '" + loginId + "'";
     
                            if(Database.insertUpdateDelete(sqlQuery))    
                            {
                            curr_p = "";
                            new_p = "";
                            this.message = "Passowrd is Changed Succesfully";
                            return "PasswordUpd";
                        
                            }
                            else{
                                
                                
                                 this.message = "Internal Error.Please try again";
                                 return "Error";
                                
                            }
                    
                    }
                    else 
                    {
                        curr_p = "";
                        new_p = "";
                        this.message = "Passowrd is incorrect";
                        return "PasswordUpd";
                    }
        
        
        }
    
    }

    
}

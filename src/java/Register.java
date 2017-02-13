/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Mihir
 */
@Named(value = "register")
@SessionScoped
public class Register implements Serializable {

    
    private String loginId;
    private String name;
    private String password;
    private String security_Q_1;
    private String answer1;
    private String profilePic;
    private String coverPic;
    private String doB;
    private String aboutMe;
    private String location;
    private String error;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDoB() {
        return doB;
    }

    public void setDoB(String doB) {
        this.doB = doB;
    }
    

    /**
     * Creates a new instance of Register
     */
    public Register() {
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurity_Q_1() {
        return security_Q_1;
    }

    public void setSecurity_Q_1(String security_Q_1) {
        this.security_Q_1 = security_Q_1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

   

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public String RegisterMethod()
        
            
    {
       
        
        this.error = "";
       if(!loginId.equals(""))
       {
           
          String sql = "select * from t_user_details where user_loginId=" + loginId;
                if(!Database.IsThereAnyRecords(sql))
                {                     
                      if(!password.equals(""))
                      {
                          
                        if(!answer1.equals(""))
                        {
                             if(!doB.equals(""))
                            {
                              
                                
                               
                                String sqlQuery = "INSERT INTO t_user_details (user_loginId,user_name,user_password,user_sec_que,user_sec_ans,user_profile_pic,user_cover_pic,user_DoB,user_about_me,user_location) VALUES ('" + loginId + "','" + name + "','" + password + "','" + security_Q_1 + "','" + answer1 + "','" + profilePic + "','" + coverPic + "','" + doB + "','" + aboutMe + "','" + location +"')";
                                if(Database.insertUpdateDelete(sqlQuery))
                                {
                                  Login.userid = loginId;
                                  return "Welcome";  
                                }
                                
                                else
                                    {
                                     this.error = "Internal Error.Please try again";
                                        return "Register";
                                }
                            }
                            else 
                            {
                            this.error = "Please Enter DOB";
                            }
                        }
                        else 
                        {
                           this.error = "Please Write Answer";
                        }
                          
                      }
                      else 
                      {
                           this.error = "Password Can not be Blank";
                      }
                }               
                else
                {
                    this.error = "User Already Exists"; 
                } 
       }
       else
       {
          this.error = "Login ID Can not be Blank";
       }
        
        return "Register";
    }
    
    
}

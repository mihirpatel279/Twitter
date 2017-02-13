/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mihir
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    /**
     * Creates a new instance of Login
     */
    public Login() {
    }
    
    
    public static String userid;
    private  String loginId;
    private String password;
    private String error;
    private int followerCount;
    private int followingCount;

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    

    


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public String loginMethod()
    {
        userid = loginId;
         String sql1 = "select * from t_user_details where user_loginId='" + loginId + "'";
          if(Database.IsThereAnyRecords(sql1))
                { 
                String sql2 = "select * from t_user_details where user_loginId='" + loginId + "' and user_password = '" + password + "'";
                    if(Database.IsThereAnyRecords(sql2))
                    { 
                        getCount();
                        return "Welcome";
                    }
                    else 
                    {
                        this.error = "Password is wrong ";
                        return "index";
                    }          
                }
          else
          {
             this.error = "User ID not valid ";
              return "index";
          }
    }
     
    /** gets no of followers and following **/ 
    public void getCount()
     {
        String getFollowersCount = "select count(*) from t_followers where user_loginId = '"+loginId+"'";
        String getFollowingCount = "select count(*) from t_followings where user_loginId = '"+loginId+"'";
        
        Statement st1 = Database.getStatement();
        Statement st2 = Database.getStatement();
         
        ResultSet rs1 = null;
        ResultSet rs2 = null;
         
        try {
            rs1 = st1.executeQuery(getFollowersCount);
            rs2 = st2.executeQuery(getFollowingCount);
            
            while(rs1.next())
            {
                followerCount = rs1.getInt(1);
            }
            while(rs2.next())
            {
            followingCount = rs2.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
         finally
        {
              try {
                  rs1.close();
                  
            rs2.close();
            st1.close();
            st2.close();
              } catch (SQLException ex) {
                  Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
              }
            
        }
    }
    
    public String signOut()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";
    }
}

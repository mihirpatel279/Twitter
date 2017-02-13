/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
@Named(value = "follow")
@SessionScoped
public class Follow implements Serializable {

    /**
     * Creates a new instance of Follow
     */
    

    private String loginId =  Login.userid;
    private Login login;
    private String searchQuery;
    private ArrayList<UserDetails> usersSearch;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public ArrayList<UserDetails> getUsersSearch() {
        return usersSearch;
    }

    public void setUsersSearch(ArrayList<UserDetails> usersSearch) {
        this.usersSearch = usersSearch;
    }
    
    
    
    public Follow() {
    }
    
    public String search(String username)
    {
        loginId = username;
        return "search";
    }
     
    /** this searches for user **/
     public String getSearchUsers(String username)
     {
         
         ArrayList<UserDetails> userList = new ArrayList<UserDetails>();
         loginId = username;
         
         HashMap<String, String> usernames = new HashMap<String, String>();
         
         String fetchUsersQuery = "select user_loginId, user_name from t_user_details where user_name like '%"+searchQuery+"%'";
         
         Statement st = Database.getStatement();
         ResultSet rs = null;
         
         try
         {
             rs = st.executeQuery(fetchUsersQuery);
             
             while(rs.next())
             {
                 usernames.put(rs.getString(1), rs.getString(2));
             }
             
             
             
         }
         catch(Exception e)
         {
         
         }
         
         finally
         {
             try {
                 rs.close();
                 st.close();
             } catch (SQLException ex) {
                // Logger.getLogger(Follow.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println("Unable to close connection");
             }
             
         }
         
         
         
         
         //get the followers 
          Iterator it = usernames.entrySet().iterator();
         while(it.hasNext())
         {
             Map.Entry pair = (Map.Entry)it.next();
             UserDetails user = new UserDetails();
         
         String getFollowersCount = "select count(*) from t_followers where user_loginId = '"+pair.getKey()+"'";
         String getFollowingCount = "select count(*) from t_followings where user_loginId = '"+pair.getKey()+"'";
         
          Statement st1 = Database.getStatement();
          Statement st2 = Database.getStatement();
          Statement st3 = Database.getStatement();
          ResultSet rs1 = null;
          ResultSet rs2 = null;
          ResultSet rs3 = null;
         
         try
         {
             rs1 = st1.executeQuery(getFollowersCount);
             rs2 = st2.executeQuery(getFollowingCount);
             
             while(rs1.next())
             {
                 user.setFollowerCount(rs1.getInt(1));
             }
             
             while(rs2.next())
             {
                 user.setFollowingCount(rs2.getInt(1));
             }
             
             
             
              //rs3 = null;
             
             String getStatusQuery = "select * from t_followings where user_following_loginId='"+pair.getKey()+"'"
                     + " and user_loginId='"+username+"'";
             
             rs3 = st3.executeQuery(getStatusQuery);
             
             if(rs3.next())
             {
                 user.setStatus(true);
             }
             else
             {
                 user.setStatus(false);
             }
             
             
         }
         catch(Exception e)
         {
         
         }
         
         finally
         {
             try {
                 rs1.close();
                 rs2.close();
                 rs3.close();
                 st1.close();
                 st2.close();
                 st3.close();
                 
                 
                 
             } catch (SQLException ex) {
                // Logger.getLogger(Follow.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println("Unable to close connection");
             }
             
         }
         
         //add users
         user.setUsername(pair.getValue().toString());
         
         userList.add(user);
         
         }
         
         usersSearch = userList;
     
         return "searchresults";
    }
     
    /** adds user to following list **/
    public String changeFollow(String login, String user, int status)
    {
        String loginName = "";
        String userid = "";
        String getUserID = "select user_loginid from t_user_details where user_name = '"+user+"'";
        Statement st = Database.getStatement();
        ResultSet rs = null;
        ResultSet rs1 = null;
        Statement st5 = Database.getStatement();
        String getLoginUser = "select user_name from t_user_details where user_loginid = '"+login+"'";
        Statement st1 = Database.getStatement();
        Statement st2 = Database.getStatement();
        Statement st3 = Database.getStatement();
        Statement st4 = Database.getStatement();
         
        try
        {
            rs = st.executeQuery(getUserID);
            rs1 = st5.executeQuery(getLoginUser);
            while(rs1.next())
            {
                loginName = rs1.getString(1);
            }
            while(rs.next())
            {
               userid = rs.getString(1);
            }
            String userFollowerInsert = "insert into t_followers values('"+userid+"', '"+
                 login+"', '"+loginName+"')";
            String userFollowingInsert = "insert into t_followings values('"+login+"', '"+userid+"', '"+user+"')";
         
            String userFollowerDelete = "delete from t_followers where user_loginid='"+userid+"' and "
                 + "user_follower_loginid='"+login+"'";
            String userFollowingDelete = "delete from t_followings where user_loginid='"+login+"' and "
                 + "user_following_loginid='"+userid+"'";
        
            st3.executeUpdate(userFollowerDelete);
            st4.executeUpdate(userFollowingDelete);
            
            if(status == 1)
            {
            st1.executeUpdate(userFollowerInsert);
            st2.executeUpdate(userFollowingInsert);
            }
         //String queryFollower = "";
        }
        catch(SQLException ex)
        {
            Logger.getLogger(Follow.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                rs.close();
                rs1.close();
                st.close();
                if(status == 1)
            {
            st1.close();
            st2.close();
            }
                st3.close();
                st4.close();
                st5.close();
            }
            catch(SQLException ex)
            {
                 Logger.getLogger(Follow.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        // Login loginObj = new Login();
        // loginObj.loginMethod(login);
        return "Welcome";
    }
    
     
   public ArrayList<FollowingDetails> FollowersListMy() 
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            return null;
        }
        
       
         
     
        ArrayList<FollowingDetails> aray2 = new ArrayList<>();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelm8116";
        try
        {
            conn = DriverManager.getConnection(DB_URL,"patelm8116","1460202");
            stat = conn.createStatement();
//            rs = stat.executeQuery("Select * from t_tweet where user_loginId = '" + loginId + "' order by t_id desc");
rs = stat.executeQuery("Select user_following_loginId,user_following_name from t_followings where user_loginId = '" + loginId + "'");           
            while(rs.next())
            {
                aray2.add(new FollowingDetails(rs.getString(1),rs.getString(2)));
                 
            }
            
             
            return  aray2;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
               if(conn!= null)
                   conn.close();
               if(stat!= null)
                   stat.close();
               if(rs!=null)
                   rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    
    
}

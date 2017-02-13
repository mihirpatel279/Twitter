/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Mihir
 */
@Named(value = "tweet")
@SessionScoped
public class Tweet implements Serializable {

    /**
     * Creates a new instance of Tweet
     */
    public Tweet() {
        
        
    }
    
  
    private int tId;   
    
    private  String tweetDetails;
    private String loginId =  Login.userid;
    private String details = "";
    private String message = "";

    

    
    
    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    
     ArrayList<String> tweetList = new ArrayList<String>();
     ArrayList<String> tweetList2 = new ArrayList<String>();

    public ArrayList<String> getTweetList2() {
        return tweetList2;
    }

    public void setTweetList2(ArrayList<String> tweetList2) {
        this.tweetList2 = tweetList2;
    }

    public ArrayList<String> getTweetList() {
        return tweetList;
    }

    public void setTweetList(ArrayList<String> tweetList) {
        this.tweetList = tweetList;
    }
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTweetDetails() {
        return tweetDetails;
    }

    public void setTweetDetails(String tweetDetails) {
        this.tweetDetails = tweetDetails;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    
    
    public String TweetMethod()
    {
      
         Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
         
//         String sqlQuery3 = "Select max(t_id) from t_tweet";
//         
//          if(Database.IsThereAnyRecords(sqlQuery3))
//          {
//              tId = Integer.parseInt(sqlQuery3);
//          }
//          tId = tId+1;


        
        String sqlQuery1 = "Select max(t_id) from t_tweet";
       
        tId = Database.SelectQuerySingleRowColumn(sqlQuery1);
        
        tId++;

        String sqlQuery = "INSERT INTO t_tweet (user_loginId,t_date,t_details,t_tweet_count) "
                + "VALUES('" + loginId +  "','" + ts + "','" + tweetDetails + "','1')";
        
        
        String sqlQuery2 = "INSERT INTO t_retweet (t_id,t_retweet_count,user_loginId,t_date,t_details) "
                + "VALUES(' " + tId + "','1','" + loginId +  "','" + ts + "','" + tweetDetails + "')";
        
        if(Database.insertUpdateDelete(sqlQuery) && Database.insertUpdateDelete(sqlQuery2))
                    {
                                tweetDetails ="";   
                                message = "tweeted successfully";
                                  return "Welcome";  
                    }
                                
                                else
                                    {
                                     this.details = "Internal Error.Please try again";
                                        return "Error";
                                }
        
        
    }
    
  
   
   public ArrayList<TweetDetails> TweetsListMy() 
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            return null;
        }
        
       
         
     
        ArrayList<TweetDetails> aray2 = new ArrayList<>();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelm8116";
        try
        {
            conn = DriverManager.getConnection(DB_URL,"patelm8116","1460202");
            stat = conn.createStatement();
            rs = stat.executeQuery("Select * from t_tweet where user_loginId = '" + loginId + "' order by t_id desc");
           
            while(rs.next())
            {
                aray2.add(new TweetDetails(rs.getString(1),rs.getString(4),rs.getString(3), rs.getInt(2) ,rs.getInt(5)));
                 
            }
            
             this.message =  "Your total tweets are " + aray2.size() ;
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
    
    public ArrayList<TweetDetails> TweetsListOthers() 
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            return null;
        }
        
       
       message = "";
        ArrayList<TweetDetails> aray = new ArrayList<>();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelm8116";
        try
        {
            conn = DriverManager.getConnection(DB_URL,"patelm8116","1460202");
            stat = conn.createStatement(); 
//            rs = stat.executeQuery("Select * from t_tweet where user_loginId != '" + loginId + "' order by t_id desc");
            rs = stat.executeQuery( "Select * from t_tweet where user_loginID IN (Select user_following_loginId from t_followings where user_loginId = '" + loginId + "') order by t_id desc");
            while(rs.next())
            {
                aray.add(new TweetDetails(rs.getString(1),rs.getString(4),rs.getString(3), rs.getInt(2) ,rs.getInt(5)));
            }
            
             
            return aray;
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
    
    public String Retweet(String userID,int tweetId)
    {
        
        String user = userID;
        int tweetno = tweetId;
        int retweetNo = 0;
        String tweet = "";
         Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
                    
       try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            return null;
        }
      
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelm8116";
        try
        {
            conn = DriverManager.getConnection(DB_URL,"patelm8116","1460202");
            stat = conn.createStatement();
             //Query to select t_id;
            rs = stat.executeQuery("Select *  from t_retweet where t_id = '" + tweetno + "' order by t_retweet_count desc");

           
            if(rs.next())
            {
                retweetNo = rs.getInt(2);
                retweetNo++;
                tweet = rs.getString(5);
            }
            
        String sqlQuery2 = "INSERT INTO t_retweet (t_id,t_retweet_count,user_loginId,t_date,t_details) "
                + "VALUES(' " + tweetno + "','" + retweetNo + "','" + loginId +  "','" + ts + "','" + tweet + "')";
        
        String sqlQuery5 = "Update t_tweet set t_tweet_count = '" + retweetNo + "' where t_id = '" + tweetno + "'";
        
     
        
        if(Database.insertUpdateDelete(sqlQuery2) && Database.insertUpdateDelete(sqlQuery5) )
                     {
                                  message = "Retweeted successfully";
                                  return "Welcome";  
                      }
                                
                                else
                                    {
                                     this.details = "Internal Error.Please try again";
                                        return "Error";
                                }
             
           
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

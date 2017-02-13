
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mihir
 */
public class Database {
    
    private static final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/patelm8116";
    private static final String userName = "patelm8116";
    private static final String password = "1460202";
    
    
     public static boolean IsThereAnyRecords(String query)
    {
        LoadDriver();
        // To check if a Query returns a row
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        boolean resp = false;
        try
        {
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
                resp = true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                if(stat != null)
                    stat.close();
                if(rs != null)
                    rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return resp;
    }
    
    
    public static boolean insertUpdateDelete(String query)
    {
        LoadDriver();
        boolean response = false;
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        
        try
        {
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            stat.executeUpdate(query);
            response = true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            response = false;
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                if(stat != null)
                    stat.close();
                if(rs != null)
                    rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return response;
    }
        
    public static ArrayList<String> SelectQuery_IntArrayList(String query)
    {
        // For a query that returns an ArrayList<Integer>
        LoadDriver();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        ArrayList<String> resp = new ArrayList<>();
        try
        {
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while(rs.next())
            {
                resp.add(rs.getString(1));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                if(stat != null)
                    stat.close();
                if(rs != null)
                    rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return resp;
    }
    
       public static int SelectQuerySingleRowColumn(String query)
    {
        // For a Query Returning a Single Integer Value
        LoadDriver();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        int resp = -1;
        try
        {
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
                resp = rs.getInt(1);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                if(stat != null)
                    stat.close();
                if(rs != null)
                    rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return resp;
    }
       
       public static String SelectQuerySingleRowColumnString(String query)
    {
        // For a Query Returning a Single Integer Value
        LoadDriver();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        String resp = "-1";
        try
        {
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
                resp = rs.getString(1);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(conn != null)
                    conn.close();
                if(stat != null)
                    stat.close();
                if(rs != null)
                    rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return resp;
    }
    
    public static void LoadDriver()
    {
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
    }
    
    public static Statement getStatement()
    {
        
        LoadDriver();
        boolean response = false;
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        
        try
        {
            conn = DriverManager.getConnection(DB_URL,userName,password);
            stat = conn.createStatement();
           // stat.executeUpdate(query);
            //response = true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            response = false;
        }
        
        return stat;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mihir
 */
public class FollowingDetails {
    private String loginId;
    private String name;
    
    
      public FollowingDetails(String id, String n )
    {
        
       
        loginId = id;
       name = n;
        
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
      
      
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CommuterTrack;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

/**
 *
 * @author dm2474
 */
@Stateful
public class CTSession implements CTSessionRemote {
    private Integer user_id;

    @PostConstruct
    public void initialize() {
        user_id = new Integer(0);
    }

    @Override
    public boolean logInUser(String username, String password) {
        Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, "username:" + username + ",password:" + password + ",user_id:"+this.user_id, "error");
        if (user_id != 0)
        {
            
            return true;
        }
        else
        {
            this.user_id = 1;
            return false;
        }

    }

    @Override
    public boolean isLoggedIn() {
        return user_id > 0;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CommuterTrack;

import javax.ejb.Remote;

/**
 *
 * @author dm2474
 */
@Remote
public interface CTSessionRemote {

    boolean logInUser(String username, String password);

    boolean isLoggedIn();
    
}

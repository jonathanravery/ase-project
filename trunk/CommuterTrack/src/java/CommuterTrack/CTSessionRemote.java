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

    CtUser getUser(String username, String password);

    boolean addUser(String username, String password, int role);

    boolean editUser(int userId, String username, String pass, int role, int active);

    boolean addARoute(String user, String routeDescription, String routeStart, String routeEnd);



}

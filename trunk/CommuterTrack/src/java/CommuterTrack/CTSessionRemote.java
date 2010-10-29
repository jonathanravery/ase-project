/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CommuterTrack;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dm2474
 */
@Remote
public interface CTSessionRemote {

    CtUser getUser(String username, String password);

    CtUser getUser(int userId);

    List getAllUsers();

    List getAllRoutes(CtUser ub);

    boolean addUser(String username, String password, int role);

    boolean editUser(int userId, String username, String pass, int role, int active);

    boolean addARoute(CtUser user, String routeDescription, String routeStart, String routeEnd);

    boolean addTrip(Integer routeId, Date startDate, Date endDate, Integer status);

}

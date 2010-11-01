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

    List<CtUser> getAllUsers();

    boolean addUser(String username, String password, int role);

    boolean editUser(int userId, String username, String pass, int role, int active);

    boolean addARoute(CtUser user, String routeDescription, String routeStart, String routeEnd);

    boolean updateRoute(Integer routeId, String routeDescription, String routeStart, String routeEnd);

    CtRoute getRoute(Integer routeId);

    List<CtRoute> getUserRoutes(CtUser ub);

    List<CtRoute> getAllRoutes();

    CtTrip getTrip(Integer tripId);

    boolean addTrip(Integer routeId, Date startDate, Date endDate, Integer status);

    boolean editTrip(Integer tripId, CtRoute routebean, Date startDate, Date endDate, Integer status);

    boolean delTrip(Integer tripId);

    List<CtTrip> getUserTrips(Integer userId);

    List<CtTrip> getAllTrips();

    boolean userInTrip(Integer userId);
}

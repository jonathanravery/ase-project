/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CommuterTrack;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dm2474
 */
@Stateful
public class CTSession implements CTSessionRemote {
    private Integer user_id;

    @PersistenceContext(unitName = "CommuterTrackPU")
    private EntityManager em;


    @PostConstruct
    public void initialize() {
        user_id = new Integer(0);
    }

    @Override
    public boolean logInUser(String username, String password) {

        //Query q = em.createNamedQuery("CtUsers.findByUsername");
        //q.setParameter("username", username);
        Query q = em.createNamedQuery("CtUsers.findByUsername");
        q.setParameter("username", username);
        try {
            CtUsers curUser = (CtUsers) q.getSingleResult();
            if (curUser.getPassword().compareTo(password) == 0) {
                this.user_id = curUser.getUserId();
                return true;
            }
        } catch (NonUniqueResultException ex) {
        } catch (NoResultException ex) {
        }
        Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, "username:" + username + ",password:" + password + ",user_id:"+this.user_id, " login failed");
        this.user_id = 1;
        return false;
    }

    @Override
    public boolean isLoggedIn() {
        return user_id > 0;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean addARoute(String user, String routeDescription, String routeStart, String routeEnd){

    /*
     TODO: sanatize input
     TODO: get username from session
     
     */

    CtRoutes c = new CtRoutes();
    c.setDescription(routeDescription);
    c.setRouteStart(routeStart);
    c.setRouteEnd(routeEnd);
    c.setUsername(user);

    em.persist(c);
    return true;
    }

}

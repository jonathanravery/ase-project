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
import java.security.*;
import java.math.*;

/**
 *
 * @author dm2474
 */
@Stateful
public class CTSession implements CTSessionRemote {

    @PersistenceContext(unitName = "CommuterTrackPU")
    private EntityManager em;


    @PostConstruct
    public void initialize() {

    }

    @Override
    public CtUser getUser(String username, String password) {
        CtUser curUser = null;

        // md5 hash the password that came in
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(password.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            password = bigInt.toString(16);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, null, ex);
        }




        Query q = em.createNamedQuery("CtUser.findByUsername");
        q.setParameter("username", username);
        try {
            curUser = (CtUser) q.getSingleResult();
            if ((curUser.getPassword().compareTo(password) == 0) && curUser.getActive() == 1) {
                return curUser;
            }
        } catch (NonUniqueResultException ex) {
            return null;
        } catch (NoResultException ex) {
            return null;
        }
        Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, "username:" + username + ",password:" + password + ",user_id:"+curUser.getUserId(), " login failed");
        return curUser;
    }

    @Override
    public boolean addUser(String username, String pass, int role) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(pass.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            pass = bigInt.toString(16);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, null, ex);
        }

        CtUser newUser = new CtUser();
        newUser.setUsername(username);
        newUser.setPassword(pass);
        newUser.setRole(role);
        newUser.setActive(1);

        try {
            em.persist(newUser);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean editUser(int userId, String username, String pass, int role, int active) {
        return false;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean addARoute(String user, String routeDescription, String routeStart, String routeEnd){

    /*
     TODO: sanatize input
     TODO: get username from session
     TODO: Mariya suggests using user_id instead of the username
     */
   // if (user_id <= 0) user_id = new Integer(2);

    CtRoutes c = new CtRoutes();
    c.setDescription(routeDescription);
    c.setRouteStart(routeStart);
    c.setRouteEnd(routeEnd);
    
    Query q = em.createNamedQuery("CtUsers.findByUserId");
    //q.setParameter("userId", user_id);
    try {
        CtUser curUser = (CtUser) q.getSingleResult();
        c.setCtUser(curUser);
        //I'm not sure what this is
        //but should it really be in CtUser???  - mechanic
        //curUser.addToRoutes(c);
        //em.persist(curUser);
        em.merge(curUser);
    } catch (NonUniqueResultException ex) {
    } catch (NoResultException ex) {
    }

    return true;
    }
}

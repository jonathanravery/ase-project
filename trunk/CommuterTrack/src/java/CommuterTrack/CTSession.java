/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CommuterTrack;

//import java.sql.Date;
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
import java.util.HashMap;
import java.util.List;
import java.util.Date;

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
            // if the password passed in matches the one in the db
            if ((curUser.getPassword().compareTo(password) == 0) && curUser.getActive() == 1) {
                return curUser;
            } else {
                return null;

            }
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING,ex.toString(), "REACHED "+ex.toString());

            return null;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public CtUser getUser(int userId) {
        CtUser curUser = null;

        Query q = em.createNamedQuery("CtUser.findByUserId");
        q.setParameter("userId", userId);

        try {
            curUser = (CtUser) q.getSingleResult();
            return curUser;
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING,ex.toString(), "Non-unique user Id");
            return null;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List getAllUsers() {

        List CtUserList;

        Query q = em.createNamedQuery("CtUser.findAll");
        try{
            CtUserList =  q.getResultList();
            return CtUserList;

        } catch (Exception e){

            Logger.getLogger(CTSession.class.getName()).log(Level.WARNING,"caught an exception looking for all users "+e.toString());

        }

        return null;
    }

    @Override
    public List getUserRoutes(CtUser ub) {

        List CtRouteList;
        CtRoute ctr;
        //TODO: just get the routes owned by uid

        Query q = em.createNamedQuery("CtRoute.findByUserId");
//        q.setParameter("userId", ub.getUserId());
        q.setParameter("ctUser", ub);
        try {
            CtRouteList = q.getResultList();
            // if the password passed in matches the one in the db
            return CtRouteList;
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING,ex.toString(), "REACHED "+ex.toString());

            return null;
        } catch (NoResultException ex) {
            return null;
        }

    }

    @Override
    public List getAllRoutes() {
        Query q = em.createNamedQuery("CtRoute.findAll");;
        try {
            return q.getResultList();
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING,ex.toString(), "REACHED "+ex.toString());

            return null;
        } catch (NoResultException ex) {
            return null;
        }

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
        Logger.getLogger(CTSession.class.getName()).log(Level.WARNING, "in session.edituser userID: "+userId+" username: "+username+" pass: "+pass+" active: "+active+" role: "+role);

        CtUser tmpbean = this.getUser(userId);
        if (tmpbean == null) {
            return false;
        }

        tmpbean.setUsername(username);
        tmpbean.setRole(role);
        tmpbean.setActive(active);

        //if a new password was passed in, update with the hash of it.
        //otherwise leave alone.
        if (pass!=null && pass.length()>0){
            tmpbean.setPassword(this.retHash(pass));
        }// else {
        //     Logger.getLogger(CTSession.class.getName()).log(Level.WARNING, "in session.edituser userID: "+userId+" username: "+username+" pass: "+pass+" active: "+active+" role: "+role);
       // }

        em.persist(tmpbean);
        return true;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean addARoute(CtUser user, String routeDescription, String routeStart, String routeEnd){

        /*
         TODO: sanatize input
         */
       // if (user_id <= 0) user_id = new Integer(2);

        CtRoute c = new CtRoute();
        c.setDescription(routeDescription);
        c.setRouteStart(routeStart);
        c.setRouteEnd(routeEnd);

        c.setCtUser(user);
        //I'm not sure what this is
        //but should it really be in CtUser???  - mechanic
        // Mariya: since we have a constraint between the tables user and route
        // this will insure we don't violate the constraint
        // however we may not really need it. It looks like it works without it too
        //user.addToRoutes(c);
        //em.merge(user);
        em.persist(c);
        return true;
    }

    @Override
    public boolean updateRoute(Integer routeId, String routeDescription, String routeStart, String routeEnd){

        /*
         TODO: sanatize input
         */
        Query q = em.createNamedQuery("CtRoute.findByRouteId");
        q.setParameter("routeId", routeId);
        CtRoute route = (CtRoute) q.getSingleResult();


        route.setDescription(routeDescription);
        route.setRouteStart(routeStart);
        route.setRouteEnd(routeEnd);

        em.merge(route);
        return true;
    }

    private String retHash(String p){
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(p.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            p = bigInt.toString(16);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;
    }

    @Override
    public CtTrip getTrip(Integer tripId) {
        Query q = em.createNamedQuery("CtTrip.findByTripId");
        q.setParameter("tripId", tripId);

        // get the trip into an entity bean
        // update and persist
        try {
            return (CtTrip) q.getSingleResult();

        } catch (Exception e){
            Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, "caught exception trying to delete trip "+e.toString());
            return null;
        }

    }

    @Override
    public boolean addTrip(Integer routeId, Date startDate, Date endDate, Integer status) {
        CtTrip trip = new CtTrip();
        trip.setStartTime(startDate);
        trip.setEndTime(endDate);
        trip.setStatus(0);

        Query q = em.createNamedQuery("CtRoute.findByRouteId");
        q.setParameter("routeId", routeId);
        try {
            CtRoute route = (CtRoute) q.getSingleResult();
            trip.setCtRoute(route);
            em.persist(trip);
            return true;
        } catch (NonUniqueResultException ex) {
        } catch (NoResultException ex) {
        }
        //TODO: why does this always return false?
        return false;
    }

    @Override
    public boolean editTrip(Integer tripId, CtRoute routeBean, Date startDate, Date endDate, Integer status) {

        // we are assuming that the right person is editing the right trip... 
        // confirm that in the servlet level
        Query q = em.createNamedQuery("CtTrip.findByTripId");
        q.setParameter("tripId", tripId);

        // get the trip into an entity bean
        // update and persist
        try {
            CtTrip triptoedit = (CtTrip) q.getSingleResult();
            triptoedit.setCtRoute(routeBean);
            triptoedit.setStartTime(startDate);
            triptoedit.setEndTime(endDate);
            triptoedit.setStatus(status);

            em.merge(triptoedit);
            return true;

        } catch (Exception e){
            Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, "caught exception trying to delete trip "+e.toString());
            return false;
        }

    }

    @Override
    public CtRoute getRoute(Integer routeId){
        
        Query q = em.createNamedQuery("CtRoute.findByRouteId");
        q.setParameter("routeId", routeId);

        
        try {
            CtRoute route = (CtRoute) q.getSingleResult();
            return route;
        } catch (Exception e) {
            Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, "caught exception trying to delete trip " + e.toString());
            return null;
        }


    }

    @Override
    public boolean delTrip(Integer tripId) {
        Query q = em.createNamedQuery("CtTrip.findByTripId");
        q.setParameter("tripId", tripId);

        // get the trip into an entity bean
        // and delete
        try {
            CtTrip triptodel = (CtTrip) q.getSingleResult();
            em.remove(triptodel);
        } catch (Exception e){
            Logger.getLogger(CTSession.class.getName()).log(Level.SEVERE, "caught exception trying to delete trip "+e.toString());
            return false;
        }
        return true;
    }

    @Override
    public List<CtTrip> getUserTrips(Integer userId){

        List CtTripList;

        Query q = em.createNamedQuery("CtTrip.findByUserId");
        q.setParameter("userId", userId);
        try {
            CtTripList = q.getResultList();
            return CtTripList;
        } catch (Exception ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING,ex.toString(), "caught exception trying to get all user trips "+ex.toString());

            return null;
        }
    }

    @Override
    public List<CtTrip> getAllTrips(){
        List CtTripList;

        Query q = em.createNamedQuery("CtTrip.findAll");
        try {
            CtTripList = q.getResultList();
            return CtTripList;
        } catch (Exception ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, ex.toString(), "caught exception trying to get all trips " + ex.toString());
            return null;
        }
    }
    
    @Override
    public CtTrip getActiveTrip(Integer userId) {
        Query q = em.createNamedQuery("CtTrip.findByUserAndStatus");
        q.setParameter("userId", userId);
        q.setParameter("status", 0);
        try {
            return (CtTrip) q.getResultList().get(0);
        } catch (Exception ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, ex.toString(), "caught exception trying to get unfinished trips " + ex.toString());
            return null;
        }
    }

    @Override
    public boolean userInTrip(Integer userId) {
        if (getActiveTrip(userId) == null) {
            return false;
        } else {
            return true;
        }
        /*
        Query q = em.createNamedQuery("CtTrip.findByUserAndStatus");
        q.setParameter("userId", userId);
        q.setParameter("status", 0);
        try {
            List CtTripList = q.getResultList();
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, "Size of result (in userInTrip) is " + CtTripList.size());
            return CtTripList.size() > 0;
        } catch (Exception ex) {
            Logger.getLogger(CTUserController.class.getName()).log(Level.WARNING, ex.toString(), "caught exception trying to get unfinished trips " + ex.toString());
            return false;
        }
         *
         */
    }

}

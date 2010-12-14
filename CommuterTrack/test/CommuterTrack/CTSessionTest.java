package CommuterTrack;

import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Travel Timers
 */
public class CTSessionTest {

    static CTSessionRemote instance = null;
    private static String dbURL = "jdbc:derby://localhost:1527/sample;user=app;password=app";
    private static String dbDriver = "org.apache.derby.jdbc.ClientDriver";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;


    public CTSessionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Map properties = new HashMap<String, Object>();

        properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", "test-resource/domain.xml");
        EJBContainer c = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
        instance = (CTSessionRemote) c.getContext().lookup("java:global/classes/CTSession");
    }


    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        // code that will be invoked before each test starts
        try
        {
            Class.forName(dbDriver).newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL);
            stmt = conn.createStatement();
            stmt.addBatch("DELETE FROM CT_TRIPS");
            stmt.addBatch("DELETE FROM CT_ROUTES");
            stmt.addBatch("DELETE FROM CT_USERS");
            stmt.addBatch("DROP TABLE CT_TRIPS");
            stmt.addBatch("DROP TABLE CT_ROUTES");
            stmt.addBatch("DROP TABLE CT_USERS");
            stmt.addBatch("CREATE TABLE CT_USERS(user_id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),username VARCHAR(32) NOT NULL UNIQUE,password VARCHAR(32) NOT NULL,role INTEGER NOT NULL,active INTEGER NOT NULL)");
            stmt.addBatch("CREATE TABLE CT_ROUTES(route_id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),description VARCHAR(256) NOT NULL,user_id INTEGER NOT NULL CONSTRAINT ct_users_foreign_key \n REFERENCES CT_USERS ON DELETE CASCADE ON UPDATE RESTRICT,route_start VARCHAR(32) NOT NULL,route_end VARCHAR(32) NOT NULL,CONSTRAINT unique_desc_user UNIQUE(description, user_id))");
            stmt.addBatch("CREATE TABLE CT_TRIPS(trip_id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),route_id INTEGER NOT NULL CONSTRAINT ct_routes_foreign_key \n REFERENCES CT_ROUTES ON DELETE CASCADE ON UPDATE RESTRICT,start_time TIMESTAMP,end_time TIMESTAMP,status INTEGER)");
            stmt.addBatch("INSERT INTO CT_USERS (username, password, role, active) VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', " + CTConsts.ADMIN_USER + ", " + CTConsts.ACTIVE_USER + ")");
            stmt.addBatch("INSERT INTO CT_USERS (username, password, role, active) VALUES ('john', '21232f297a57a5a743894a0e4a801fc3', " + CTConsts.REGULAR_USER + ", " + CTConsts.ACTIVE_USER + ")");
            stmt.addBatch("INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (1, 'ADMIN TEST ROUTE', 'rstart', 'rend')");
            stmt.addBatch("INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (1, 'ADMIN TEST ROUTE No 2', 'rstart', 'rend')");
            stmt.addBatch("INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (2, 'USER TEST ROUTE No 1', 'home', 'school')");
            stmt.addBatch("INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (1, '2010-11-03 16:32:00.00', '2010-11-03 17:55:00.000'," + CTConsts.COMPLETED_TRIP + ")");
            stmt.addBatch("INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (3, '2010-12-06 10:32:11.132', '2010-11-06 11:05:13.909'," + CTConsts.COMPLETED_TRIP + ")");
            stmt.addBatch("INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (3, '2010-12-07 10:32:11.132', '2010-11-07 11:05:13.909'," + CTConsts.COMPLETED_TRIP + ")");
            stmt.addBatch("INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (3, '2010-12-08 7:32:00.000', null," + CTConsts.STARTED_TRIP + ")");
            stmt.executeBatch();
            conn.commit();
            stmt.close();            
            conn.close();
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getUser method, of class CTSession.
     */
    @Test
    public void testGetUser_String_String() throws Exception {
        System.out.println("getUser");
        String username = "admin";
        String password = "admin";
        CtUser u = new CtUser();
        u.setUserId(1);
        u.setRole(CTConsts.ADMIN_USER);
        u.setUsername(username);
        u.setPassword("21232f297a57a5a743894a0e4a801fc3");
        u.setActive(CTConsts.ACTIVE_USER);
        CtUser result = instance.getUser(username, password);
        assertEquals(result,u);
    }

    /**
     * Test of getUser method, of class CTSession.
     */
    @Test
    public void testGetUser_int() throws Exception {
        System.out.println("getUser");
        int userId = 2;
        CtUser u = new CtUser();
        u.setUserId(2);
        u.setUsername("john");
        u.setPassword("21232f297a57a5a743894a0e4a801fc3");
        u.setRole(CTConsts.REGULAR_USER);
        u.setActive(CTConsts.ACTIVE_USER);
        CtUser result = instance.getUser(userId);
        assertEquals(result,u);
    }

    /**
     * Test of getAllUsers method, of class CTSession.
     */
    @Test
    public void testGetAllUsers() throws Exception {
        System.out.println("getAllUsers");
        
        CtUser u1 = new CtUser();
        u1.setUserId(1);
        u1.setUsername("admin");
        u1.setPassword("21232f297a57a5a743894a0e4a801fc3");
        u1.setRole(CTConsts.ADMIN_USER);
        u1.setActive(CTConsts.ACTIVE_USER);
        
        CtUser u2 = new CtUser();
        u2.setUserId(2);
        u2.setUsername("john");
        u2.setPassword("21232f297a57a5a743894a0e4a801fc3");
        u2.setRole(CTConsts.REGULAR_USER);
        u2.setActive(CTConsts.ACTIVE_USER);

        List result = instance.getAllUsers();
        assertEquals(2, result.size());
        assertTrue(result.contains(u1));
        assertTrue(result.contains(u2));
    }

    /**
     * Test of delRoute method, of class CTSession.
     */
    @Test
    public void testDelRoute() throws Exception {
        System.out.println("delRoute");

        Integer routeId;
        boolean expResult;
        boolean result;
        routeId = null;
        expResult = false;
        result = instance.delRoute(routeId);
        assertEquals(expResult, result);

        routeId = new Integer(-1);
        expResult = false;
        result = instance.delRoute(routeId);
        assertEquals(expResult, result);

        routeId = new Integer(4);
        expResult = false;
        result = instance.delRoute(routeId);
        assertEquals(expResult, result);


        routeId = new Integer(1);
        expResult = true;
        result = instance.delRoute(routeId);
        assertEquals(expResult, result);

        assertEquals(null,instance.getRoute(routeId));
    }

    /**
     * Test of getUserRoutes method, of class CTSession.
     */
    @Test
    public void testGetUserRoutes() throws Exception {
        System.out.println("getUserRoutes");
        CtUser ub = new CtUser();
        ub.setActive(CTConsts.ACTIVE_USER);
        ub.setRole(CTConsts.ADMIN_USER);
        ub.setUserId(1);
        ub.setUsername("admin");
        List result = instance.getUserRoutes(ub);

        CtRoute r1 = new CtRoute();
        r1.setRouteId(1);
        r1.setDescription("ADMIN TEST ROUTE");
        r1.setRouteStart("rstart");
        r1.setRouteEnd("rend");

        CtRoute r2 = new CtRoute();
        r2.setRouteId(2);
        r2.setDescription("ADMIN TEST ROUTE No 2");
        r2.setRouteStart("rstart");
        r2.setRouteEnd("rend");

        assertEquals(2, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
    }

    /**
     * Test of getAllRoutes method, of class CTSession.
     */
    @Test
    public void testGetAllRoutes() throws Exception {
        System.out.println("getAllRoutes");
        //List expResult = null;
        List result = instance.getAllRoutes();

        CtRoute r1 = new CtRoute();
        r1.setRouteId(1);
        r1.setDescription("ADMIN TEST ROUTE");
        r1.setRouteStart("rstart");
        r1.setRouteEnd("rend");

        CtRoute r2 = new CtRoute();
        r2.setRouteId(2);
        r2.setDescription("ADMIN TEST ROUTE No 2");
        r2.setRouteStart("rstart");
        r2.setRouteEnd("rend");

        CtRoute r3 = new CtRoute();
        r3.setRouteId(2);
        r3.setDescription("USER TEST ROUTE No 1");
        r3.setRouteStart("home");
        r3.setRouteEnd("school");


        assertEquals(3, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
        assertTrue(result.contains(r2));
    }

    /**
     * Test of addUser method, of class CTSession.
     */
    @Test
    public void testAddUser() throws Exception {
        System.out.println("addUser");
        String username = "mariya";
        String pass = "21232f297a57a5a743894a0e4a801fc3";
        int role = CTConsts.REGULAR_USER;
        boolean expResult = true;
        boolean result = instance.addUser(username, pass, role);
        assertEquals(expResult, result);
    }

    /**
     * Test of editUser method, of class CTSession.
     */
    @Test
    public void testEditUser() throws Exception {
        System.out.println("editUser");
        int userId = 2;
        String username = "Dan";
        String pass = "Mechanic";
        int role = CTConsts.REGULAR_USER;
        int active = CTConsts.ACTIVE_USER;
        
        CtUser u = new CtUser();
        u.setUserId(2);
        u.setUsername(username);
        u.setPassword("64eb669723cae1983f03e34cca705c80");
        u.setRole(role);
        u.setActive(active);

        boolean expResult = true;
        boolean result = instance.editUser(userId, username, pass, role, active);
        assertEquals(expResult, result);
        CtUser resUser = instance.getUser(userId);
        assertEquals(u, resUser);
    }

    /**
     * Test of getRoute method, of class CTSession.
     */
    @Test
    public void testGetRoute() throws Exception {
        System.out.println("getRoute");

        Integer routeId;
        CtRoute expResult;
        CtRoute result;
        routeId = null;
        expResult = null;
        result = instance.getRoute(routeId);
        assertEquals(expResult, result);

        routeId = new Integer(-1);
        expResult = null;
        result = instance.getRoute(routeId);
        assertEquals(expResult, result);

        routeId = new Integer(4);
        expResult = null;
        result = instance.getRoute(routeId);
        assertEquals(expResult, result);


        routeId = new Integer(1);
        expResult = new CtRoute();
        expResult.setRouteId(1);
        expResult.setDescription("ADMIN TEST ROUTE");
        expResult.setRouteStart("rstart");
        expResult.setRouteEnd("rend");
        result = instance.getRoute(routeId);
        assertEquals(expResult.getDescription(), result.getDescription());
        assertEquals(expResult.getRouteStart(), result.getRouteStart());
        assertEquals(expResult.getRouteEnd(), result.getRouteEnd());
        assertEquals(expResult.getRouteId(), result.getRouteId());
    }

    /**
     * Test of addARoute method, of class CTSession.
     */
    @Test
    public void testAddARoute() throws Exception {
        System.out.println("addARoute");
        CtUser user = new CtUser();
        user.setUserId(1);
        user.setRole(CTConsts.ADMIN_USER);
        user.setUsername("admin");
        user.setPassword("21232f297a57a5a743894a0e4a801fc3");
        user.setActive(CTConsts.ACTIVE_USER);

        String routeDescription = "route added by test suite";
        String routeStart = "starting point by test";
        String routeEnd = "ending point by test";
        boolean expResult;
        boolean result;

        expResult = true;
        result = instance.addARoute(user, routeDescription, routeStart, routeEnd);
        assertEquals(expResult, result);

        // assert the new route is stored in the database
        CtRoute r = instance.getRoute(4);
        assertEquals(routeDescription, r.getDescription());
        assertEquals(routeStart, r.getRouteStart());
        assertEquals(routeEnd, r.getRouteEnd());
        assertEquals(user, r.getCtUser());

        //256 character route description should succeed
        routeDescription = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
       routeStart = "xxxxxxxxxxx";
       routeEnd = "xxxxx";

        expResult = true;
        try{
            result = instance.addARoute(user, routeDescription, routeStart, routeEnd);
        } catch(javax.ejb.EJBException xe){
            assert(false);
        }
        assertEquals(expResult, result);


        // 257 character route Description should fail
        //unfortunately, it fails with a DB exception
        routeDescription = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
       routeStart = "xxxxxxxxxxx";
       routeEnd = "xxxxx";

        expResult = false;
        try{
        result = instance.addARoute(user, routeDescription, routeStart, routeEnd);
        //the above should cause exception.  If it doesn't...
            result = true; //...this line will cause below assert to fail.  Yes it's sortof backward.
        }catch(javax.ejb.EJBException x){
            result = false;
        }
        assertEquals(expResult, result);


        //32 character route start should succeed
        routeDescription = "xxxxxxx";
        routeStart = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        routeEnd = "xxxxx";

        expResult = true;
        try{
            result = instance.addARoute(user, routeDescription, routeStart, routeEnd);
        } catch(javax.ejb.EJBException xe){
            assert(false);
        }
        assertEquals(expResult, result);


        // 33 character route start should fail
        routeDescription = "xxxxxxx";
        routeStart = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        routeEnd = "xxxxx";

        expResult = false;
        try{
            result = instance.addARoute(user, routeDescription, routeStart, routeEnd);
            //the above should cause exception.  If it doesn't...
            result = true; //...this line will cause below assert to fail.  Yes it's sortof backward.
        }catch(javax.ejb.EJBException x){
            result = false;
        }
        assertEquals(expResult, result);

        user.setUserId(3);
        expResult = false;
        try{
            result = instance.addARoute(user, routeDescription, routeStart, routeEnd);
            result = true;
        }catch(javax.ejb.EJBException x){
            result = false;
        }
        assertEquals(expResult, result);
        
        user = null;
        expResult = false;
        result = instance.addARoute(user, routeDescription, routeStart, routeEnd);
        assertEquals(expResult, result);
        

    }

    /**
     * Test of updateRoute method, of class CTSession.
     */
    @Test
    public void testUpdateRoute() throws Exception {
        System.out.println("updateRoute");
        Integer routeId = new Integer(1);
        String routeDescription = "route updated by test suite";
        String routeStart = "start updated";
        String routeEnd = "end updated";
        boolean expResult;
        boolean result;
        expResult = true;
        result = instance.updateRoute(routeId, routeDescription, routeStart, routeEnd);
        assertEquals(expResult, result);

        CtRoute route = new CtRoute();
        route.setRouteId(1);
        route.setRouteStart(routeStart);
        route.setRouteEnd(routeEnd);
        route.setDescription(routeDescription);
        assertEquals(route,instance.getRoute(routeId));

        //257 length string
        routeDescription = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        routeStart = "xxxxxxxxxxx";
        routeEnd = "xxxxx";

        expResult = false;
        try{
            result = instance.updateRoute(routeId, routeDescription, routeStart, routeEnd);
            //the above should cause exception.  If it doesn't...
            result = true; //...this line will cause below assert to fail.  Yes it's sortof backward.
        }catch(javax.ejb.EJBException x){
            result = false;
        }
        assertEquals(expResult, result);

        routeDescription = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        routeStart = "xxxxxxxxxxx";
        routeEnd = "xxxxx";

        expResult = true;
        try{
            result = instance.updateRoute(routeId, routeDescription, routeStart, routeEnd);
        }catch(javax.ejb.EJBException x){
            assert(false);
        }
        assertEquals(expResult, result);


    }

    /**
     * Test of getTrip method, of class CTSession.
     */
    @Test
    public void testGetTrip() throws Exception {
        System.out.println("getTrip");
        Integer tripId = -1;
        CtTrip expResult = null;
        CtTrip result = instance.getTrip(tripId);
        assertEquals(expResult, result);
        
        CtRoute r = new CtRoute();
        r.setRouteId(3);
        r.setDescription("USER TEST ROUTE No 1");
        r.setRouteStart("home");
        r.setRouteEnd("school");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

        CtTrip t = new CtTrip();
        t.setTripId(4);
        t.setCtRoute(r);
        t.setStartTime(format.parse("Dec 8, 2010 7:32 AM"));
        t.setEndTime(null);
        t.setStatus(0);

        tripId = 4;
        CtTrip res = instance.getTrip(tripId);
        assertEquals(t.getTripId(), res.getTripId());
        assertEquals(t.getCtRoute(), res.getCtRoute());
        assertEquals(t.getStartTime(), res.getStartTime());
        assertEquals(t.getEndTime(), res.getEndTime());
        assertEquals(t.getStatus(), res.getStatus());
    }

    /**
     * Test of getUserTrips method, of class CTSession.
     */
    @Test
    public void testGetUserTrips() throws Exception {
        System.out.println("getUserTrips");
        Integer userId = null;
        List expResult = new ArrayList();
        List result = instance.getUserTrips(userId);
        assertEquals(expResult, result);

        userId = 4;
        expResult = new ArrayList();
        result = instance.getUserTrips(userId);
        assertEquals(expResult, result);

        CtRoute r = instance.getRoute(1);
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

        CtTrip t = new CtTrip();
        t.setTripId(1);
        t.setCtRoute(r);
        t.setStartTime(format.parse("Nov 3, 2010 4:32 PM"));
        t.setEndTime(format.parse("Nov 3, 2010 5:55 PM"));
        t.setStatus(CTConsts.COMPLETED_TRIP);

        userId = 1;
        result = instance.getUserTrips(userId);
        assertEquals(1, result.size());
        
        assertEquals(((CtTrip)result.get(0)).getTripId(), t.getTripId());
        assertEquals(((CtTrip)result.get(0)).getCtRoute(), t.getCtRoute());
        assertEquals(((CtTrip)result.get(0)).getStartTime(), t.getStartTime());
        assertEquals(((CtTrip)result.get(0)).getEndTime(), t.getEndTime());
        assertEquals(((CtTrip)result.get(0)).getStatus(), t.getStatus());        
    }

    /**
     * Test of addTrip method, of class CTSession.
     */
    @Test
    public void testAddTrip() throws Exception {
        System.out.println("addTrip");
        Integer routeId = 1;
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
        Date startDate = format.parse("Dec 8, 2010 11:30 AM");
        Date endDate = format.parse("Dec 8, 2010 12:06 AM");
        Integer status = CTConsts.STARTED_TRIP;
        boolean expResult = true;
        boolean result = instance.addTrip(routeId, startDate, endDate, status);
        assertEquals(expResult, result);

        CtTrip t = new CtTrip();
        t.setTripId(5);
        CtRoute r = instance.getRoute(routeId);
        t.setCtRoute(r);
        t.setStartTime(startDate);
        t.setEndTime(endDate);
        t.setStatus(status);
        CtTrip resultTrip = instance.getTrip(5);
        assertEquals(t.getTripId(), resultTrip.getTripId());
        assertEquals(t.getCtRoute(), resultTrip.getCtRoute());
        assertEquals(t.getStartTime(), resultTrip.getStartTime());
        assertEquals(t.getEndTime(), resultTrip.getEndTime());
        assertEquals(t.getStatus(), resultTrip.getStatus());
    }

    /**
     * Test of editTrip method, of class CTSession.
     */
    @Test
    public void testEditTrip() throws Exception {
        System.out.println("editTrip");
        Integer tripId = 1;
        CtRoute routeBean = null;
        Date startDate = null;
        Date endDate = null;
        Integer status = null;
        
        boolean expResult = false;
        boolean result = instance.editTrip(tripId, routeBean, startDate, endDate, status);
        assertEquals(expResult, result);
        
        routeBean = new CtRoute();
        routeBean.setRouteId(3);
        routeBean.setDescription("USER TEST ROUTE No 1");
        routeBean.setRouteStart("home");
        routeBean.setRouteEnd("school");

        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
        tripId = 1;
        Date start = format.parse("Dec 9, 2010 11:30 AM");
        Date end = null;
        Integer st = CTConsts.STARTED_TRIP;


        CtTrip t = new CtTrip();
        t.setTripId(tripId);
        t.setCtRoute(routeBean);
        t.setStartTime(start);
        t.setEndTime(end);
        t.setStatus(st);

        expResult = true;
        result = instance.editTrip(tripId, routeBean, start, end, st);
        assertEquals(expResult, result);
    }

    /**
     * Test of delTrip method, of class CTSession.
     */
    @Test
    public void testDelTrip() throws Exception {
        System.out.println("delTrip");
        Integer tripId = -1;
        boolean expResult = false;
        boolean result = instance.delTrip(tripId);
        assertEquals(expResult, result);

        tripId = 2;
        expResult = true;
        result = instance.delTrip(tripId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllTrips method, of class CTSession.
     */
    @Test
    public void testGetAllTrips() throws Exception {
        System.out.println("getAllTrips");
        List result = instance.getAllTrips();
        assertEquals(4, result.size());
    }

    /**
     * Test of getActiveTrip method, of class CTSession.
     */
    @Test
    public void testGetActiveTrip() throws Exception {
        System.out.println("getActiveTrip");
        Integer userId = 2;
        CtTrip t = new CtTrip();

        CtTrip result = instance.getActiveTrip(userId);
        assertEquals(result.getTripId(), new Integer(4));
    }

    /**
     * Test of userInTrip method, of class CTSession.
     */
    @Test
    public void testUserInTrip() throws Exception {
        System.out.println("userInTrip");
        Integer userId = 2;
        boolean expResult = true;
        boolean result = instance.userInTrip(userId);
        assertEquals(expResult, result);
    }

}

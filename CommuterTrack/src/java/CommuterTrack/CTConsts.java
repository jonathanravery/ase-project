package CommuterTrack;

/**
 *
 * @author Travel Timers
 */
public class CTConsts {
    
    /*
     * role of an admin user
     */
    public static final int ADMIN_USER = 1;
    
    /*
     * role of a regular user
     */
    public static final int REGULAR_USER = 2;
    
    /*
     * status of an active user
     */
    public static final int ACTIVE_USER = 1;
    
/*
     * status of a deactivated user
     */
    public static final int DEACTIVATED_USER = 0;

    /*
     * status of a started trip
     */
    public static final int STARTED_TRIP = 0;

    /*
     * status of a stopped trip
     */
    public static final int STOPPED_TRIP = 1;

    /*
     * status of a completed trip
     */
    public static final int COMPLETED_TRIP = 2;

    private CTConsts(){
    //this prevents even the native class from
    //calling this ctor as well :
    throw new AssertionError();
    }
}

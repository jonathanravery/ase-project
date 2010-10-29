/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CommuterTrack;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dm2474
 */
@Entity
@Table(name = "CT_TRIPS")
@NamedQueries({
    @NamedQuery(name = "CtTrips.findAll", query = "SELECT c FROM CtTrips c"),
    @NamedQuery(name = "CtTrips.findByTripId", query = "SELECT c FROM CtTrips c WHERE c.tripId = :tripId"),
    @NamedQuery(name = "CtTrips.findByStartTime", query = "SELECT c FROM CtTrips c WHERE c.startTime = :startTime"),
    @NamedQuery(name = "CtTrips.findByEndTime", query = "SELECT c FROM CtTrips c WHERE c.endTime = :endTime")})
public class CtTrips implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TRIP_ID")
    private Integer tripId;
    @Column(name = "START_TIME")
    @Temporal(TemporalType.DATE)
    private Date startTime;
    @Column(name = "END_TIME")
    @Temporal(TemporalType.DATE)
    private Date endTime;

    public CtTrips() {
    }

    public CtTrips(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tripId != null ? tripId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CtTrips)) {
            return false;
        }
        CtTrips other = (CtTrips) object;
        if ((this.tripId == null && other.tripId != null) || (this.tripId != null && !this.tripId.equals(other.tripId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommuterTrack.CtTrips[tripId=" + tripId + "]";
    }

}

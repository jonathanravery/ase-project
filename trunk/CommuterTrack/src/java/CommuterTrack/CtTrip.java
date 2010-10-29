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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author maria
 */
@Entity
@Table(name = "CT_TRIPS")
@NamedQueries({
    @NamedQuery(name = "CtTrip.findAll", query = "SELECT c FROM CtTrip c"),
    @NamedQuery(name = "CtTrip.findByTripId", query = "SELECT c FROM CtTrip c WHERE c.tripId = :tripId"),
    @NamedQuery(name = "CtTrip.findByStartDate", query = "SELECT c FROM CtTrip c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "CtTrip.findByStartTime", query = "SELECT c FROM CtTrip c WHERE c.startTime = :startTime"),
    @NamedQuery(name = "CtTrip.findByEndDate", query = "SELECT c FROM CtTrip c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "CtTrip.findByEndTime", query = "SELECT c FROM CtTrip c WHERE c.endTime = :endTime"),
    @NamedQuery(name = "CtTrip.findByStatus", query = "SELECT c FROM CtTrip c WHERE c.status = :status")})
public class CtTrip implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TRIP_ID")
    private Integer tripId;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "ROUTE_ID", referencedColumnName = "ROUTE_ID")
    @ManyToOne(optional = false)
    private CtRoute ctRoute;

    public CtTrip() {
    }

    public CtTrip(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CtRoute getCtRoute() {
        return ctRoute;
    }

    public void setCtRoute(CtRoute ctRoute) {
        this.ctRoute = ctRoute;
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
        if (!(object instanceof CtTrip)) {
            return false;
        }
        CtTrip other = (CtTrip) object;
        if ((this.tripId == null && other.tripId != null) || (this.tripId != null && !this.tripId.equals(other.tripId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommuterTrack.CtTrip[tripId=" + tripId + "]";
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CommuterTrack;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author maria
 */
@Entity
@Table(name = "CT_ROUTES")
@NamedQueries({
    @NamedQuery(name = "CtRoute.findAll", query = "SELECT c FROM CtRoute c"),
    @NamedQuery(name = "CtRoute.findByRouteId", query = "SELECT c FROM CtRoute c WHERE c.routeId = :routeId"),
//    @NamedQuery(name = "CtRoute.findByUserId", query = "SELECT c FROM CtRoute c WHERE c.userId = :userId"),
    @NamedQuery(name = "CtRoute.findByUserId", query = "SELECT c FROM CtRoute c WHERE c.ctUser = :ctUser"),
    @NamedQuery(name = "CtRoute.findByDescription", query = "SELECT c FROM CtRoute c WHERE c.description = :description"),
    @NamedQuery(name = "CtRoute.findByRouteStart", query = "SELECT c FROM CtRoute c WHERE c.routeStart = :routeStart"),
    @NamedQuery(name = "CtRoute.findByRouteEnd", query = "SELECT c FROM CtRoute c WHERE c.routeEnd = :routeEnd")})
public class CtRoute implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROUTE_ID")
    private Integer routeId;
    @Basic(optional = false)
//    @Column(name = "USER_ID")
//    private Integer userId;
//    @Basic(optional = false)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @Column(name = "ROUTE_START")
    private String routeStart;
    @Basic(optional = false)
    @Column(name = "ROUTE_END")
    private String routeEnd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ctRoute")
    private Collection<CtTrip> ctTripCollection;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private CtUser ctUser;

    public CtRoute() {
    }

    public CtRoute(Integer routeId) {
        this.routeId = routeId;
    }

    public CtRoute(Integer routeId, String description, String routeStart, String routeEnd) {
        this.routeId = routeId;
        this.description = description;
        this.routeStart = routeStart;
        this.routeEnd = routeEnd;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRouteStart() {
        return routeStart;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public Collection<CtTrip> getCtTripCollection() {
        return ctTripCollection;
    }

    public void setCtTripCollection(Collection<CtTrip> ctTripCollection) {
        this.ctTripCollection = ctTripCollection;
    }

    public CtUser getCtUser() {
        return ctUser;
    }

    public void setCtUser(CtUser ctUser) {
        this.ctUser = ctUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routeId != null ? routeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CtRoute)) {
            return false;
        }
        CtRoute other = (CtRoute) object;
        if ((this.routeId == null && other.routeId != null) || (this.routeId != null && !this.routeId.equals(other.routeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommuterTrack.CtRoute[routeId=" + routeId + "]";
    }

}

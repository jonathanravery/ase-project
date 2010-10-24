/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CommuterTrack;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author COMS 4156
 */
@Entity
@Table(name = "CT_ROUTES")
@NamedQueries({
    @NamedQuery(name = "CtRoutes.findAll", query = "SELECT c FROM CtRoutes c"),
    @NamedQuery(name = "CtRoutes.findByRouteId", query = "SELECT c FROM CtRoutes c WHERE c.routeId = :routeId"),
    @NamedQuery(name = "CtRoutes.findByDescription", query = "SELECT c FROM CtRoutes c WHERE c.description = :description"),
    @NamedQuery(name = "CtRoutes.findByUsername", query = "SELECT c FROM CtRoutes c WHERE c.username = :username"),
    @NamedQuery(name = "CtRoutes.findByRouteStart", query = "SELECT c FROM CtRoutes c WHERE c.routeStart = :routeStart"),
    @NamedQuery(name = "CtRoutes.findByRouteEnd", query = "SELECT c FROM CtRoutes c WHERE c.routeEnd = :routeEnd")})
public class CtRoutes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROUTE_ID")
    private Integer routeId;
    @Basic(optional = false)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Column(name = "ROUTE_START")
    private String routeStart;
    @Basic(optional = false)
    @Column(name = "ROUTE_END")
    private String routeEnd;

    public CtRoutes() {
    }

    public CtRoutes(Integer routeId) {
        this.routeId = routeId;
    }

    public CtRoutes(Integer routeId, String description, String username, String routeStart, String routeEnd) {
        this.routeId = routeId;
        this.description = description;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routeId != null ? routeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CtRoutes)) {
            return false;
        }
        CtRoutes other = (CtRoutes) object;
        if ((this.routeId == null && other.routeId != null) || (this.routeId != null && !this.routeId.equals(other.routeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommuterTrack.CtRoutes[routeId=" + routeId + "]";
    }

}

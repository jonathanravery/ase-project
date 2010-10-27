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
 * @author dm2474
 */
@Entity
@Table(name = "CT_USERS")
@NamedQueries({
    @NamedQuery(name = "CtUser.findAll", query = "SELECT c FROM CtUser c"),
    @NamedQuery(name = "CtUser.findByUserId", query = "SELECT c FROM CtUser c WHERE c.userId = :userId"),
    @NamedQuery(name = "CtUser.findByUsername", query = "SELECT c FROM CtUser c WHERE c.username = :username"),
    @NamedQuery(name = "CtUser.findByPassword", query = "SELECT c FROM CtUser c WHERE c.password = :password"),
    @NamedQuery(name = "CtUser.findByRole", query = "SELECT c FROM CtUser c WHERE c.role = :role"),
    @NamedQuery(name = "CtUser.findByActive", query = "SELECT c FROM CtUser c WHERE c.active = :active")})
public class CtUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "ROLE")
    private int role;
    @Basic(optional = false)
    @Column(name = "ACTIVE")
    private int active;

    public CtUser() {
    }

    public CtUser(Integer userId) {
        this.userId = userId;
    }

    public CtUser(Integer userId, String username, String password, int role, int active) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CtUser)) {
            return false;
        }
        CtUser other = (CtUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommuterTrack.CtUser[userId=" + userId + "]";
    }

}

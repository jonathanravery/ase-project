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
    @NamedQuery(name = "CtUsers.findAll", query = "SELECT c FROM CtUsers c"),
    @NamedQuery(name = "CtUsers.findByUserId", query = "SELECT c FROM CtUsers c WHERE c.userId = :userId"),
    @NamedQuery(name = "CtUsers.findByUsername", query = "SELECT c FROM CtUsers c WHERE c.username = :username"),
    @NamedQuery(name = "CtUsers.findByPassword", query = "SELECT c FROM CtUsers c WHERE c.password = :password")})
public class CtUsers implements Serializable {
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

    public CtUsers() {
    }

    public CtUsers(Integer userId) {
        this.userId = userId;
    }

    public CtUsers(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CtUsers)) {
            return false;
        }
        CtUsers other = (CtUsers) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommuterTrack.CtUsers[userId=" + userId + "]";
    }

}

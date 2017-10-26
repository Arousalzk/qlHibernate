package win.arousalzk.hibernate.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User.java
 * 用户实体
 * @author Arousalzk
 * @date 2017-10-25
 * @version 1.0
 */
@Entity
@Table(name="userinfo")
//EJB注解用的表名为userinfo不带下划线
public class User {
    @Id
    private int id;
    private String username;
    private String password;
 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
}

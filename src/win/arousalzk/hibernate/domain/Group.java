package win.arousalzk.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="group_table")//对应表名
/**
 * Group.java
 * 群组x
 * @author Arousalzk
 * @date 2017-10-26
 * @version 1.0
 */
public class Group {
    
    @Id//主键
    @Column(name="group_id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int id;
    
    @Column(name="group_name")
    /**对应列名*/
    private String groupName;
    
//    @OneToMany(targetEntity=User.class)
//    @JoinColumn(name="group_id") 
//    private Set<User> users = new HashSet<User>();
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    
//    @OneToMany(mappedBy="user")
    /**此处不一定一定要加，测试不加好像没问题*/
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
}

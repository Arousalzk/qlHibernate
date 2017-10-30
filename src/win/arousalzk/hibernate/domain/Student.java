package win.arousalzk.hibernate.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;


/**
 * Student.java
 * 学生实体
 * @author Arousalzk
 * @date 2017-10-29
 * @version 1.0
 */
@Entity
@Table(name="stu_info")
public class Student implements Serializable{

    /**
     * 创建bean时候获取随机值
     */
    private static final long serialVersionUID = -4644749856767636159L;

    @Id
    @Column(name="stu_id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private int id;
    
    @Column(name="stu_name")
    private String name;
    
    @Column(name="stu_courses")
    @ManyToMany(targetEntity=Course.class)                                       //指定多对多关系
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinTable(name="sc_table",
    joinColumns=@JoinColumn(name="stu_id",nullable=false),
    inverseJoinColumns=@JoinColumn(name="course_id"))
    private Set<Course> courses;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Course> getCourses() {
        return courses;
    }
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((courses == null) ? 0 : courses.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        if (courses == null) {
            if (other.courses != null) {
                return false;
            }
        } else if (!courses.equals(other.courses)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
    
    
}

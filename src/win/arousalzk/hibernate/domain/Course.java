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
 * Course.java
 * 课程实体
 * @author Arousalzk
 * @date 2017-10-29
 * @version 1.0
 */
@Entity
@Table(name="course_info")
public class Course implements Serializable{

    /**
     * 创建bean时自动生成
     */
    private static final long serialVersionUID = 6307934951311825869L;

    @Id
    @Column(name="course_id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy="increment")
    private int id;
    
    @Column(name="course_name")
    private String name;
    
    @Column(name="course_students")
    @ManyToMany(targetEntity=Student.class)                                       //指定多对多关系
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinTable(name="sc_table",
    joinColumns=@JoinColumn(name="course_id",nullable=false),
    inverseJoinColumns=@JoinColumn(name="stu_id"))
    private Set<Student> students;
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
    public Set<Student> getStudents() {
        return students;
    }
    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((students == null) ? 0 : students.hashCode());
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
        Course other = (Course) obj;
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
        if (students == null) {
            if (other.students != null) {
                return false;
            }
        } else if (!students.equals(other.students)) {
            return false;
        }
        return true;
    }
    
    
    
}

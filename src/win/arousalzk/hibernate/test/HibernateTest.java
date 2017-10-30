package win.arousalzk.hibernate.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import win.arousalzk.hibernate.domain.Course;
import win.arousalzk.hibernate.domain.Group;
import win.arousalzk.hibernate.domain.Student;
import win.arousalzk.hibernate.domain.User;
import win.arousalzk.hibernate.utils.HibernateUtils;

/**
 * HibernateTest.java
 * 主要测试Hibernate的CRUD功能
 * @author Arousalzk
 * @date 2017-10-26
 * @version 1.0
 */
public class HibernateTest {

    /**
     * 测试增操作
     */
    @Test
    public void testCreate() {
        //通过默认位置获取Hibernate配置文件
        Configuration configuration = new Configuration().configure();
        //由配置文件获取SessionFactory
        SessionFactory factory = configuration.buildSessionFactory();
        //开启一个session，≈JDBC的connection
        Session session = factory.openSession();
        //开启事务
        session.beginTransaction();
        //创建User实体
        User user = new User();
        user.setId(1);
        user.setUsername("Tom");
        user.setPassword("impwd");
        //保存实体
        session.save(user);
        //提交事务
        session.getTransaction().commit();        
        //关闭资源
        session.close();
        factory.close();
    }

    /**
     * 测试查询操作
     */
    @Test
    public void testRetrieve() {
        //获取配置文件
        Configuration config = new Configuration().configure();
        //获取SessionFactory
        SessionFactory sf = config.buildSessionFactory();
        //开个Session
        Session session = sf.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        //执行查询操作

        StringBuilder sb = new StringBuilder();
        //select * from user_info;
        sb.append("from ").append(User.class.getName());
        //获得List结果集
        Query query = session.createQuery(sb.toString());
        @SuppressWarnings("unchecked")
        List<User> userList = query.list();
        System.out.println("...............................");
        for (User user : userList) {
            //显示获得结果的所有用户姓名
            System.out.println(user.getUsername());
        }
        //提交事务
        transaction.commit();
        //释放资源
        session.close();
        sf.close();        
        
        
        
    }
    
    /**
     * 测试修改操作
     */
    @Test
    public void testUpdate() {
        //获取配置文件
        Configuration configuration = new Configuration().configure();
        //通过配置文件获取SessionFactory
        SessionFactory factory = configuration.buildSessionFactory();
        //打开一个Session
        Session session = factory.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        //进行修改操作
        //将名字为Tom的用户的密码改为imcat
        //首先查到叫Tom这位，获取其实体
        StringBuilder sb = new StringBuilder();
//        sb.append("from ").append(User.class.getName()).append(" where user_username=?");
        //EJB形式
        sb.append("from ").append(User.class.getName()).append(" where username=?");
        Query query = session.createQuery(sb.toString());
        query.setString(0, "Tom");
        //另外一种写法
        /*
        sb.append("from ").append(User.class.getName()).append(" where user_username=:name");
        Query query = session.createQuery(sb.toString());
        query.setString("name", "Tom");
        */
        @SuppressWarnings("unchecked")
        //获取名字为Tom的用户列表，依次修改其密码
        List<User> list = query.list();
        for (User user : list) {
            user.setPassword("imcat");
        }
        //提交事务
        transaction.commit();
    }


    /**
     * 测试删除操作
     */
    @Test
    public void testDelete() {
        //获取配置信息
        Configuration config = new Configuration().configure();
        //通过配置信息获取SessionFactory
        SessionFactory sf = config.buildSessionFactory();
        //得到Session
        Session session = sf.openSession();
        //开启事务
        session.beginTransaction();
        //执行删除操作，删除名字为Tom的用户
        //获取名字为Tom的用户
        StringBuilder hql = new StringBuilder();
        //注意表中字段不要写错
//        hql.append("from ").append(User.class.getName()).append(" where user_username=?");
        //EJB注入
        hql.append("from ").append(User.class.getName()).append(" where username=?");
        Query query = session.createQuery(hql.toString());
        query.setString(0, "Tom");
        @SuppressWarnings("unchecked")
        List<User> list = query.list();
        for (User user : list) {
            session.delete(user);
        }
        //提交事务
        session.getTransaction().commit();
    }


    /**
     * 测试用工具类create
     */
    @Test
    public void testUtilsCreate() {
        HibernateUtils utils = HibernateUtils.getHibernateUtils();
        User user = new User();
        user.setId(2);
        user.setPassword("password");
        user.setUsername("Mr.U");
        utils.addObject(user);
    }
    
    /**
     * 测试用工具类查找
     */
    @Test
    public void testUtilsRetrieve() {
        HibernateUtils utils = HibernateUtils.getHibernateUtils();
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(User.class.getName()).append(" where user_id=?");
        List<Object> users = utils.getObject(sb.toString(), "2");
        for (Object object : users) {
            System.out.println(((User) object).getUsername());
        }
    }
    
    
    /**
     * 测试用工具类进行删除
     */
    @Test
    public void testUtilsDelete() {
        HibernateUtils utils = HibernateUtils.getHibernateUtils();
        User user = new User();
        user.setId(2);
        utils.deleteObject(user);
    }
    
    /**
     * 专门测试通过HQL进行各种查询操作
     */
    @Test
    public void testHQL() {
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        //1. 准备数据
        /*
            USE `hibernate`;
            INSERT INTO userinfo VALUES (1, 'AAAA', 'Anderson');
            INSERT INTO userinfo VALUES (2, 'BBBB', 'Bill');
            INSERT INTO userinfo VALUES (3, 'CCCC', 'Candy');
            INSERT INTO userinfo VALUES (4, 'DDDD', 'Dave');
         */
        //2. 普通查询
        //2.1 查询所有用户
        /*
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(User.class.getSimpleName());
        Query query = session.createQuery(sb.toString());
        @SuppressWarnings("unchecked")
        List<User> users = query.list();
        for (User user : users) {
            System.out.println(user.getUsername());
        }
        */
        // 3. 条件查询
        /*
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(User.class.getSimpleName()).append(" where username=?");
        Query query = session.createQuery(sb.toString());
        query.setParameter(0, "Bill");
        @SuppressWarnings("unchecked")
        List<User> users = query.list();
        for (User user : users) {
            System.out.println(user.getPassword());
        }
         */
        //4. 原生SQL查询
        /*
        String queryString = "select * from userinfo";
        Query query = session.createSQLQuery(queryString).addEntity(User.class);
        //q.setParameter(0, "Jack");
        @SuppressWarnings("unchecked")
        List<User> users = query.list();
        for (User user : users) {
            System.out.println(user.getUsername());
        }
        */
        //5. criteria 查询
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.idEq(1));
        @SuppressWarnings("unchecked")
        List<User> list = criteria.list();
        for (User user : list) {
            System.out.println(user.getUsername());
        }
        session.getTransaction().commit();
        session.close();
        factory.close();
       

    }


    /**
     * 测试一对多查询
     */
    @Test
    public void testOne2Many() {
        
        
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        
        
        // 新建一个群组
        Group group = new Group();
        group.setGroupName("newGroup");

        // 新建两个用户
        Set<User> users = new HashSet<User>();
        User user1 = new User();
        user1.setUsername("newAAA");
        user1.setPassword("new11AA");
        User user2 = new User();
        user2.setUsername("newBBB");
        user2.setPassword("new222bbb");
        
        
        users.add(user1);
        users.add(user2);
        
        // 保存相关对象
        for (User user : users) {
            session.save(user);
        }
//        group.setUsers(users);

       
        session.save(group);
        
        session.getTransaction().commit();
        session.close();
        factory.close();
    }
    /**
     * 测试多对一查询
     */
    @Test
    public void testMany2One() {
        
        
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        
        
        // 新建一个群组
        Group group = new Group();
        group.setGroupName("AlexBob2");

        // 新建两个用户
        User user1 = new User();
        user1.setUsername("Alex2");
        user1.setPassword("Alexppp2");
        User user2 = new User();
        user2.setUsername("Bob2");
        user2.setPassword("Bobppp2");
        user1.setGroup(group);
        user2.setGroup(group);
        
        session.save(user1);
        
        session.save(user2);

       
        session.save(group);
        
        session.getTransaction().commit();
        session.close();
        factory.close();
    }
    
    /**
     * 测试多对多
     */
    @Test
    public void testMany2Many() {
        
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        
        // 新建三门课程
        Set<Course> courses = new HashSet<Course>();
        Course c1 = new Course();
        c1.setName("Chinese");
        Course c2 = new Course();
        c2.setName("English");
        Course c3 = new Course();
        c3.setName("Math");
        courses.add(c1);
        courses.add(c2);
        courses.add(c3);

        // 新建三个学生
        Set<Student> students = new HashSet<Student>();
        Student s1 = new Student();
        s1.setName("Michael");
        Student s2 = new Student();
        s2.setName("KangKang");
        Student s3 = new Student();
        s3.setName("Jane");
        students.add(s1);
        students.add(s2);
        students.add(s3);

        // 将三个学生都关联到每一门课程中
        c1.setStudents(students);
        c2.setStudents(students);
        c3.setStudents(students);

        // 保存相关对象
        session.save(c1);
        session.save(c2);
        session.save(c3);
        
        
        
        session.getTransaction().commit();
        session.close();
        factory.close();
    }
}

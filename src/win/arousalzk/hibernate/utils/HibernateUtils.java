package win.arousalzk.hibernate.utils;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;


/**
 * HibernateUtils.java
 * 把【增删改查】操作封装成单独的功能类或函数，这样使用起来就很方便不用直接改代码。
 * @author Arousalzk
 * @date 2017-10-26
 * @version 1.0
 */
public class HibernateUtils {

    
    private Session session;
    
    private HibernateUtils() {
        //私有化构造方法
        session = new Configuration().configure().buildSessionFactory().openSession();
    }
    
    
    private static class Holder {
        private static final HibernateUtils SINSTANCE = new HibernateUtils();
    }
    
    /**
     * 获取HibernateUtils实例
     * @return HibernateUtils实例
     */
    public static HibernateUtils getHibernateUtils() {
        return Holder.SINSTANCE;
    }
    
    
    
    /**
     * 添加对象save(Object object)
     */
    public void addObject(Object object) {
        //FIXME: 不知道这里要不要再操作之后释放session和SessionFactory
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
    }
    
    /**
     * 通过hql查询对象
     * @param hql hql语句
     * @param args 参数
     * @return 列表形式的对象集合
     */
    @SuppressWarnings("unchecked")
    public List<Object> getObject(String hql, String ...args) {
        List<Object> list = null;
        session.beginTransaction();
        
        Query query = session.createQuery(hql);
        for(int i=0; i<args.length; i++) {
            query.setString(i, args[i]);
        }
        list = query.list();
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * 删除对象
     * @param object
     */
    public void deleteObject(Object object) {
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
    }
    
}

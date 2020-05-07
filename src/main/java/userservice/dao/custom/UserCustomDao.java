package userservice.dao.custom;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import userservice.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserCustomDao {

    @Autowired
    EntityManager entityManager;

    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public Optional<User> userByEmailAndPassword(String email,String password){
        List<User> userList = null;
        Query query = entityManager.createNativeQuery("select * from user where email=? and password =?",User.class);
        query.setParameter(1,email);
        query.setParameter(2,password);
        userList = query.getResultList();
        return  userList!=null && !userList.isEmpty() ? Optional.ofNullable(userList.get(0)) : Optional.ofNullable(null);

    }
}

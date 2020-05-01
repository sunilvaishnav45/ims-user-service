package userservice.dao.custom;

import userservice.entity.Brand;
import org.hibernate.Session;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class BrandCustomDao {

    @Autowired
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(BrandCustomDao.class);

    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public Optional<Brand> getBrandByName(String brand){
        List<Brand> brands = null;
        Query query = entityManager.createNativeQuery("select * from brand where brand = ?",Brand.class);
        query.setParameter(1,brand);
        brands = query.getResultList();
        return brands!=null && !brands.isEmpty()? Optional.ofNullable(brands.get(0)) : Optional.ofNullable(null);
    }

}

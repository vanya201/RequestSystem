package org.common.seeder;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitializer {

    @Autowired
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        String checkIndexSql = "SELECT COUNT(*) FROM pg_indexes WHERE indexname = 'unique_friendship';";
        int count = (Integer) entityManager.createNativeQuery(checkIndexSql).getSingleResult();

        if (count == 0) {
            String sql = "CREATE UNIQUE INDEX unique_friendship " +
                    "ON friend_request (LEAST(sender, receiver), GREATEST(sender, receiver));";
            entityManager.createNativeQuery(sql).executeUpdate();
        }
    }
}


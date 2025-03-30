package org.common.seeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;


    @PostConstruct
    public void init() {
        String sql = "CREATE UNIQUE INDEX IF NOT EXISTS unique_friendship " +
                "ON friend_request (LEAST(sender_id, receiver_id), GREATEST(sender_id, receiver_id));";
        jdbcTemplate.execute(sql);
    }
}

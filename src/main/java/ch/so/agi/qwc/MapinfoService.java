package ch.so.agi.qwc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MapinfoService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private final JdbcTemplate jdbcTemplate;

    public MapinfoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getInfo(double x, double y, String crs) {
        String stmt = "SELECT "
                + "gemeindename "
                + "FROM "
                + "PUBLIC.HOHEITSGRENZEN_GEMEINDEGRENZE "
                + "WHERE ST_Intersects(geometrie, ST_Transform(ST_SetSRID(ST_MakePoint(?, ?), ?), 2056))";
        try {
            String gemeinde = jdbcTemplate.queryForObject(stmt, String.class, x, y, crs);       
            // Ich verstehe den Response-Struktur nicht so wirklich. List of List?
            return List.of("Gemeinde", gemeinde);                
        } catch (EmptyResultDataAccessException e) {
            log.warn("no record found");
            return null;
        }
    }
}

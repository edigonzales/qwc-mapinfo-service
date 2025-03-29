package ch.so.agi.qwc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MapinfoController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    
    public MapinfoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(@RequestHeader Map<String, String> headers, HttpServletRequest request) {
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });
        
        log.info("server name: " + request.getServerName());
        log.info("context path: " + request.getContextPath());
        
        log.info("ping"); 
        
        return new ResponseEntity<String>("qwc-mapinfo-service", HttpStatus.OK);
    }    
    
    @GetMapping("/")
    public String getInfo(@RequestParam(name = "pos", required = true) String pos, 
            @RequestParam(name = "crs", required = true) String crs) {
        
        String[] posArray = pos.split(",");
        double x = Double.valueOf(posArray[0]);
        double y = Double.valueOf(posArray[1]);
        
        var foo = jdbcTemplate.query("SELECT * FROM PUBLIC.HOHEITSGRENZEN_GEMEINDEGRENZE LIMIT 1", new RowMapper<String>() {

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {

                var bar = rs.getString(3);
                System.out.println(bar);
                return bar;
            }
            
        });
        
        return "foo";
    }
    
}

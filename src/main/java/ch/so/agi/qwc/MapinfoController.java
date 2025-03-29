package ch.so.agi.qwc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MapinfoController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final MapinfoService mapinfoService;
    
    public MapinfoController(MapinfoService mapinfoService) {
        this.mapinfoService = mapinfoService;
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
    public ResponseEntity<?> getInfo(@RequestParam(name = "pos", required = true) String pos, 
            @RequestParam(name = "crs", required = true) String crs) {
        
        String[] posArray = pos.split(",");
        double x = Double.valueOf(posArray[0]);
        double y = Double.valueOf(posArray[1]);
        String[] crsArray = crs.split(":");
        String crsString = crsArray[1];
        
        List<String> info = mapinfoService.getInfo(x, y, crsString);

        // Komische Response-Struktur: List of List?! 
        if (info != null) {
            return ResponseEntity.ok(Map.of("results", List.of(info)));
        } else {
            return ResponseEntity.ok(Map.of("results", new ArrayList<String>()));
        } 
    }    
}

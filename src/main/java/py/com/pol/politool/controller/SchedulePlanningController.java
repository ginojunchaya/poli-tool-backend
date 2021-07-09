package py.com.pol.politool.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import py.com.pol.politool.model.SchedulePlanningData;
import py.com.pol.politool.service.SchedulePlanningService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/schedulePlanning")
public class SchedulePlanningController {

    private final Logger LOGGER = LogManager.getLogger(SchedulePlanningController.class);

    @Autowired
    private SchedulePlanningService schedulePlanningService;

    @PostMapping("/parse")
    public ResponseEntity parse(
        @RequestParam("file") MultipartFile file, HttpServletRequest request
    )
    {
        try {
            SchedulePlanningData data = schedulePlanningService.parse(file, request);
            return new ResponseEntity(data, HttpStatus.OK);
        }
        catch(Exception ex){
            LOGGER.error("Error in parse", ex);
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

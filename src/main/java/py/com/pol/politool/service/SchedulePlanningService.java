package py.com.pol.politool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import py.com.pol.politool.ExcelHelper;
import py.com.pol.politool.model.Carrer;
import py.com.pol.politool.model.SchedulePlanningData;
import py.com.pol.politool.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class SchedulePlanningService {

    @Autowired
    private ExcelHelper excelHelper;

    public SchedulePlanningData parse(MultipartFile file, HttpServletRequest request) throws IOException {
        List<Carrer> carrers = excelHelper.extractCarrers(
            file.getInputStream(), StringUtil.extractExtension(file.getOriginalFilename())
        );
        for(Carrer carrer : carrers){
            carrer.setCourses(
                excelHelper.extractCourses(
                    file.getInputStream(), carrer.getCode(),
                    StringUtil.extractExtension(file.getOriginalFilename())
                )
            );
        }
        return SchedulePlanningData.builder()
        .carrers(carrers)
        .fileName(file.getOriginalFilename())
        .build();
    }

}

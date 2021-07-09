package py.com.pol.politool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import py.com.pol.politool.model.*;
import py.com.pol.politool.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelHelper {

    private static final String CODE_SHEET = "Códigos";
    private static final String CARRERS = "CARRERAS";
    private static final String COURSES = "Item";

    public List<Carrer> extractCarrers(InputStream stream, String extension){
        try {
            Workbook workbook = instantiateWorkbook(stream, extension);
            Sheet sheet = workbook.getSheet(CODE_SHEET);
            Integer carrersInitialRow = null;
            for(int i = 0;;i++){
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(row.getFirstCellNum());
                if(CARRERS.equals(cell.getStringCellValue())){
                    carrersInitialRow = i + 2;
                    break;
                }
            }
            if(carrersInitialRow == null){
                throw new RuntimeException("No se encontraron carreras");
            }
            List<Carrer> carrers = new ArrayList<>();
            for(int i = carrersInitialRow;;i++){
                Row row = sheet.getRow(i);
                Cell code = row.getCell(0);

                if(CellType.BLANK.equals(code.getCellType())){
                    break;
                }

                Cell name = row.getCell(1);
                Carrer carrer = Carrer.builder()
                .code(code.getStringCellValue())
                .name(name.getStringCellValue())
                .build();
                carrers.add(carrer);
            }
            return carrers;
        }
        catch (Exception ex){
            throw new RuntimeException("Error in extract carrers: " + ex.getMessage());
        }
    }

    public List<Course> extractCourses(InputStream stream, String carrer, String extension){
        try {
            Workbook workbook = instantiateWorkbook(stream, extension);
            Sheet sheet = workbook.getSheet(carrer);
            Integer coursesInitialRow = null;
            for(int i = 0;;i++){
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(row.getFirstCellNum());
                if(COURSES.equals(cell.getStringCellValue())){
                    coursesInitialRow = i+1;
                    break;
                }
            }
            if(coursesInitialRow == null){
                throw new RuntimeException("No se encontraron asignaturas");
            }
            List<Course> courses = new ArrayList<>();
            for(int i = coursesInitialRow;;i++){
                Row row = sheet.getRow(i);
                Cell item = row.getCell(0);
                if(item == null || CellType.BLANK.equals(item.getCellType())){
                    break;
                }

                Schedule schedule = Schedule.builder()
                .finals(extractFinals(row))
                .lessons(extractLessons(row))
                .partials(extractPartials(row))
                .build();

                Section section = Section.builder()
                .plan(getIntegerCellValue(7, row))
                .turn(getCellValue(8, row))
                .code(row.getCell(9).getStringCellValue())
                .platform(getCellValue(10, row))
                .teachers(extractTeachers(row))
                .schedule(schedule)
                .build();

                Course course = Course.builder()
                .item(getIntegerCellValue(0, row))
                .name(row.getCell(2).getStringCellValue())
                .level(getIntegerCellValue(3, row))
                .semester(getIntegerCellValue(4, row))
                .emphasis(getCellValue(6, row))
                .section(section)
                .build();

                courses.add(course);
            }
            return courses;
        }
        catch (Exception ex){
            throw new RuntimeException("Error in extract courses: " + ex.getMessage(), ex);
        }
    }

    private List<Lesson> extractLessons(Row row){
        List<Lesson> lessons = new ArrayList<>();

        for(int i = 23; i <= 28; i++){
            if(!CellType.BLANK.equals(row.getCell(i).getCellType())){
                String timeRange = getCellValue(i, row);
                String[] timeRangeSplitted = timeRange.split("\\-");
                Lesson lesson = Lesson.builder()
                .dayOfWeek(DayOfWeek.find(i))
                .startsAt(timeRangeSplitted.length > 0 ? timeRangeSplitted[0] : null)
                .endsAt(timeRangeSplitted.length > 1 ? timeRangeSplitted[1] : null)
                .build();
                lessons.add(lesson);
            }
        }

        return lessons;
    }

    private List<Exam> extractPartials(Row row){
        List<Exam> partials = new ArrayList<>();

        Exam firstPartial = Exam.builder()
        .examType(ExamType.FIRST_PARTIAL)
        .date(StringUtil.extractDate(getCellValue(15, row)))
        .dayOfWeek(StringUtil.extractDayOfWeek(getCellValue(15, row)))
        .time(getCellValue(16, row))
        .build();

        Exam secondPartial = Exam.builder()
        .examType(ExamType.SECOND_PARTIAL)
        .date(StringUtil.extractDate(getCellValue(17, row)))
        .dayOfWeek(StringUtil.extractDayOfWeek(getCellValue(17, row)))
        .time(getCellValue(18, row))
        .build();

        partials.add(firstPartial);
        partials.add(secondPartial);

        return partials;
    }

    private List<Exam> extractFinals(Row row){
        List<Exam> finals = new ArrayList<>();

        Exam firstFinal = Exam.builder()
        .examType(ExamType.FIRST_FINAL)
        .date(StringUtil.extractDate(getCellValue(19, row)))
        .dayOfWeek(StringUtil.extractDayOfWeek(getCellValue(19, row)))
        .time(getCellValue(20, row))
        .build();

        Exam secondFinal = Exam.builder()
        .examType(ExamType.SECOND_FINAL)
        .date(StringUtil.extractDate(getCellValue(21, row)))
        .dayOfWeek(StringUtil.extractDayOfWeek(getCellValue(21, row)))
        .time(getCellValue(22, row))
        .build();

        finals.add(firstFinal);
        finals.add(secondFinal);

        return finals;
    }

    private List<Teacher> extractTeachers(Row row){
        List<Teacher> teachers = new ArrayList<>();
        String titles[] = row.getCell(11).getStringCellValue().split("\n");
        String lastNames[] = row.getCell(12).getStringCellValue().split("\n");
        String firstNames[] = row.getCell(13).getStringCellValue().split("\n");
        String emails[] = row.getCell(14).getStringCellValue().split("\n");
        Integer max = Math.max(Math.max(titles.length, lastNames.length), firstNames.length);
        for(int i = 0; i < max; i++){
            Teacher teacher = Teacher.builder()
            .email(emails.length > i ? emails[i] : null)
            .firstName(firstNames.length > i ? firstNames[i] : null)
            .lastName(lastNames.length > i ? lastNames[i] : null)
            .title(titles.length > i ? titles[i] : null)
            .build();
            teachers.add(teacher);
        }
        return teachers;
    }

    public Workbook instantiateWorkbook(InputStream stream, String extension) throws IOException {
        return "xlsx".equals(extension) ? new XSSFWorkbook(stream) : new HSSFWorkbook(stream);
    }

    private String getCellValue(Integer index, Row row){
        try {
            return row.getCell(index).getStringCellValue();
        }
        catch (RuntimeException ex){
            return null;
        }
    }

    private Integer getIntegerCellValue(Integer index, Row row){
        try {
            return Integer.valueOf(String.valueOf(row.getCell(index).getNumericCellValue()));
        }
        catch (Throwable ex){
            return null;
        }
    }

}

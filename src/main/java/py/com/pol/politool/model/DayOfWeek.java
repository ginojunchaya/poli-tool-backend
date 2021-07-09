package py.com.pol.politool.model;

public enum DayOfWeek {

    MONDAY("Lunes", 23),
    TUESDAY("Martes", 24),
    WEDNESDAY("Miércoles", 25),
    THURSDAY("Jueves", 26),
    FRIDAY("Viernes", 27),
    SATURDAY("Sábado", 28);

    private String day;
    private Integer code;

    DayOfWeek(String day, Integer code){
        this.day = day;
        this.code = code;
    }

    public static String find(Integer code){
        for(DayOfWeek value : DayOfWeek.values()){
            if(code.equals(value.code)){
               return value.day;
            }
        }
        return null;
    }

}

package py.com.pol.politool.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exam implements Serializable {

    @JsonProperty("dia")
    private String dayOfWeek;

    @JsonProperty("fecha")
    private String date;

    @JsonProperty("hora")
    private String time;

    @JsonProperty("tipo")
    private ExamType examType;

}

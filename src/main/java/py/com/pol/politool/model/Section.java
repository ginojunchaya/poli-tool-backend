package py.com.pol.politool.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Section implements Serializable {

    @JsonProperty("codigo")
    private String code;

    @JsonProperty("turno")
    private String turn;

    @JsonProperty("plan")
    private Integer plan;

    @JsonProperty("plataforma")
    private String platform;

    @JsonProperty("profesores")
    private List<Teacher> teachers;

    @JsonProperty("horario")
    private Schedule schedule;

}

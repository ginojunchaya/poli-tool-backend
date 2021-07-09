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
public class Course implements Serializable {

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("item")
    private Integer item;

    @JsonProperty("nivel")
    private Integer level;

    @JsonProperty("semestre")
    private Integer semester;

    @JsonProperty("enfasis")
    private String emphasis;

    @JsonProperty("seccion")
    private Section section;

}

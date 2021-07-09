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
public class Schedule implements Serializable {

    @JsonProperty("clases")
    private List<Lesson> lessons;

    @JsonProperty("parciales")
    private List<Exam> partials;

    @JsonProperty("finales")
    private List<Exam> finals;

}

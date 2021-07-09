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
public class Lesson implements Serializable {

    @JsonProperty("dia")
    private String dayOfWeek;

    @JsonProperty("hora_inicio")
    private String startsAt;

    @JsonProperty("hora_fin")
    private String endsAt;

}

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
public class Teacher implements Serializable {

    @JsonProperty("titulo")
    private String title;

    @JsonProperty("nombre")
    private String firstName;

    @JsonProperty("apellido")
    private String lastName;

    @JsonProperty("email")
    private String email;

}

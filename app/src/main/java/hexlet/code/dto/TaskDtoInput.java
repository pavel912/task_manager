package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDtoInput {
    private long id;

    @NotBlank
    private String name;

    @Lob
    private String description;

    @NotNull
    private long taskStatusId;

    private long executorId;

    private Instant createdAt;
}

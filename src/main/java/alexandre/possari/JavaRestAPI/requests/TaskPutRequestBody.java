package alexandre.possari.JavaRestAPI.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskPutRequestBody {
    private long id;
    @NotEmpty(message = "The task title cannot be empty")
    @NotNull(message = "The task title cannot be null")
    @NotBlank(message = "The task title cannot be blank")
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
}

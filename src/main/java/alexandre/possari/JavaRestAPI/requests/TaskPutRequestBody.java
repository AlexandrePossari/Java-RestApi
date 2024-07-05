package alexandre.possari.JavaRestAPI.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskPutRequestBody {
    private long id;
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
}

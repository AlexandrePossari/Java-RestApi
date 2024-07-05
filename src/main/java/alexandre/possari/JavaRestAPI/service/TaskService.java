package alexandre.possari.JavaRestAPI.service;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.repository.TaskRepository;
import alexandre.possari.JavaRestAPI.requests.TaskPostRequestBody;
import alexandre.possari.JavaRestAPI.requests.TaskPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> listAll(){
        return taskRepository.findAll();
    }

    public Task findByIdOrThrowBadRequestException(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task not Found"));
    }

    public Task save(TaskPostRequestBody taskPostRequestBody) {
        return taskRepository.save(Task.builder().title(taskPostRequestBody.getTitle())
                        .description(taskPostRequestBody.getDescription())
                        .dueDate(taskPostRequestBody.getDueDate())
                        .status(taskPostRequestBody.getStatus())
                .build());
    }

    public void replace(TaskPutRequestBody taskPutRequestBody) {
        Task savedTask = findByIdOrThrowBadRequestException(taskPutRequestBody.getId());
        Task task = Task.builder()
                .id(savedTask.getId())
                .title(taskPutRequestBody.getTitle())
                .description(taskPutRequestBody.getDescription())
                .dueDate(taskPutRequestBody.getDueDate())
                .status(taskPutRequestBody.getStatus())
                .build();
        taskRepository.save(task);
    }

    public void delete(long id) {
        taskRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}

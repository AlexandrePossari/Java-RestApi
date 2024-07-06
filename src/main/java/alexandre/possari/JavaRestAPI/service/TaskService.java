package alexandre.possari.JavaRestAPI.service;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.exception.BadRequestException;
import alexandre.possari.JavaRestAPI.mapper.TaskMapper;
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
                .orElseThrow(() -> new BadRequestException("Task not Found"));
    }

    public List<Task> findByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    public Task save(TaskPostRequestBody taskPostRequestBody) {
        return taskRepository.save(TaskMapper.INSTANCE.toTask(taskPostRequestBody));
    }

    public void replace(TaskPutRequestBody taskPutRequestBody) {
        Task savedTask = findByIdOrThrowBadRequestException(taskPutRequestBody.getId());
        Task task = TaskMapper.INSTANCE.toTask(taskPutRequestBody);
        task.setId(savedTask.getId());
        taskRepository.save(task);
    }

    public void delete(long id) {
        taskRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}

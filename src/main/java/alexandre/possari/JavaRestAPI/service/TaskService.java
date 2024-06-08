package alexandre.possari.JavaRestAPI.service;

import alexandre.possari.JavaRestAPI.model.Task;
import alexandre.possari.JavaRestAPI.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> listAll(){
        return taskRepository.findAll();
    }
}

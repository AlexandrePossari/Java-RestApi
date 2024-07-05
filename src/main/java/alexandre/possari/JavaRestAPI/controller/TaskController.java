package alexandre.possari.JavaRestAPI.controller;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.requests.TaskPostRequestBody;
import alexandre.possari.JavaRestAPI.requests.TaskPutRequestBody;
import alexandre.possari.JavaRestAPI.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("tasks")
@Log4j2
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> list() {
        return ResponseEntity.ok(taskService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Task> findById(@PathVariable long id) {
        return ResponseEntity.ok(taskService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody TaskPostRequestBody taskPostRequestBody) {
        return new ResponseEntity<>(taskService.save(taskPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody TaskPutRequestBody taskPutRequestBody) {
        taskService.replace(taskPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

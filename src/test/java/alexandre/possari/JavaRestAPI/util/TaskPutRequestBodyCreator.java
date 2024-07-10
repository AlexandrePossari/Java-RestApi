package alexandre.possari.JavaRestAPI.util;

import alexandre.possari.JavaRestAPI.requests.TaskPutRequestBody;

public class TaskPutRequestBodyCreator {
    public static TaskPutRequestBody createTaskPutRequestBody(){
        return TaskPutRequestBody.builder()
                .id(TaskCreator.createValidUpdatedTask().getId())
                .title(TaskCreator.createValidUpdatedTask().getTitle())
                .description(TaskCreator.createValidUpdatedTask().getDescription())
                .status(TaskCreator.createValidUpdatedTask().getStatus())
                .dueDate(TaskCreator.createValidUpdatedTask().getDueDate())
                .build();
    }
}

package alexandre.possari.JavaRestAPI.util;

import alexandre.possari.JavaRestAPI.requests.TaskPostRequestBody;

public class TaskPostRequestBodyCreator {
    public static TaskPostRequestBody createTaskPostRequestBody(){
        return TaskPostRequestBody.builder()
                .title(TaskCreator.createTaskToBeSaved().getTitle())
                .description(TaskCreator.createTaskToBeSaved().getDescription())
                .status(TaskCreator.createTaskToBeSaved().getStatus())
                .dueDate(TaskCreator.createTaskToBeSaved().getDueDate())
                .build();
    }
}

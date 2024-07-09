package alexandre.possari.JavaRestAPI.util;

import alexandre.possari.JavaRestAPI.domain.Task;

import java.time.LocalDate;

public class TaskCreator {
    public static Task createTaskToBeSaved(){
        LocalDate date = LocalDate.of(2024, 12, 21);;
        return Task.builder()
                .title("Task Test")
                .description("Task Test")
                .status("Done")
                .dueDate(date)
                .build();
    }

    public static Task createValidTask(){
        LocalDate date = LocalDate.of(2024, 12, 21);;
        return Task.builder()
                .id(1L)
                .title("Task Test")
                .description("Task Test")
                .status("Done")
                .dueDate(date)
                .build();
    }

    public static Task createValidUpdatedAnime(){
        LocalDate date = LocalDate.of(2024, 12, 21);;
        return Task.builder()
                .id(1L)
                .title("Task Test Updated")
                .description("Task Test Updated")
                .status("Done")
                .dueDate(date)
                .build();
    }
}

package be.tftic.spring.demo.api;

import be.tftic.spring.demo.bll.TaskService;
import be.tftic.spring.demo.bll.TaskServiceImpl;

public class TaskController {

    private final TaskService service = new TaskServiceImpl();
}

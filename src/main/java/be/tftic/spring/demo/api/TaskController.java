package be.tftic.spring.demo.api;

import be.tftic.spring.demo.bll.TaskService;
import be.tftic.spring.demo.bll.TaskServiceImpl;
import be.tftic.spring.demo.domain.Task;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // GET - http://localhost:8080/task/_
    @GetMapping("/{id}")
    public Task getOne(@PathVariable long id){
        return service.getOne( id );
    }

    // GET - http://localhost:8080/task?minUrgency=_
    @GetMapping
    public List<Task> getWithMinUrgency(@RequestParam int minUrgency) {
        return service.getWithUrgencyGreaterThan( minUrgency );
    }

    // POST - http://localhost:8080/task
    @PostMapping
    public void create(@RequestBody Task task){
        service.addTask(task);
    }

}

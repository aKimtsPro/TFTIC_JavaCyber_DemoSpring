package be.tftic.spring.demo.bll;

import be.tftic.spring.demo.domain.Task;

import java.util.List;

public class TaskServiceImpl implements TaskService{
    @Override
    public Task getOne(long id) {
        return null;
    }

    @Override
    public List<Task> getWithUrgencyGreaterThan(int minUrgency) {
        return null;
    }

    @Override
    public void addTask(Task task) {

    }
}

package hexlet.code.services;

import hexlet.code.domain.Task;
import hexlet.code.domain.TaskStatus;
import hexlet.code.domain.User;
import hexlet.code.dto.TaskDtoInput;
import hexlet.code.dto.TaskDtoOutput;
import hexlet.code.exceptions.EntityNotFoundException;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskStatusRepository taskStatusRepository;

    @Autowired
    UserService userService;

    @Autowired
    TaskStatusService taskStatusService;

    @Override
    public Task createTask(TaskDtoInput taskDtoInput) {
        TaskStatus taskStatus = taskStatusRepository.findById(taskDtoInput.getTaskStatusId());
        User author = userService.getCurrentUser();
        User executor = userRepository.findById(taskDtoInput.getExecutorId());

        Task task = new Task();
        task.setName(taskDtoInput.getName());
        task.setDescription(taskDtoInput.getDescription());
        task.setTaskStatus(taskStatus);
        task.setAuthor(author);
        task.setExecutor(executor);

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(TaskDtoInput taskDtoInput) {
        Task task = taskRepository.findById(taskDtoInput.getId());
        if (task == null) {
            throw new EntityNotFoundException("Task with id " + taskDtoInput.getId() + " does not exist");
        }

        TaskStatus taskStatus = taskStatusRepository.findById(taskDtoInput.getTaskStatusId());
        User executor = userRepository.findById(taskDtoInput.getExecutorId());

        task.setName(taskDtoInput.getName());
        task.setDescription(taskDtoInput.getDescription());
        task.setTaskStatus(taskStatus);
        task.setExecutor(executor);

        return taskRepository.save(task);
    }

    @Override
    public TaskDtoOutput taskToTaskDto(Task task) {
        TaskDtoOutput taskDtoOutput = new TaskDtoOutput();
        taskDtoOutput.setId(task.getId());
        taskDtoOutput.setName(task.getName());
        taskDtoOutput.setDescription(task.getDescription());
        taskDtoOutput.setTaskStatus(taskStatusService.taskStatusToDto(task.getTaskStatus()));
        taskDtoOutput.setAuthor(userService.userToUserDto(task.getAuthor()));
        taskDtoOutput.setExecutor(userService.userToUserDto(task.getExecutor()));
        taskDtoOutput.setCreatedAt(task.getCreatedAt());

        return taskDtoOutput;
    }
}

package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {

    private PriorityRepository hibernatePriorityRepository;

    @Override
    public Optional<Priority> add(Priority priority) {
        return hibernatePriorityRepository.add(priority);
    }

    @Override
    public List<Priority> findAll() {
        return hibernatePriorityRepository.findAll();
    }

    @Override
    public Optional<Priority> findById(int id) {
        return hibernatePriorityRepository.findById(id);
    }
}

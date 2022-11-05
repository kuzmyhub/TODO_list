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
public class HibernatePriorityService implements PriorityService {

    private PriorityRepository store;

    @Override
    public Priority add(Priority priority) {
        return store.add(priority);
    }

    @Override
    public List<Priority> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<Priority> findById(int id) {
        return store.findById(id);
    }
}

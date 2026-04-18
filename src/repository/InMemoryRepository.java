package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import model.Identificavel;

public abstract class InMemoryRepository<T extends Identificavel> {
    private final List<T> registros = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public T save(T entidade) {
        if (entidade.getId() == null) {
            entidade.setId(sequence.getAndIncrement());
            registros.add(entidade);
            return entidade;
        }

        for (int i = 0; i < registros.size(); i++) {
            if (registros.get(i).getId().equals(entidade.getId())) {
                registros.set(i, entidade);
                return entidade;
            }
        }

        registros.add(entidade);
        return entidade;
    }

    public List<T> findAll() {
        return List.copyOf(registros);
    }

    public Optional<T> findById(Long id) {
        return registros.stream()
                .filter(registro -> registro.getId().equals(id))
                .findFirst();
    }
}


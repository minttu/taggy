package me.pieso.taggy.db;

import io.dropwizard.hibernate.AbstractDAO;
import me.pieso.taggy.core.Tag;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class TagDAO extends AbstractDAO<Tag> {
    public TagDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Tag> findByName(String name) {
        return Optional.ofNullable(get(name));
    }
}

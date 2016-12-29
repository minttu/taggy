package me.pieso.taggy.daos;

import io.dropwizard.hibernate.AbstractDAO;
import me.pieso.taggy.models.Tag;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class TagDAO extends AbstractDAO<Tag> {
    public TagDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Tag> findByName(String name) {
        return Optional.ofNullable(get(name));
    }

    public Tag save(Tag tagParameter) {
        return persist(tagParameter);
    }
}

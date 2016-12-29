package me.pieso.taggy.daos;

import io.dropwizard.hibernate.AbstractDAO;
import me.pieso.taggy.models.Image;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class ImageDAO extends AbstractDAO<Image> {

    public ImageDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Image> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public List<Image> getImages(long cursor) {
        Query query = currentSession().createQuery("FROM Image AS image WHERE image.id < :cursor ORDER BY image.id DESC")
                .setParameter("cursor", cursor)
                .setMaxResults(25);
        return list(query);
    }

    public List<Image> getImagesHavingTags(long cursor, String[] tagList) {
        String sql = "SELECT image.* "
                + "FROM ( "
                + "  SELECT image_tag.image_id "
                + "  FROM image_tag "
                + "  WHERE image_tag.tag_name IN (:tags) "
                + "  GROUP BY image_tag.image_id "
                + "  HAVING COUNT(1) = :count "
                + ") s "
                + "JOIN image "
                + "  ON image.id = s.image_id "
                + "WHERE image.id < :cursor "
                + "ORDER BY image.id DESC";

        Query query = currentSession().createSQLQuery(sql)
                .addEntity(Image.class)
                .setParameterList("tags", tagList)
                .setParameter("count", (long) tagList.length)
                .setParameter("cursor", cursor)
                .setMaxResults(25);
        return list(query);
    }

    public boolean hasAlreadyBeenUploaded(byte[] hash) {
        Query query = currentSession().createQuery("FROM Image AS image WHERE image.hash = :hash")
                .setParameter("hash", hash)
                .setMaxResults(1);
        return list(query).size() != 0;
    }

    public Image save(Image image) {
        return persist(image);
    }
}

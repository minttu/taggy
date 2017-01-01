package me.pieso.taggy;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.pieso.taggy.daos.ImageDAO;
import me.pieso.taggy.daos.TagDAO;
import me.pieso.taggy.models.Image;
import me.pieso.taggy.models.Tag;
import me.pieso.taggy.resources.ImageResource;
import me.pieso.taggy.resources.TagResource;
import me.pieso.taggy.services.ImageService;

public class TaggyApplication extends Application<TaggyConfiguration> {
    private final HibernateBundle<TaggyConfiguration> hibernateBundle = new HibernateBundle<TaggyConfiguration>(Image.class, Tag.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TaggyConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }

        @Override
        protected Hibernate5Module createHibernate5Module() {
            return super.createHibernate5Module().disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        }
    };
    private final MigrationsBundle<TaggyConfiguration> migrationsBundle = new MigrationsBundle<TaggyConfiguration>() {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(TaggyConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String... args) throws Exception {
        new TaggyApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<TaggyConfiguration> bootstrap) {
        bootstrap.addBundle(migrationsBundle);
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(TaggyConfiguration configuration, Environment environment) throws Exception {
        final ImageDAO imageDAO = new ImageDAO(hibernateBundle.getSessionFactory());
        final TagDAO tagDAO = new TagDAO(hibernateBundle.getSessionFactory());
        final ImageService imageService = new ImageService(configuration.getFileStorageLocation());

        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        environment.jersey().register(new ImageResource(imageDAO, imageService));
        environment.jersey().register(new TagResource(tagDAO));
    }
}

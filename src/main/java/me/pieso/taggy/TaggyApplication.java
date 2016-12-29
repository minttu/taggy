package me.pieso.taggy;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.pieso.taggy.core.Image;
import me.pieso.taggy.core.Tag;
import me.pieso.taggy.db.ImageDAO;
import me.pieso.taggy.db.TagDAO;
import me.pieso.taggy.resources.ImageResource;
import me.pieso.taggy.resources.TagResource;

public class TaggyApplication extends Application<TaggyConfiguration> {
    private final HibernateBundle<TaggyConfiguration> hibernateBundle = new HibernateBundle<TaggyConfiguration>(Image.class, Tag.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TaggyConfiguration configuration) {
            return configuration.getDataSourceFactory();
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
    }

    @Override
    public void run(TaggyConfiguration configuration, Environment environment) throws Exception {
        final ImageDAO imageDAO = new ImageDAO(hibernateBundle.getSessionFactory());
        final TagDAO tagDAO = new TagDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new ImageResource(imageDAO));
        environment.jersey().register(new TagResource(tagDAO));
    }
}

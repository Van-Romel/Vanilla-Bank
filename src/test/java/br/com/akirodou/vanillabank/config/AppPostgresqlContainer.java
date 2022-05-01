package br.com.akirodou.vanillabank.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class AppPostgresqlContainer extends PostgreSQLContainer<AppPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:13.6-alpine";
    private static AppPostgresqlContainer container;

    private AppPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static AppPostgresqlContainer getInstance() {
        if (container == null) {
            container = new AppPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
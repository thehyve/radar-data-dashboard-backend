# Run for development

The following will run a standalone Postgres database. Data dashboard can be run with a config that connects to this database.

```shell
cd data-dashboard-backend/dev
docker-compose -f docker-compose-postgres.yml up -d
```

And then run your IDE while passing data-dashboard-backend/dev/dashboard.yml as argument to the application.


FROM postgres:14.2-alpine
COPY init-db.sh /docker-entrypoint-initdb.d/
HEALTHCHECK --interval=10s --timeout=10s --retries=10 CMD psql -c 'select 1' -d postgres -U postgres
EXPOSE 5432
LABEL authors="Ahmed Marzook, Beld"
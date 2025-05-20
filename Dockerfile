FROM postgres:13.2-alpine
ENV POSTGRES_DB forecast_db
ENV POSTGRES_USER myuser
ENV POSTGRES_PASSWORD 11111

# docker run --name forecast_db -e POSTGRES_DB=forecast_db -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=11111 -p 5432:5432 -d postgres
# 489de088766f55bb235503a9f38f8d7b0290fa2c4490aee290677a54c2d0932e
# docker exec -it forecast_db psql -U myuser forecast_db
#  запуск..интерактивный режим..имя_контейнера..имя_бд..имя_user..название_бд_в_контейнере

# \dt
# \q

# CREATE TABLE weather (
#   id SERIAL PRIMARY KEY,
#   city VARCHAR(255) NOT NULL,
#   description VARCHAR(255) NOT NULL,
#   temperature INT NOT NULL,
#   time DATE NOT NULL);

# DROP TABLE weather;

# select * from weather;

# SELECT MAX(id) FROM weather;
DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls CASCADE;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE url_checks (
    id SERIAL PRIMARY KEY,
    url_id INTEGER NOT NULL,
    status_code INT NOT NULL,
    h1 VARCHAR(5000),
    title VARCHAR(5000),
    description VARCHAR(5000),
    created_at TIMESTAMP,
    CONSTRAINT url_check_url_id_fkey FOREIGN KEY (url_id) REFERENCES urls(id) ON DELETE CASCADE
);
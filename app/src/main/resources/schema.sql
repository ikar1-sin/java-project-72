ALTER TABLE url_checks DROP CONSTRAINT IF EXISTS url_id;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE url_checks (
    id SERIAL PRIMARY KEY,
    url_id INTEGER NOT NULL REFERENCES urls(id) ON DELETE CASCADE,
    status_code INT NOT NULL,
    h1 VARCHAR(5000),
    title VARCHAR(5000),
    description VARCHAR(5000),
    created_at TIMESTAMP
);
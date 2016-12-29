--liquibase formatted sql

--changeset minttu:1

CREATE TABLE image (
  id SERIAL PRIMARY KEY,
  created TIMESTAMP DEFAULT now(),
  hash BYTEA UNIQUE
);
--rollback DROP TABLE image;

CREATE INDEX image_hash_idx ON image(hash);
--rollback DROP INDEX image_hash_idx;

CREATE TABLE tag (
  name VARCHAR(128) PRIMARY KEY
);
--rollback DROP TABLE tag;

CREATE TABLE image_tag (
  image_id INT NOT NULL REFERENCES image(id),
  tag_name VARCHAR(128) NOT NULL REFERENCES tag(name),
  PRIMARY KEY (image_id, tag_name)
);
--rollback DROP TABLE image_tag;

CREATE INDEX tag_image_idx ON image_tag(tag_name);
--rollback DROP INDEX tag_image_idx;
DROP TABLE IF EXISTS recipes;

CREATE TABLE IF NOT EXISTS recipes(
    id serial PRIMARY KEY,
    title varchar(100) NOT NULL,
    making_time varchar(100) NOT NULL,
    serves varchar(100) NOT NULL,
    ingredients varchar(300) NOT NULL,
    cost integer NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP FUNCTION set_update_time();

create function set_update_time() returns opaque as '
  begin
    new.updated_at := ''now'';
    return new;
  end;
' language 'plpgsql';

create trigger update_tri before update on posts for each row
  execute procedure set_update_time();
  
  
INSERT INTO recipes (
  id,
  title,
  making_time,
  serves,
  ingredients,
  cost,
  created_at,
  updated_at
)
VALUES (
  1,
  'チキンカレー',
  '45分',
  '4人',
  '玉ねぎ,肉,スパイス',
  1000,
  '2016-01-10 12:10:12',
  '2016-01-10 12:10:12'
);

INSERT INTO recipes (
  id,
  title,
  making_time,
  serves,
  ingredients,
  cost,
  created_at,
  updated_at
)
VALUES (
  2,
  'オムライス',
  '30分',
  '2人',
  '玉ねぎ,卵,スパイス,醤油',
  700,
  '2016-01-11 13:10:12',
  '2016-01-11 13:10:12'
);


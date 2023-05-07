DROP TABLE IF EXISTS ball_order; 
DROP TABLE IF EXISTS orders; 
DROP TABLE IF EXISTS ball;  
DROP TABLE IF EXISTS customer; 

CREATE TABLE customer (
  customer_pk int unsigned NOT NULL AUTO_INCREMENT,
  customer_id varchar(40) NOT NULL,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  phone varchar(40) NOT NULL,
  PRIMARY KEY (customer_pk)
);


CREATE TABLE ball (
  ball_pk int unsigned NOT NULL AUTO_INCREMENT, 
  ball_id enum('ADIDAS_FINALE_MADRID', 'ADIDAS_FINALE_19', 'ADIDAS_FINALE_ISTANBUL', 
  'ADIDAS_FINALE_20', 'ADIDAS_FINALE_21', 'ADIDAS_FINALE_PETERSBURG', 'ADIDAS_FINALE_PEACE', 'ADIDAS_FINALE_22', 'JABULANI',
  'JOBULANI', 'BRAZUCA', 'BRAZUCA_FINAL_RIO', 'TELSTAR_18', 'TELSTAR_18_MECHTA', 'AL_RIHLA', 'AL_HILM'), 
  ball_size enum( 'SIZE3', 'SIZE4', 'SIZE5'),
  ball_year varchar(40) NOT NULL, 
  price decimal(9,2) NOT NULL, 
  PRIMARY KEY (ball_pk)
);

 CREATE TABLE orders (
  order_pk int unsigned NOT NULL AUTO_INCREMENT,
  customer_fk int unsigned NOT NULL,
  ball_fk int unsigned NOT NULL,
  total decimal (9,2) NOT NULL,
  PRIMARY KEY (order_pk),
  FOREIGN KEY (customer_fk) REFERENCES customer (customer_pk) ON DELETE CASCADE,
  FOREIGN KEY (ball_fk) REFERENCES ball (ball_pk) ON DELETE CASCADE
);


CREATE TABLE ball_order (
 ball_fk int unsigned NOT NULL,
 order_fk int unsigned NOT NULL,
 FOREIGN KEY (ball_fk) REFERENCES ball (ball_pk) ON DELETE CASCADE,
 FOREIGN KEY (order_fk) REFERENCES orders (order_pk) ON DELETE CASCADE
);
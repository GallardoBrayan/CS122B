DROP PROCEDURE IF EXISTS add_movie;

DELIMITER //
CREATE PROCEDURE add_movie
(IN title varchar(100),
IN year int(11),
IN director varchar(100),
IN banner_url varchar(200), 
IN trailer varchar(200),
IN star_first_name VARCHAR(50),
IN star_last_name VARCHAR(50),
IN star_dob DATE,
IN star_photo_url VARCHAR(200),
IN genre VARCHAR(200),
OUT responseMsg VARCHAR(200)
)
BEGIN
	DECLARE Exist INT DEFAULT 0;
	DECLARE MovieID INT DEFAULT 0 ;
	DECLARE star_id INT DEFAULT 0 ;
	DECLARE genre_id INT DEFAULT 0 ;
	
    SELECT movies.id, count(*)
	INTO MovieID, Exist
	FROM movies 
	WHERE  movies.title = title AND movies.year = year AND movies.director = director;

	proc_label:BEGIN
		IF Exist > 0 THEN
			SET @responseMsg = 'Movie not added, already exists.';
		ELSE
			SET @responseMsg = 'Movie added. ';
			INSERT INTO movies (title, year, director,banner_url, trailer) 
			VALUES (title, year, director,banner_url, trailer);
			SET MovieID = LAST_INSERT_ID();
		END IF;

		SELECT star_id, count(*) 
		INTO star_id, Exist 
		FROM stars 
		WHERE  stars.first_name = star_first_name AND stars.last_name = star_last_name AND stars.dob = star_dob;

		IF Exist = 0  And star_last_name != ""THEN
			INSERT INTO stars (first_name, last_name, dob,photo_url) VALUES (star_first_name, star_last_name, star_dob, star_photo_url);
			SET star_id = LAST_INSERT_ID();
			SET @responseMsg = concat(@responseMsg , 'Star was not found and was created. ') ;
		END IF;
        
        IF genre != "" THEN
			SELECT id, count(*) 
			INTO genre_id, Exist 
			FROM genres 
			WHERE genres.name = genre;
		END IF;

		IF Exist = 0  AND genre != "" THEN
			INSERT INTO genres (name) VALUES (genre);
			SET genre_id = LAST_INSERT_ID();
			SET @responseMsg = concat(@responseMsg , 'Genre was not found and was created. ') ;
		END IF;
        
        If genre_id != 0 THEN
			INSERT INTO genres_in_movies (genres_id, movies_id) VALUES (genre_id, MovieID);
        END IF;
        
        IF star_id != 0 THEN
			INSERT INTO stars_in_movies (star_id, movies_id) VALUES (star_id, MovieID);
        END IF;
	END;
    SET responseMsg = @responseMsg;
END //
DELIMITER ;
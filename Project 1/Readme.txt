Part one:
	Create db "moviedb":
		mysql -u root -proot
		Create Database moviedb;
	Create tables:
		cd Project\ 1/sql\ scripts
		mysql -u root -proot -D moviedb <  createtable.sql
	Populate data
		mysql -u root -proot -D moviedb < data.sql
Part 2(Linux):
	To compile on Linux machine:
		cd Project\ 1
		make
	To compile and run :
		cd Project\ 1
		make run
Part 2(Window):	
	To compile on WindowS machine:
		javac -cp ./;./bin/;./lib/mysql-connector-java-5.0.8-bin.jar -g -d ./bin dbFunctions.java
		javac -cp ./;./bin/;./lib/mysql-connector-java-5.0.8-bin.jar -g -d ./bin p1t2.java
	To run 
		java -cp ./;./bin/;./lib/mysql-connector-java-5.0.8-bin.jar p1t2
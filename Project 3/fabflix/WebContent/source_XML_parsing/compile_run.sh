javac fabflix/*.java  -d ./classes/ -cp .;./lib/*;./classes/*;./lib/mysql-connector-java-5.0.8-bin.jar
javac ./*.java  -d ./ -cp .;./lib/*;./classes/*;./lib/mysql-connector-java-5.0.8-bin.jar
java  -cp .;./lib/*;./classes/*;./lib/mysql-connector-java-5.0.8-bin.jar Parser_Main
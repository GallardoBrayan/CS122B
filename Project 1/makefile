JC = javac
JVM = java
CLASSPATH = ./:./bin/:./lib/mysql-connector-java-5.0.8-bin.jar
JFLAGS = -cp $(CLASSPATH) -g -d ./bin
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	dbFunctions.java \
	p1t2.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) -cp $(CLASSPATH) p1t2

clean: $(RM) *.class

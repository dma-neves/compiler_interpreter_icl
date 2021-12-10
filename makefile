SOURCE = source_1

all: clean compile
	
run:
	java ICLCompiler source_code/$(SOURCE).icl
	java $(SOURCE)

compile:
	javacc Parser0.jj
	javac ICLInterpreter.java
	javac ICLCompiler.java

clean:
	-rm *Parse*.java
	-rm *Token*.java
	# -rm ICLInterpreterConstants.java
	-rm SimpleCharStream.java
	# -rm *.class
	-find . -name "*.class" -type f -delete
	-rm *.j

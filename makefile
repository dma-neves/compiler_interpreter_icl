SOURCE = source_2

all: clean compile run
	
run:
	java ICLCompiler source_code/$(SOURCE).icl
	java $(SOURCE)

compile:
	javacc Parser0.jj
	javac ICLInterpreter.java
	javac ICLCompiler.java

clean:
	-rm Parse*.java
	-rm *Token*.java
	-# rm ICLInterpreterConstants.java
	-rm SimpleCharStream.java
	-rm *.class
	-rm *.j

SOURCE = fun2

all: clean compile
	
runcompiler:
	java ICLCompiler ../programs/$(SOURCE).icl
	java $(SOURCE)

compile:
	javacc ICLParser.jj
	javac ICLInterpreter.java
	javac ICLCompiler.java

clean:
	-rm *Parse*.java
	-rm *Token*.java
	-rm SimpleCharStream.java
	-find . -name "*.class" -type f -delete
	-rm *.j

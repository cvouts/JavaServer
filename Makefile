all: clean compile run

compile:
	@echo "Compiling..."
	@javac webserver/*.java 

run:
	@echo "Starting..."
	@java webserver.ServerTest

clean:
	@echo "Cleaning..."
	@find . -iname *.swp -delete
	@find . -iname *.swo -delete
	@find . -iname *.class -delete

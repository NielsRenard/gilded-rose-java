ROOT = $(dir $(realpath $(firstword $(MAKEFILE_LIST))))

clean:
	@mvn clean

build:
	@mvn package

test:
	@mvn test
go:
	@java -jar ./target/gilded-rose-kata-0.0.1-SNAPSHOT-jar-with-dependencies.jar

run: build go

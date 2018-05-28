## Read/Write Routing Data Source

This project aims to exemplify Springs ability to seperate read and write operations to your primary and replica databases.


## Read the blog

A full blog post delving into details of this project is available for reading [here](https://fable.sh/blog/splitting-read-and-write-operations-in-spring-boot/).

## Usage

In order to use this project, you'll need Docker and Java 8 installed.

Simply `docker-compose up` and then `./gradlew bootRun`. You should see logs of `fable_write` inserting data, and `fable_read` then reading. Easy!

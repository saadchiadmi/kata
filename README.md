
# Spring Batch - Kata Issue

## Description
The purpose of this application is to transform a number or a list of numbers (ranging from 0 to 100) into a string. The algorithm follows these rules:
- If the number is divisible by 3 or contains 3, the resulting string must include "FOO".
- If the number is divisible by 5 or contains 5, the resulting string must include "BAR".
- If the number contains 7, the resulting string must include "QUIX".
- The "divisible by" rule takes precedence over the "contains" rule.
- The content is analyzed from left to right.
- If none of the rules apply, return the input number as a string.

## Prerequisites
To run this project, you need to have the following installed:
- Java 21
- Maven
## Usage
- 1 Place your input file in the resources/input/input.txt directory. The input file should contain one number per line
- 2 Run the application
- 3 The batch job will be executed every 5 minutes, processing the numbers from the input file and writing the results to resources/output/output.txt.
## Endpoints
The application exposes the following endpoints:
- GET /api/batch/run: Manually triggers the batch job.
- GET /transform/{number}: Transforms the number passed as a parameter according to the specified rules.
## Docker Deployment
To deploy the application using Docker, follow these steps:
- Build the Docker image:
docker build -t kata .
- Run the Docker container:
docker run -p 8080:8080 kata
- -The application will be accessible at http://localhost:8080/api/batch/run to manually trigger the batch job.
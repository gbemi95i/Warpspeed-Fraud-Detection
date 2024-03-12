# Fraud Detection System

This is a prototype of a real-time fraud detection system that analyzes transactions to identify potential fraudulent patterns.

## Prerequisites

Before running the Fraud Detection System, ensure you have the following installed:

- Java Development Kit (JDK) 11 or later
- Apache Maven

## Compile with Maven

To compile the Fraud Detection System with Maven, follow these steps:

1. Navigate to the root directory of the project.
2. Run the following Maven command:

    ```
    mvn clean package
    ```

   This command will compile the source code, run tests (if any), and package the application into an executable JAR file.

## Run with Java

To run the Fraud Detection System with Java from the command line, follow these steps:

1. Ensure you have compiled the project using Maven as described above.
2. Navigate to the `target` directory within the root directory of the project.
3. Run the following command to execute the JAR file:

    ```
    java -jar Warp-1.0-SNAPSHOT.jar
    ```

   Replace `Warp-1.0-SNAPSHOT.jar` with the actual name of the generated JAR file.

## Usage

Upon running the Fraud Detection System, it will analyze a stream of transactions and identify fraudulent patterns based on predefined rules. The system will output any detected fraudulent activity to the console.

## Configuration

You can configure the Fraud Detection System by modifying the source code to adjust the detection rules or by providing input data in the desired format.

Note that there are 2 datasets available in the src/resouces folder designed to demonstrate both small and large amounts of transactions. You can configure file to process in the config file (application.properties) before building the project.

## Assumptions

- The transactions are well-formed.
- This is just a prototype.

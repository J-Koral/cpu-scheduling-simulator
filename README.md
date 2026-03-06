# CPU Scheduling Simulator
This project is a CPU Scheduling Algorithm Simulator web application that helps students visualize how different 
scheduling algorithms work. It will allow the user to input any number of processes with their desired arrival 
time and burst time. The user can then select an algorithm they wish to use such as FCFS, SJF, SRTF, RR, and Priority. 
The goal is to make an easy way for anyone interested to visualize and play around with different CPU scheduling 
algorithms so they can strengthen their understanding of the topic.
## Important Requirements

This project uses ***Java 25***, so you need to have it installed on your computer.

You can check your version of java by running:
java -version

## How to Run the Project
1. Open a terminal in the project folder. Go to the main project directory (cpu-scheduling-simulator).
2. Start the Spring Boot backend by running thre command: .\mvnw.cmd spring-boot:run
3. Open the frontend. Open the index.html file in your browser. It is located at: src/main/resources/static/index.html


You can now enter processes and run the FCFS scheduling simulation.

## How to Stop the Program
If the backend is running in your terminal, you can stop it by pressing: Ctrl + C

This will shut down the Spring Boot server.

## Technologies Used
- Java 25
- Spring Boot
- Maven
- HTML / CSS / JavaScript

## Notes
  Right now the project supports FCFS scheduling. I plan to add more algorithms later like SJF, SRTF, Round Robin, and Priority.



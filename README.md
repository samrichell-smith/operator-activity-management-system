# Operator Activity Management System (CLI Application)

This project is a Java-based command-line system designed to manage a catalogue of operators, their associated activities, and a structured review system. It simulates the backend logic behind a service platform such as a tourism aggregator or event booking system, allowing users to create and query operators, add and review activities, and handle feedback workflows through a clean CLI.

The entire system runs locally and is built using standard Java, compiled with Maven Wrapper, and tested using JUnit.

---

## System Overview

The application manages three core entities:

- **Operators**: Representing service providers, each with a name and location.
- **Activities**: Each operator can offer multiple activities, such as "Skydiving" or "Guided Tour", each tagged with a type.
- **Reviews**: Users can leave structured feedback on activities. Three types of reviews are supported:
  - **Public Reviews** (with optional anonymity)
  - **Private Reviews** (internal-only, with email and follow-up flags)
  - **Expert Reviews** (with recommendations and optional image uploads)

Each entity is fully managed through a terminal-based interface that accepts commands and arguments. The system uses clear validation logic and prompts for missing data interactively.

---

## Key Features

- **Command-based Interface**  
  Users interact with the system entirely through typed commands. Each function — from searching operators to resolving reviews — is accessible via a well-documented CLI.

- **Realistic Review System**  
  The review types are designed to reflect real-world use cases, with distinct data fields and workflows:
  - Private reviews can be resolved
  - Public reviews can be endorsed by others
  - Expert reviews can include an image 

- **Search and Ranking**  
  Activities and operators can be searched via keywords, and the system can output the top-rated activity in each location based on review data.


---

## Screenshot Previews

**Startup Command Menu**
Displays the full list of supported commands when the program launches:

![CLI Commands Screenshot](/OperatorSystemCommandList.png)


**CLI In Use**
Demonstrates a user creating an activity and submitting a review:

![CLI Interaction Screenshot](/OperatorSystemExample.png)

---

## Tests and Quality Assurance

The project includes a set of unit tests written using **JUnit**, covering:

- Core logic for creating and retrieving operators and activities
- Review submission and validation workflows
- Command parsing and argument handling
- Endorsement, resolution, and ranking logic

Tests are structured to verify correctness and handle edge cases, ensuring robust CLI behaviour under various input scenarios.

---

## Technology Stack

- **Language**: Java
- **Build Tool**: Maven Wrapper (`mvnw.cmd`)
- **Testing**: JUnit
- **CLI I/O**: Standard System.in / System.out input handling
- **Design Principles**: Object-Oriented Programming, separation of concerns, and extensibility

---

## How to Run


```bash
.\mvnw.cmd compile exec:java@run

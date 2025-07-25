# DNA Sequence Alignment Web Application

## Introduction
This project is a web-based application designed for aligning user-submitted DNA sequences (comprising 'A', 'T', 'G', and 'C') against a predefined set of protein sequences from a list of NCBI assemblies. The application displays detailed alignment results and maintains a persistent history of query submissions specific to each user's machine. The supported assemblies are:
- NC_000852
- NC_007346
- NC_008724
- NC_009899
- NC_014637
- NC_020104
- NC_023423
- NC_016072
- NC_023719
- NC_027867

## Features

- **DNA Sequence Submission**: Users can input DNA sequences via a web interface.
- **Alignment Search**: The application searches for the first exact match of the input sequence within the coding sequences of the listed assemblies.
- **Persistent Query History**: Query results are stored locally and persist across browser sessions. Pending queries are re-triggered upon browser restart.
- **Clear Results Option**: Users can clear the history of query results.
- **Local Deployment**: Supports both Docker-based and manual deployment for local development.

## Technology Stack

- **Backend**: Java 8 with Spring Boot
- **Frontend**: Angular 9
- **Build & Deployment**: Docker and Docker Compose for containerized deployment
- **Data Source**: FASTA Nucleotide files for coding sequences, sourced from NCBI Nucleotide (stored in `alignment-service/resources/coding-sequences`)

## Installation and Deployment

### Prerequisites

- **With Docker**: Install Docker and Docker Compose.
- **Without Docker**: Install Java 8, Angular 9, and npm.

### With Docker

1. Clone the repository:
   ```bash
   git clone https://github.com/dami-gupta-git/dna-sequence-alignment.git
   ```
2. Navigate to the project root (where `docker-compose.yml` is located).
3. Build and run the application:
   ```bash
   docker-compose up --build
   ```
   For subsequent runs (without rebuilding):
   ```bash
   docker-compose up
   ```
4. Open a browser and navigate to [http://localhost:9898](http://localhost:9898).

### Without Docker

1. **Frontend Setup**:
   - Navigate to the `alignment-ui` directory:
     ```bash
     cd alignment-ui
     ```
   - Install dependencies and serve the frontend:
     ```bash
     npm install
     ng serve
     ```
2. **Backend Setup**:
   - Navigate to the `alignment-service` directory.
   - Open the project in an IDE and run `mvn clean install`.
   - Start the backend by running the `Application.java` class.
   - Alternatively, use the command line:
     ```bash
     cd alignment-service
     mvn spring-boot:run
     ```
3. Open a browser and navigate to [http://localhost:4200](http://localhost:4200).

## Usage

1. **Submit a Query**:
   - Enter a DNA sequence (e.g., "ATGC") in the input box at the top of the web interface.
2. **View Results**:
   - The "Sequence Match Results" section displays all submitted queries and their alignment details (if a match is found).
   - Results are ordered with the most recent at the top.
3. **Clear History**:
   - Click the "Clear Results" button to remove all stored query results.
4. **Alignment Behavior**:
   - The system identifies only the first exact match for a given sequence; subsequent matches are ignored.

## Implementation Details

- **Data Source**: Coding sequences for the listed assemblies were downloaded from NCBI Nucleotide (e.g., [NC_027867](https://www.ncbi.nlm.nih.gov/nuccore/NC_027867)) in FASTA Nucleotide format.
- **Storage**: Sequence files are stored in `alignment-service/resources/coding-sequences`.
- **Testing**: Basic unit and integration tests are implemented for the backend. Frontend tests are pending.
- **Persistence**: Query history is stored locally on the user's machine and persists across browser sessions.

## Potential Improvements

- **Enhanced Testing**: Add comprehensive unit and integration tests for both backend and frontend.
- **Advanced Matching**: Replace exact substring matching with a more sophisticated alignment algorithm (e.g., BLAST or Smith-Waterman).
- **Data Integration**: Fetch data dynamically via NCBI REST APIs or nightly downloads, potentially storing in an in-memory database like H2.
- **Error Handling**: Implement robust exception handling and logging mechanisms.
- **Performance Optimization**: Optimize search performance, especially for large sequences or frequent queries.
- **User Experience**: Enhance the UI with better visualizations, pagination for results, and support for partial matches.

## Known Limitations

- The application currently performs exact substring matching, which may not suit all use cases.
- No frontend unit tests are implemented.
- Limited exception handling and logging.
- NCBI data is stored locally rather than fetched dynamically, which may become outdated.

## Screenshot
<img width="841" height="384" alt="image" src="https://github.com/user-attachments/assets/a3b1b2ee-3a75-49d1-b439-4ef8aaf9204e" />

 

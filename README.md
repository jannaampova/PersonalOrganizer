# Personal Organizer

A full-stack web application designed to help users organize, manage, and track their personal information efficiently. Built with Java backend, HTML/CSS frontend, and powered by Maven.

## Overview

Personal Organizer is a practical tool for managing daily tasks, notes, and personal data. The project combines a robust Java backend with a clean, responsive web interface to provide an intuitive user experience.

## Technology Stack

- **Backend**: Java
- **Frontend**: HTML, CSS
- **Build Tool**: Maven
- **Project Type**: Web Application

### Language Composition
- HTML: 38%
- Java: 34%
- CSS: 28%

## Features

- ✓ Organize and manage personal information
- ✓ Task and note management
- ✓ Data persistence and retrieval
- ✓ Clean, user-friendly web interface
- ✓ Export functionality (Excel format support)
- ✓ Responsive design across devices

## Project Structure

```
PersonalOrganizer/
├── src/                           # Source code
│   ├── main/java/                # Java backend code
│   ├── main/resources/           # Configuration files
│   └── main/webapp/              # Web assets (HTML, CSS)
├── pom.xml                        # Maven Project Object Model
├── mvnw                           # Maven wrapper for Linux/Mac
├── mvnw.cmd                       # Maven wrapper for Windows
├── exports.xlsx                   # Sample export file
├── .mvn/                          # Maven wrapper configuration
├── .gitignore                     # Git ignore rules
├── .gitattributes                 # Git attributes
└── README.md                      # This file
```

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 11 or higher
- **Maven**: 3.6+ (or use the included Maven wrapper)
- **Web Browser**: Modern browser (Chrome, Firefox, Safari, Edge)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/tstoycheva93/PersonalOrganizer.git
   cd PersonalOrganizer
   ```

2. **Build the project**:
   ```bash
   # On Linux/Mac
   ./mvnw clean install

   # On Windows
   mvnw.cmd clean install
   ```

3. **Run the application**:
   ```bash
   # On Linux/Mac
   ./mvnw spring-boot:run

   # On Windows
   mvnw.cmd spring-boot:run
   ```

4. **Access the application**:
   Open your web browser and navigate to `http://localhost:8080` (or the configured port)

## Usage

Once the application is running, you can:

### Core Features
- **Create Tasks**: Add new personal tasks with descriptions and due dates
- **Manage Notes**: Organize and store important notes
- **Track Progress**: Monitor task completion and progress
- **Export Data**: Export your organized information to Excel format (`.xlsx`)
- **Search & Filter**: Easily find tasks and notes using search functionality
- **Edit & Delete**: Modify or remove tasks and notes as needed

### User Interface
The application provides a clean, intuitive web interface with:
- Easy navigation menus
- Quick access to main features
- Responsive design for desktop and tablet use
- Clear visual organization of information

## API Endpoints

The application exposes RESTful endpoints for managing personal data. Common operations include:
- Creating, reading, updating, and deleting tasks
- Managing notes and reminders
- Exporting data to various formats

## Building from Source

### Build Commands

```bash
# Clean and build the project
./mvnw clean install

# Run tests
./mvnw test

# Build without running tests
./mvnw clean install -DskipTests

# Run the application directly
./mvnw spring-boot:run

# Package the application
./mvnw package
```

## Configuration

Configuration files are located in `src/main/resources/`. You can customize:
- Application port (default: 8080)
- Database settings
- Logging levels
- Other application properties

## Data Export

The application supports exporting your organized data to Excel format:
1. Navigate to the export section
2. Select the data you want to export
3. Download the `.xlsx` file to your computer

## Project Information

- **Repository**: [tstoycheva93/PersonalOrganizer](https://github.com/tstoycheva93/PersonalOrganizer)
- **Owner**: [tstoycheva93](https://github.com/tstoycheva93)
- **Repository ID**: 938109588
- **Created**: February 24, 2025
- **Last Updated**: May 11, 2025
- **Visibility**: Public
- **License**: Not specified

## Contributing

Contributions are welcome and appreciated! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/AmazingFeature`
3. **Commit your changes**: `git commit -m 'Add some AmazingFeature'`
4. **Push to the branch**: `git push origin feature/AmazingFeature`
5. **Open a Pull Request**

Please ensure your code follows the existing style and includes appropriate comments.

## Development

### Local Development Setup

```bash
# Clone the repository
git clone https://github.com/tstoycheva93/PersonalOrganizer.git
cd PersonalOrganizer

# Install dependencies
./mvnw clean install

# Start development server
./mvnw spring-boot:run

# Access at http://localhost:8080
```

## License

This project is currently unlicensed. For licensing inquiries, please contact the repository owner.

# Project Overview: Feature List, Goals, and Testing Strategy

## Project Goals
The primary goals of this project are to create a robust and scalable platform with the following objectives:
1. **User Authentication**: Implement a login system with username and password.
2. **Video Interaction**: Enable users to interact with video content, such as scrolling and liking videos.
3. **Backend Infrastructure**: Build a reliable backend with well-defined API endpoints and a properly structured database.
4. **Frontend Development**: Develop a user-friendly and intuitive UI for the application.
5. **Pipeline Optimization**: Optimize the development pipeline for efficiency and reliability.

---

## Feature List

### Backlog (Not Started)
1. **Test Strategy Document**  
   *Task*: Create a comprehensive test strategy document.  
   *ID*: GTEE-MAX #7  

2. **User Login System**  
   *Task*: Implement login functionality using username and password.  
   *ID*: GTEE-MAX #8  

3. **Video Scrolling**  
   *Task*: Enable smooth scrolling between videos.  
   *ID*: GTEE-MAX #9  

4. **Video Liking Feature**  
   *Task*: Allow users to like videos as an additional feature.  
   *ID*: GTEE-MAX #10  

5. **Frontend Pipeline Optimization**  
   *Task*: Use `npm ci` instead of `npm i` for improved build performance.  
   *ID*: GTEE-MAX #18  

---

### In Progress
1. **API Endpoints**  
   *Task*: Create backend endpoints for the API.  
   *ID*: GTEE-MAX #12  

2. **Basic UI Development**  
   *Task*: Build the basic structure and design of the frontend UI.  
   *ID*: GTEE-MAX #16  

3. **Documentation**  
   *Task*: Write documentation for the feature list and project overview.  
   *ID*: GTEE-MAX #17  

---

### In Review
1. **JPA Entities**  
   *Task*: Create JPA entities for the backend.  
   *ID*: GTEE-MAX #13  

---

### Completed
1. **Database Schema Definition**  
   *Task*: Define the schema for the Postgres database.  
   *ID*: GTEE-MAX #11  

2. **Backend Pipeline Update**  
   *Task*: Update the backend stage in the CI/CD pipeline.  
   *ID*: GTEE-MAX #6  

---

## Testing Strategy

To ensure the quality and reliability of the application, we will include testing strategys that includes the following types of tests:

### 1. **Unit Testing**
   - **Scope**: Test individual components, functions, and methods in isolation.
   - **Tools**: 
     - Backend: Use **JUnit** for testing Java-based backend components.
     - Frontend: Use **Jest** for testing React components and JavaScript functions.
   - **Objective**: Ensure that each unit of the application behaves as expected.

### 2. **Integration Testing**
   - **Scope**: Test the interaction between different modules (e.g., frontend-backend communication, database queries).
   - **Tools**: 
     - Use **Postman** or **REST Assured** for API testing.
     - Mock dependencies where necessary.
   - **Objective**: Verify that modules work together seamlessly.

### 3. **End-to-End (E2E) Testing**
   - **Scope**: Test the entire application workflow from the user's perspective.
   - **Tools**: Use **Cypress** or **Selenium** for automated browser testing.
   - **Objective**: Ensure that the user experience is smooth and that all features function as intended.

### 4. **Security Testing**
   - **Scope**: Test for vulnerabilities such as SQL injection, XSS, and authentication flaws.
   - **Tools**: Use **OWASP ZAP** or **Burp Suite** for security scanning.
   - **Objective**: Ensure that the application is secure against common threats.

### 5. **Manual Testing**
   - **Scope**: Perform exploratory testing to identify edge cases and usability issues.
   - **Objective**: Catch any issues that automated tests may miss and ensure the application meets user expectations.

---

## Summary
This project is structured to ensure seamless collaboration between backend and frontend teams while maintaining a focus on delivering a high-quality user experience. By following the feature list, adhering to the project goals, and implementing a robust testing strategy, we aim to build a functional and scalable platform that meets user needs and technical standards.

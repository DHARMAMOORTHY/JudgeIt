# JudgeIt: Results and Submission History

This feature adds persistence and Thymeleaf pages for saving code submissions, viewing a result record, and browsing submission history.

## Files added

- `src/main/java/com/judgeit/judgeit/Submission.java`
- `src/main/java/com/judgeit/judgeit/SubmissionRepository.java`
- `src/main/java/com/judgeit/judgeit/SubmissionService.java`
- `src/main/java/com/judgeit/judgeit/SubmissionController.java`
- `src/main/resources/templates/submit-code.html`
- `src/main/resources/templates/submissions.html`
- `src/main/resources/templates/submission-result.html`

No `pom.xml` changes should be needed: the repository already has Spring Web MVC, Thymeleaf, Spring Data JPA, and validation dependencies. The existing `spring.jpa.hibernate.ddl-auto=update` setting should create the `submissions` table on startup.

## Routes

- `GET /submissions` — submission history
- `GET /submissions/new` — open code submission form
- `GET /submissions/new?problemId=1` — open the form with a problem preselected
- `POST /submissions` — save a submission
- `GET /submissions/{id}` — view the submission result and source code

## Important scope note

This feature **stores** source code and records the verdict as `PENDING`. It does not compile or execute submitted code, run test cases, or calculate a score. Do not label a submission Accepted/Rejected until a secure, isolated judge runner is implemented. Never execute user-submitted code directly inside the Spring Boot application process.

## Run and verify

1. Ensure MySQL and the local database configuration are set up as described in the main README.
2. Run `.\mvnw.cmd clean package`.
3. Run `.\mvnw.cmd spring-boot:run`.
4. Open `http://localhost:8081/problems`.
5. Open `http://localhost:8081/submissions/new`, save a sample submission, and check the result page.
6. Open `http://localhost:8081/submissions` and verify the new record appears.

## Limitations

The current project does not expose an authenticated user relationship in the problem-management module, so this first version displays the repository's saved submissions as one shared history. Add user authentication and ownership filtering before using this with multiple real users.

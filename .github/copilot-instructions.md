- Always use ; (semicolon) on Windows terminals to separate commands (instead of &, && or others)
- Prefix the Gradle wrapper with .\
- Custom application properties should start with an `application.` prefix

- Keep specific application-wide annotation such as @EnableJpaAuditing in their own configuration classes
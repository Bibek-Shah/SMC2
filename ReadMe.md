npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --watch

# SMC2

SMC2 is a Spring Boot application that provides a simple contact management system.

## Features

- User authentication and authorization
- Contact creation, retrieval, update, and deletion
- Image upload and storage using Cloudinary

## Getting Started

To get started with SMC2, follow these steps:

1. Clone the repository:
   git clone https://github.com/your-username/smc2.git

2. Install the dependencies:
   cd smc2 mvn clean install

3. Configure the application:

- Create a `application.yml` file in the `src/main/resources` directory.
- Add the following configuration:

  ```yaml
  spring:
    datasource:
      url: jdbc:mysql://localhost:3306/smc2
      username: your-username
      password: your-password
    security:
      oauth2:
        client:
          registration:
            google:
              client-id: your-client-id
              client-secret: your-client-secret
            github:
              client-id: your-client-id
              client-secret: your-client-secret
  cloudinary:
    cloud_name: your-cloud-name
    api_key: your-api-key
    api_secret: your-api-secret
  ```

4. Start the application:
   mvn spring-boot:run

5. Access the application:

- Open a web browser and navigate to `http://localhost:8080`.
- Log in using your Google or Github account.

## Contributing

We welcome contributions to SMC2. To contribute, follow these steps:

1. Fork the repository.
2. Create a new branch for your changes.
3. Make your changes and commit them.
4. Push your changes to your forked repository.
5. Open a pull request.

## License

SMC2 is released under the MIT License. See the `LICENSE` file for details.

## Questions

If you have any questions, please open an issue or contact us
at [shahbibek2000@gmai.com](mailto:shahbibek2000@gmai.com).

# EA-FinalProject
Enterprise Architecture final project - MUM social network
- Technologies: Spring, OAuth2, JWT, RabbitMQ, MySQL, HTML, JQuery, JavaScript, RESTful webservices. 

#How to setting up application:
## Database
- Databases:
    - "web": use for main application (store business data). [Please update DB info in the backend module]
    - "rabbitMQ": use for notification service. [Please update DB info in the RabbitMQ-Consumer module]
    
## Start application:
- Open and start the RabbitMQ-Producer application (ProducerApplication.java)
- Open and start the RabbitMQ-Consumer aplication (ConsumerApplication.java)
- Open index.html at path "./EA-FinalProject/frontend/index.html".
    - User can register new account or use some existing account: bao@mum.edu/123456 or quy@mum.edu/123456

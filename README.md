# Mini Project Microservices

---
### Database
MongoDB is running in Atlas - https://account.mongodb.com/account/login  
Email - cph-mn492@cphbusiness.dk  
Password - systemintegration 
### Docker
Each service are containerized and deployed at Docker Hub at the following urls:

Eureka Server - https://hub.docker.com/r/cphmn492/eureka-server  
Car-service - https://hub.docker.com/r/cphmn492/carservice  
User-service - https://hub.docker.com/r/cphmn492/userservice  
Gateway - https://hub.docker.com/r/cphmn492/gateway 

### Kubernetes

Run the Kubernetes dashboard and deploy each service from the Docker repo.  In each project there is a configuration file for Kubernetes for deployment and for linking the services to the Eureka Server  

## Simple Bank server

Server app for 'Simple Bank' organization. Contains some features like:
* account information
* balance of the account with the operations history
* information about cards and executed operations with them
* information about loans which user have

Implementation leverages:
* Java 21
* Spring Framework 6.x
* Spring Security 6.x
* Spring Boot 3.4.1
* JWT 0.12.6
* Lombok 1.18.36
* Spring Security's OAuth2 resource server features
* Spring Data JPA
* Spring Data JDBC
* MySQL driver

Server can be run with 3 profiles (in case you do not specify the active profile, then marked as [default] will be selected):
* [default] `auth-and-resource-server` Spring backend plays here two roles:
  * authorization server - issuing and validating JSON Web Tokens (JWT)
  * resource server - providing resources
* `keycloak-auth-server` - Spring backend plays resource server role
  * Keycloak is the authorization server here (separate configuration of Keycloak is required beforehand)
* `spring-auth-server` - Spring backend plays resource server role
  * Spring Authorization Server is the authorization server. I used my [implementation](https://github.com/tof3r/authserver) of Spring Auth server  
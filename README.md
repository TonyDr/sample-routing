### It's a simple web app for assignment.

On startup app retrieve country list from remote data repository 

#### Web app contains:
   - REST services for retrieve countries 
    /api/countries - return all countries; 
    /api/countries?page=? - return page of country. page size is 5;
   
   - One Web page with list of countries available by path "/" or "/index"
![Image of page](https://github.com/TonyDr/sample-routing/content/sample-routing-countries.png)  
***
    
#### Implementation.     

 Application use Spring Boot.
 
 App contains three layers
 
 - Controllers - Who handle requests
 - Services - who may contain some business logic
 - Repositories - return some data   
 
 For security used Spring Security it configured for allow to use all "/api" and disallow  other.
 
 
 When implementing retrieving data from remote repository, I have a problem with certification.
 To resolve this problem I create a  custom keystore witch used for create connection.
 
 
 


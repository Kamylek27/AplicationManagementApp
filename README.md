# AplicationManagementApp
I used to docker-composer to get the database, I chose Postgress for this application and for easier development, I used lombok.
The application has multiple methods, each of which changes the status of the application.

Endpoints example:
```
localhost:8080/create

{
    "name":"Test",
    "description":"Test"
}
```
```
localhost:8080/delete/1
{
    "reasonOfDelete":"Test"
}
```
```
localhost:8080/update/1

{
    "description":"Test"
}
 ```
 ```
 localhost:8080/verify/1
 ```
 ```
 localhost:8080/accept/1
 ```
 ```
 localhost:8080/publish/1
 ```
 ```
 localhost:8080/pagination/0/10
 ```
 ```
 localhost:8080/pagination
 ```
 

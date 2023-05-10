# Setting up external services

This document describes the setup procedure of external services and running them locally inside a docker container.

[Toc]



## 1. Database

### SQL Server

* From the [SQLServer](database\SQLServer) directory run the SQL Server instance as a docker container using the

   `docker-compose-ms-sql.yaml` file.

  ```
  docker compose -f docker-compose-ms-sql.yaml up -d
  ```

* ##### Setup Schema :

  After the database instance has started in container , run the `create_schema.sql` script to setup the database and table.

* ##### Create Stored Procedures 

   Create the stored procedure from `P_CustomOperation.sql` definition .

* ##### Enter Dummy Data

  Run the insert scripts `insert_data.sql` to insert dataset
  
  

## 2. Redis

* From the [redis](redis) directory run the SQL Server instance as a docker container using the

   `docker-compose-ms-sql.yaml` file.

  ```
  docker compose -f docker-compose-redis.yaml up -d
  ```

* ##### 
# kotlinSpringBatchFile
A kotlin application that makes use of Spring Batch to write to a database.

## Setup
1. You need to run the docker compose file, this will setup both the batch-db and the service-db.
   1. Make sure you have Docker Desktop running (the docker service should be accessible by your IDE)
   2. Open a terminal in the root directory of the project (where the docker-compose.yaml file is located).
   3. run: `docker-compose up -d`
   4. This should now have created the container and images, you should be able to see them in Docker Desktop's Container tab.
2. Setup the database
   1. Go to the docker container, open a terminal in the service-db image
   2. You now have to login to the MySql running on the image, the username and password will be in the application-local.yaml file. The login command is:
      `mysql -u <username> -p<password>`
   3. Next you'll have to create the database, for this project I called it: bank. The command to creat the database is:
         `CREATE DATABASE bank;`. You can get information about the database that the application will connect to in the application-local.yaml file
   4. Then you have to tell MySql to use this database. The command for this is:
         `use bank;`
   5. Now you'll be able to copy the sql queries from the createTableScripts.txt file and paste them in the MySql terminal.
   You'll see that there is a service-db and a batch-db section in that file.
   6. All tables should now have been created, to see them you can run:
         `SHOW TABLES;`
   7. Do the same for the batch-db
3. Next you need to create a file to use for the validation job.
   1. First go to the JustInsertTestConfig class. You should see justTransactionBulkInsertTestJob with a JobBuilder instance being created in it. This instance will have a name, copy that name.
   2. Go to the TransactionDbApplication.kt file, you'll see a Qualifier annotation set for a jobToRun parameter in the TransactionDbApplication class. Paste the name of the job you copied there.
   3. Now run the application.
   4. Once done, you'll be able to check the just_transaction table and see the data in there



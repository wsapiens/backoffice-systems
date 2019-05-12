# inventory-manager 

This is

composed by Steve Park

## Prepare a database and user on MySQL database

```
$ mysql -u root -p
mysql> CREATE DATABASE zappos;
mysql> CREATE USER 'username'@'localhost' IDENTIFIED BY 'password';
mysql> GRANT ALL PRIVILEGES ON zappos.* TO 'username'@'localhost';
mysql> quit

$ mysql -u username -p
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| zappos             |
+--------------------+

mysql> use zappos;
Database changed
```

## Install Lombok on eclipse
After run maven build, lombok will be downloaded to local maven repo
Run the lombok jar and updated eclipse

```
$ java -jar ~/.m2/repository/org/projectlombok/lombok/1.18.4
```

## Build Project

This project is maven project, so Java version 8 or higher and Maven are required
Below are the java and maven versions used in my teste

```
$ java -version
java version "1.8.0_192"
Java(TM) SE Runtime Environment (build 1.8.0_192-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.192-b12, mixed mode)

$ mvn -v
Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-04T14:00:29-05:00)
Maven home: /usr/local/Cellar/maven/3.6.1/libexec
Java version: 1.8.0_192, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_192.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.14.4", arch: "x86_64", family: "mac"


backoffice-systems$ mvn clean install spotbugs:check

      :

[INFO]
[INFO] --- spotbugs-maven-plugin:3.1.11:check (default-cli) @ backoffice-systems ---
[INFO] BugInstance size is 0
[INFO] Error size is 0
[INFO] No errors/warnings found
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  32.786 s
[INFO] Finished at: 2019-05-11T21:15:28-05:00
[INFO] ------------------------------------------------------------------------
```

## Run Project

After finishing maven build, it will generate .war file under target folder
Run the .war file with providing three configurations

`--spring.datasource.url={your mysql database jdbc url}`

`--spring.datasource.username={database username}`

`--spring.datasource.password={database password}`


Database Tables will be created by DB migration script while starting this application.
So, you don't need to create any table manually.
This application will grab port 8080 as default, but if you want to change the port please add below configuration

`--server.port={the port you want to bind}`


```
backoffice-systems$ java -jar target/backoffice-systems-0.0.1-SNAPSHOT.war --spring.datasource.url=jdbc:mysql://localhost/zappos?serverTimezone=UTC --spring.datasource.username=username --spring.datasource.password=password

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.1.RELEASE)

2019-05-11 21:39:03.189  INFO 51344 --- [           main] com.zappos.backoffice.Application        : Starting Application v0.0.1-SNAPSHOT on spark-mbp with PID 51344 (/Users/spark/workspace2/backoffice-systems/target/backoffice-systems-0.0.1-SNAPSHOT.war started by spark in /Users/spark/workspace2/backoffice-systems)

                              :

2019-05-11 21:39:15.887  INFO 51344 --- [           main] com.zappos.backoffice.Application        : Started Application in 13.175 seconds (JVM running for 13.815)
2019-05-11 21:39:19.704  INFO 51344 --- [1)-192.168.1.24] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
```





* Upload Brands.tsv file via curl

This application has endpoints to upload Brands.tsv and Brand_Quantity_Time_Received.tsv files
First, upload Brand TSV file like below via curl command

"file" parameter needs to be infont

`-F 'file=@{the filepath of the .tsv file to uplaod}'`



```
$ curl -F 'file=@/Users/spark/Desktop/zappos/Brands.tsv'  http://localhost:8080/upload/brands
brands uploaded successfully...


$ mysql -u username -p
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| zappos             |
+--------------------+
2 rows in set (0.08 sec)

mysql> use zappos;
Database changed

mysql> select * from brand;
+----+--------------+
| ID | NAME         |
+----+--------------+
|  2 | "Asics"      |
|  5 | "Levi's"     |
|  3 | "Lucky"      |
|  1 | "Nike"       |
|  6 | "Rockport"   |
|  4 | "Timberland" |
|  7 | "Vans"       |
+----+--------------+
7 rows in set (0.00 sec)
```


* Uplaod Brand_Quantity_Time_Received.tsv file via curl

```
$ curl -F 'file=@/Users/spark/Desktop/zappos/Brand_Quantity_Time_Received.tsv' http://localhost:8080/upload/inventories
inventories uploaded successfully...


mysql> show tables;
+------------------+
| Tables_in_zappos |
+------------------+
| BRAND            |
| INVENTORY        |
| schema_version   |
+------------------+
3 rows in set (0.00 sec)

mysql> select count(*) from inventory;
+----------+
| count(*) |
+----------+
|       50 |
+----------+
1 row in set (0.01 sec)

mysql> select * from inventory;
+----+----------+----------+---------------------+
| ID | BRAND_ID | QUANTITY | TIME_RECEIVED       |
+----+----------+----------+---------------------+
|  1 |        1 |       12 | 2018-10-01 07:12:00 |
|  2 |        2 |       45 | 2018-10-01 07:15:00 |
|  3 |        3 |       67 | 2018-10-02 07:17:00 |
|  4 |        4 |       35 | 2018-10-02 07:14:00 |
|  5 |        5 |       57 | 2018-10-03 07:12:00 |
|  6 |        6 |       23 | 2018-10-03 07:13:00 |
|  7 |        7 |       13 | 2018-10-04 07:18:00 |
|  8 |        1 |       87 | 2018-10-04 08:01:00 |
|  9 |        2 |       45 | 2018-10-05 07:02:00 |
| 10 |        3 |       23 | 2018-10-05 07:03:00 |
| 11 |        4 |       15 | 2018-10-06 07:04:00 |
| 12 |        5 |       98 | 2018-10-06 07:05:00 |
| 13 |        6 |        1 | 2018-10-07 07:06:00 |
| 14 |        7 |       67 | 2018-10-07 07:07:00 |
| 15 |        1 |       45 | 2018-10-07 07:08:00 |
| 16 |        2 |       34 | 2018-10-08 07:09:00 |
| 17 |        3 |       23 | 2018-10-08 07:10:00 |
| 18 |        4 |       76 | 2018-10-08 07:01:00 |
| 19 |        5 |       23 | 2018-10-09 07:02:00 |
| 20 |        6 |       56 | 2018-10-10 07:03:00 |
| 21 |        7 |       34 | 2018-10-11 07:04:00 |
| 22 |        1 |       98 | 2018-10-12 07:05:00 |
| 23 |        2 |       26 | 2018-10-12 07:06:00 |
| 24 |        3 |       84 | 2018-10-13 07:07:00 |
| 25 |        4 |       56 | 2018-10-13 07:08:00 |
| 26 |        5 |       16 | 2018-10-14 07:09:00 |
| 27 |        6 |       29 | 2018-10-15 07:01:00 |
| 28 |        7 |       49 | 2018-10-16 07:02:00 |
| 29 |        1 |       16 | 2018-10-17 07:03:00 |
| 30 |        2 |       45 | 2018-10-18 07:04:00 |
| 31 |        3 |       34 | 2018-10-19 07:05:00 |
| 32 |        4 |       12 | 2018-10-20 07:06:00 |
| 33 |        5 |       78 | 2018-10-21 07:07:00 |
| 34 |        6 |       45 | 2018-10-22 07:08:00 |
| 35 |        7 |       34 | 2018-10-22 07:09:00 |
| 36 |        1 |       16 | 2018-10-23 07:20:00 |
| 37 |        2 |       28 | 2018-10-24 07:21:00 |
| 38 |        3 |       45 | 2018-10-25 07:22:00 |
| 39 |        4 |       23 | 2018-10-26 07:22:00 |
| 40 |        5 |       28 | 2018-10-27 07:21:00 |
| 41 |        6 |       94 | 2018-10-28 07:20:00 |
| 42 |        7 |       84 | 2018-10-29 07:01:00 |
| 43 |        1 |       54 | 2018-10-29 07:02:00 |
| 44 |        2 |       23 | 2018-10-22 07:03:00 |
| 45 |        3 |       75 | 2018-10-29 07:04:00 |
| 46 |        4 |       12 | 2018-10-29 07:05:00 |
| 47 |        5 |        0 | 2018-10-29 07:06:00 |
| 48 |        6 |       34 | 2018-10-29 07:07:00 |
| 49 |        7 |       67 | 2018-10-29 07:08:00 |
| 50 |        1 |       72 | 2018-10-30 07:09:00 |
+----+----------+----------+---------------------+
50 rows in set (0.04 sec)
```

The .tsv files used in this test are included in this project under sample folder

```
backoffice-systems$ ll sample/
total 16
drwxr-xr-x   4 spark  staff   128 May 11 21:59 .
drwxr-xr-x  15 spark  staff   480 May 11 21:58 ..
-rw-r--r--   1 spark  staff  1789 May 11 21:59 Brand_Quantity_Time_Received.tsv
-rw-r--r--   1 spark  staff    94 May 11 21:59 Brands.tsv
```

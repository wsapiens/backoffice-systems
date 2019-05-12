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

This application will parse the header line to decide column order without asking user to change the configuration.
So brand dump .tsv file must have "BRAND_ID"	"Name" both separately by tab. 

```
"BRAND_ID"	"Name"
1	"Nike"
2	"Asics"
3	"Lucky"
4	"Timberland"
5	"Levi's"
6	"Rockport"
7	"Vans"
```

if want to change column order, then the label order in header also should be changed

```
"Name"	"BRAND_ID"
"Nike"	1
"Asics"	2
"Lucky"	3
"Timberland"	4
"Levi's"	5
"Rockport"	6
"Vans"	7
```


Once have success message return from this application, you can check the result from database too.

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

mysql> select * from brand order by id;
+----+------------+
| ID | NAME       |
+----+------------+
|  1 | Nike       |
|  2 | Asics      |
|  3 | Lucky      |
|  4 | Timberland |
|  5 | Levi's     |
|  6 | Rockport   |
|  7 | Vans       |
+----+------------+
7 rows in set (0.09 sec)
```


* Uplaod Brand_Quantity_Time_Received.tsv file via curl

Same as above Brands.tsv, this application decide the order of column by parsing the header line
and check the labels. So these three labels "TIME_RECEIVED"	"QUANTITY"	"BRAND_ID" must be in header line with being separated by tab.


```
"TIME_RECEIVED"	"QUANTITY"	"BRAND_ID"
2018-10-01T07:12:00.0000000Z	12	1
2018-10-01T07:15:00.0000000Z	45	2
2018-10-02T07:17:00.0000000Z	67	3
2018-10-02T07:14:00.0000000Z	35	4
2018-10-03T07:12:00.0000000Z	57	5
2018-10-03T07:13:00.0000000Z	23	6
         :
```

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


## Web Service Endpoints Test

I see the `BRAND_ID` in .tsv file as good candidate of the primary key for `BRAND` table.
And Brand Name better be unique, so I put unique constraint on `NAME` column in `BRAND` table.
So when change entry in BRAND table, change name to different unique name. 


* Read Brands

If no id or name query param, it will read all brands
If id or name query param is given, it will return the list contains only the brand it found.
If no brand is found, it will return empty list

```
$ curl http://localhost:8080/service/v1/brands
{"brands":[{"id":2,"name":"Asics"},{"id":5,"name":"Levi's"},{"id":3,"name":"Lucky"},{"id":1,"name":"Nike"},{"id":6,"name":"Rockport"},{"id":4,"name":"Timberland"},{"id":7,"name":"Vans"}]}

$ curl http://localhost:8080/service/v1/brands?id=2
{"brands":[{"id":2,"name":"Asics"}]}

$ curl http://localhost:8080/service/v1/brands?id=20
{"brands":[]}

$ curl http://localhost:8080/service/v1/brands?name=Lucky
{"brands":[{"id":3,"name":"Lucky"}]}

$ curl http://localhost:8080/service/v1/brands?name=Bart
{"brands":[]}
```

It supports XML format for the response

```
$ curl -H "Accept: application/xml" http://localhost:8080/service/v1/brands
<?xml version="1.0" encoding="UTF-8"?>
<BrandStatus>
   <brands>
      <id>2</id>
      <name>Asics</name>
   </brands>
   <brands>
      <id>5</id>
      <name>Levi's</name>
   </brands>
   <brands>
      <id>3</id>
      <name>Lucky</name>
   </brands>
   <brands>
      <id>1</id>
      <name>Nike</name>
   </brands>
   <brands>
      <id>6</id>
      <name>Rockport</name>
   </brands>
   <brands>
      <id>4</id>
      <name>Timberland</name>
   </brands>
   <brands>
      <id>7</id>
      <name>Vans</name>
   </brands>
</BrandStatus>
```

* Create Brands and Read

Create Brands and check by read

```
$ curl -d '{"brands":[{"name":"Bart"}]}' -H "Content-Type: application/json" -X POST http://localhost:8080/service/v1/brands
{"brands":[{"id":9,"name":"Bart"}]}

$ curl http://localhost:8080/service/v1/brands?id=9
{"brands":[{"id":9,"name":"Bart"}]}

$ curl http://localhost:8080/service/v1/brands?name=Bart
{"brands":[{"id":9,"name":"Bart"}]}
```

* Update Brands

To prevent deleted brand from being re-added, service endpoint will check id exists or not and update the entries only it find

```
$ curl -d '{"brands":[{"id":9, "name":"Hommer"}]}' -H "Content-Type: application/json" -X PUT http://localhost:8080/service/v1/brands
{"brands":[{"id":9,"name":"Hommer"}]}


$ curl http://localhost:8080/service/v1/brands?id=9
{"brands":[{"id":9,"name":"Hommer"}]}
```


* Delete Brands

`id` field in the input json is used to delete Brands

```
$ curl -d '{"brands":[{"id":9, "name":"Hommer"}]}' -H "Content-Type: application/json" -X DELETE http://localhost:8080/service/v1/brands

$ curl http://localhost:8080/service/v1/brands?id=9
{"brands":[]}
```

If delete and re-add all the brands and inventries from same .tsv files, it will cause foreign key violation!! 
The `ID` column of `BRAND` table is `BRAND_ID` and set as `AUTO_INCREAMENT`. So re-upload will make brand to have different id value,
Even though it accepts the given ID value from .tsv file, only if there is nothing on table
So either updating BRAND_ID on Brand_Quantity_Time_Recieved.tsv file according to database or reset database will prevent the issue.
or develop custom identifier/sequence generator to not generate value if value is given.



* Sum of Inventories Endpoints for All Brands

I understand this for providing endpoint to pull out all the brands with its total inventory quantities
Also make this endpoints to produce json and xml payload both.

First, I checked from database

```
mysql> select BRAND_ID, sum(QUANTITY) from inventory group by BRAND_ID;
+----------+---------------+
| BRAND_ID | sum(QUANTITY) |
+----------+---------------+
|        1 |           400 |
|        2 |           246 |
|        3 |           351 |
|        4 |           229 |
|        5 |           300 |
|        6 |           282 |
|        7 |           348 |
+----------+---------------+
7 rows in set (0.00 sec)
```


Default is json style payload. The total quantity is exact same as above sql query

```
$ curl http://localhost:8080/service/v1/brands/inventories
{
   "brands":[
      {
         "brandName":"Asics",
         "brandId":2,
         "quantity":246
      },
      {
         "brandName":"Levi's",
         "brandId":5,
         "quantity":300
      },
      {
         "brandName":"Lucky",
         "brandId":3,
         "quantity":351
      },
      {
         "brandName":"Nike",
         "brandId":1,
         "quantity":400
      },
      {
         "brandName":"Rockport",
         "brandId":6,
         "quantity":282
      },
      {
         "brandName":"Timberland",
         "brandId":4,
         "quantity":229
      },
      {
         "brandName":"Vans",
         "brandId":7,
         "quantity":348
      }
   ]
}
```

Send curl command with Accept header for application/xml
Now this application returns xml payload

```
$ curl -H "Accept: application/xml" http://localhost:8080/service/v1/brands/inventories
<?xml version="1.0" encoding="UTF-8"?>
<BrandInventoryStatus>
   <brands>
      <brandName>Asics</brandName>
      <brandId>2</brandId>
      <quantity>246</quantity>
   </brands>
   <brands>
      <brandName>Levi's</brandName>
      <brandId>5</brandId>
      <quantity>300</quantity>
   </brands>
   <brands>
      <brandName>Lucky</brandName>
      <brandId>3</brandId>
      <quantity>351</quantity>
   </brands>
   <brands>
      <brandName>Nike</brandName>
      <brandId>1</brandId>
      <quantity>400</quantity>
   </brands>
   <brands>
      <brandName>Rockport</brandName>
      <brandId>6</brandId>
      <quantity>282</quantity>
   </brands>
   <brands>
      <brandName>Timberland</brandName>
      <brandId>4</brandId>
      <quantity>229</quantity>
   </brands>
   <brands>
      <brandName>Vans</brandName>
      <brandId>7</brandId>
      <quantity>348</quantity>
   </brands>
</BrandInventoryStatus>
```

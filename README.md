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

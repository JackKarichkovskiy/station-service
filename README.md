# station-service

It's a Java REST Web service which allows users to manipulate the work of some bus station and its bus traffic.

## Software requirement
* jre 1.7 or above - [download](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Tomcat Application Server - [download](https://tomcat.apache.org/download-80.cgi)
* MySQL 5.4 or above - [download](https://dev.mysql.com/downloads/workbench/)
* Maven 2.5 or above - [download](https://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache/)

## Usage

Available requests:

| Request | Method | Content-Type | Body | Description |
| :--- | :---: | :---: | :---: | :--- |
| `http://<server_host>:<server_port>/stationservice/webapi/buses` | POST | application/json | Bus JSON object | Add a new bus to the database |
| `http://<server_host>:<server_port>/stationservice/webapi/stations/<station_id>/tickets` | POST | application/json | Ticket JSON object | Order a new ticket in the station |
| `http://<server_host>:<server_port>/stationservice/webapi/stations` | POST | application/json | Station JSON object | Add a new station to the database |
| `http://<server_host>:<server_port>/stationservice/webapi/stations/<station_id>` | GET | application/json | - | Get information about station from the database |
| `http://<server_host>:<server_port>/stationservice/webapi/stations/<station_id>/tickets` | GET | application/json | - | Get list of tickets ordered in that station |
| `http://<server_host>:<server_port>/stationservice/webapi/buses/<bus_id>/seats` | POST | application/json | Ticket JSON object | Order a seat in the mentioned bus |
| `http://<server_host>:<server_port>/stationservice/webapi/drivers` | POST | application/json | Driver JSON object | Add a new driver to the database |
| `http://<server_host>:<server_port>/stationservice/webapi/buses/<bus_id>/seats` | GET | application/json | - | Get a list of all seats in the mentioned bus |
| `http://<server_host>:<server_port>/stationservice/webapi/buses/<bus_id>` | PUT | application/json | Bus JSON object | Update the information of bus in the database |
| `http://<server_host>:<server_port>/stationservice/webapi/buses/<bus_id>/seats/freeSeats` | GET | application/json | - | Get the list of all not ordered seats in the bus |
| `http://<server_host>:<server_port>/stationservice/webapi/buses` | GET | application/json | - | Get the list of all buses in the database |
| `http://<server_host>:<server_port>/stationservice/webapi/stations` | GET | application/json | - | Get the list of all stations in the database |
| `http://<server_host>:<server_port>/stationservice/webapi/stations/<station_id>` | PUT | application/json | Station JSON object | Update the information about station in the database |
| `http://<server_host>:<server_port>/stationservice/webapi/buses/<bus_id>/seats/orderedSeats` | GET | application/json | - | Get the list of all ordered seats in the bus |
| `http://<server_host>:<server_port>/stationservice/webapi/buses/<bus_id>/seats/<seat_number>` | GET | application/json | - | Get the information of ticket related to the seat number in the bus |
| `http://<server_host>:<server_port>/stationservice/webapi/stations/<station_id>` | DELETE | application/json | - | Delete the station from the database |
| `http://<server_host>:<server_port>/stationservice/webapi/drivers/<driver_id>` | GET | application/json | - | Get driver information from the database |

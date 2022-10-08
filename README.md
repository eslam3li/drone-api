
---
## Table of contents
>* [Title](#Drone Rest API)
>* [About](#about)
>* [Requirements](#requirements)
>* [APIs](#apis)
   >    * [Register a drone](#register-a-drone)
>    * [Load a drone with medication items](#load-a-drone-with-medication-items)
>    * [Get loaded medication items for a given drone](#get-loaded-medication-items-for-a-given-drone)
>    * [Get drones available for loading](#get-drones-available-for-loading)
>    * [Get battery level for a given drone](#get-battery-level-for-a-given-drone)
>
---
# Drone Rest API

## About
* Service via REST API that allows clients to communicate with the drones.
---
# Requirements
* Java 8 SDK or higher.
* Maven 3.6 or higher.
---
# APIs
## Register a drone:
- Url: `http://localhost:8080/api/drone
- Request type: POST
- Request body Sample
```
{
    "serialNumber":"15cb3a86-2d1d-49ab-94cb-9d1d5be5ba54",
    "model":"Lightweight",
    "weightLimit":"100",
    "state":"IDLE",
    "batteryCapacity": 80
}
```

## Load a drone with medication items:
- Url: `http://localhost:8080/api/drone/medications
- Request type: PUT
- Request Parameters Sample
```
    KEY1 : serialnumber 
    VALUE1: 15cb3a86-2d1d-49ab-94cb-9d1d5be5ba54
    
    KEY2 : medicationcodes 
    VALUE3 : CRX_MJ70,CRX_QT22

```
## Get loaded medication items for a given drone:
- Url: `http://localhost:8080/api/drone/medications
- Request type: GET
- Request Parameter Sample
```
{
    KEY : serialnumber 
    VALUE: 15cb3a86-2d1d-49ab-94cb-9d1d5be5ba54
}
```
## Get drones available for loading:
- Url: `http://localhost:8080/api/drones
- Request type: GET


## Get battery level for a given drone:
- Url: `http://localhost:8080/api/drone/battery
- Request type: GET
- Request Parameter Sample
```
{
       KEY : serialnumber 
       VALUE: 15cb3a86-2d1d-49ab-94cb-9d1d5be5ba54
}
```
---


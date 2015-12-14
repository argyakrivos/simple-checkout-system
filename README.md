# Simple Checkout System

This is a simple checkout system implementation based on a shop that sells `Items` (Apples, Oranges, Bananas). Input is a `Basket` which can contain several items and output is a `Receipt` which lists all your items and the total price.

## Current offers

- Apples: buy one, get one free*
- Bananas: buy one, get one free*
- Oranges: 3 for the price of 2

\* You can mix and match items and the cheapest one will be free.

## Run

The service will run on http://localhost:5000 (see [application.conf](src/main/resources/application.conf)). To start the service, type:

```
$ sbt run
```

## REST API

- `POST` `/checkout` with `Basket` entity
	- `200` with a `Receipt`
	- `400` if no `Basket` is provided
	- `400` if the `Basket` contained an unrecognised item
	- `500` if something unexpected happened

### Example

```
$ curl -X POST -H "Content-Type: application/json" -d '{"items":["Apple","Orange","Orange"]}' 'localhost:5000/checkout'

{"items":["Apple","Orange","Orange"],"total":1.1}
```

## Test

To run all the tests, simply type:

```
$ sbt test
```

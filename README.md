# random-draw
draw a random entry from weighted entries

Build
gradlew clean bootJar

Docker
docker-compose up

Endpoint 
Optional Parameters 
int numberOfWinners (default 1)
int seed (Supplying a seed will give a consistent result. Not used if not supplied / Must be used with numberOfWinners)

Valid requests examples: 

   POST http://localhost:8080/random-draw/draw

   POST http://localhost:8080/random-draw/draw?numberOfWinners=1

   POST http://localhost:8080/random-draw/draw?numberOfWinners=1&seed=1

Body
```
[
   {
      "entryId":"FirstEntry",
      "weight":2
   },
   {
      "entryId":"SecondEntry",
      "weight":1
   },
   {
      "entryId":"ThirdEntry",
      "weight":5
   },
   {
      "entryId":"FourthEntry",
      "weight":4
   }
]
```
Response
```
[
    {
        "entryId": "FirstEntry",
        "weight": 2
    }
]
```

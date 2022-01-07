# 24-pay 
---
## Payment gate integration

## Requirements
* Java > 1.7+
* Free local host 8024 port 

## Run
Final JAR is located in target folder. 

`
java -jar paymentGate-0.0.1-SNAPSHOT.jar
`

## Internal Logic

Dependencies are managed by **maven.** <br />
**Static** dependencies were bundled via webpack. 
All static dependencies are in **./resources/static** folder <br />
**Thymeleaf** is templating engine

<br />
Application shows HTML form to the user. On the UI level all the form are validated
through HTML5 fields. After form is submitted, on server site data are injected into POJO. Payment resolution is a POJO 
which later can be stored in database. POJO is validated using JAVAX validation. It only contains payment related fields. 
Fields such as Mid, Eshopid, Key are defined in application properties and injected into model. This model represents 
HTML post form which send POST request to 24-pay server.  <br /> <br />Another endpoint-> rurl simply reads query params
which are simulated outcomes of the transaction. 

### Rest
**Endpoints:**
1. **GET** request                       
   Show UI payment gateway form 
2. **POST** request      
   Send POST request to 24-pay server
3. **GET** rurl
   Get resuls

   
## Ideas for further improvements
1. UX/ UI
2. Tests
3. Validation of predefined constants such as CountryCodes or CurrencyCodes 

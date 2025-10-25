## Lab #5

**XML/JSON data parsing from the Web API**

### You will need to use „Android Studio” IDE in order to create an application, that will read currency rates from the chosen source and will display them to the user. Application should meet the following requirements:
- Common part:
  - Variables, functions and other elements should have meaningful names (e.g. txtName vs EditText1);

### Special part:
- There should be the following classes in the application:
  - MainActivity – displays a list of currency rates by defined format: “USD – 1.235“ (You can use any currency as base currency). One way to show the list is to use ListView control. Used should be able to filter currencies list i.e. can enter currency name (EditText or Dropdown) and get info just about that particular currency.
  - DataLoader – class for downloading data in the background. You can use any background processing class like AsyncTask, Bound/Started service or others.
  - Parser – class for parsing the XML/JSON data for required information. You can choose DOM or SAX approach by Your preference.

### Data can be acquired from the following sources:
      •	http://www.floatrates.com/daily/usd.xml
      •	https://api.exchangeratesapi.io/latest
      •	Others…

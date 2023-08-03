# bonus-app

#Build app
mvn clean install

#Run the app
mvn spring-boot:run

#Test api
1.Use postman
2.Create a customer using "/customers/create", method: post
3. Add transaction to a customer using "/transactions/customerId/create", method: post
4. Get monthly report for a customer "/transactions/customerId", Method: Get

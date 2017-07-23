This is SPRING BOOT application with h2 embedded database.

1. To launch, you have to run com.pc.checkout.Main class.
2. com.pc.checkout.DataLoader class is responsible for setting up data for DB.
3. Application will run on port 8080.
4. Application have SWAGGER enabled, so there is possibility to check requests on url:
http://localhost:8080/swagger-ui.html#
5. In order to use checkout component first you have to login using /login URL. There you
will get token, for further operations.
6. Example database data:

Customers: "MACIEJ", "TOMEK", "ADMIN";
Items to buy: "APPLE", "BANANA", "CHERRY", "DILL";
Promotions: 5 APPLES for 10 units and 2 CHERRIES for 80 units;
Rebate: if u buy all 4 products u will get 50 units rebate
 if u buy apple, banana and cherry u will get 10 units rebate

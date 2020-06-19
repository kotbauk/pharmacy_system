Run and Build
1. git clone https://github.com/kotbauk/pharmacy_system.git 
2. Change ${ORACLE_NAME} and ${ORACLE_PASSWORD} properties in mvnsetting.xml file to your Oracle credentials.
It need for uploading Oracle JDBC driver.
3. Build the application: mvn package --settings mvnsetting.xml
4. Run the application java -jar target/pharmacy_system-1.0-SNAPSHOT-jar-with-dependencies.jar
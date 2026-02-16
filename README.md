## Online Complaint Registration and Tracking System

An minimal Application built with java to Register and track complaints online for CAT 1 assignment of UCS2601 Internet Programming Course.

### Requirements

- java 17+
- apache tomcat 11+

### Instruction to use.

- Download the zip file of this folder.
- Unzip `OCRTSystem` folder into `<path-to-tomcat>/webapps/`
- open terminal in `<path-to-tomcat>/webapps/OCRTSystem/WEB-INF/classes`
- run the following 

        javac -cp "<path-to-tomcat>/lib/servlet-api.jar" Login.java
        javac -cp "<path-to-tomcat>/lib/servlet-api.jar" PostComplaint.java

- start apache tomcat server on a new terminal by running `"<path-to-tomcat>/bin/startup.bat"`
- open browser and go to http://localhost:8080/OCRTSystem
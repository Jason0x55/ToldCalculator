1. Browse to https://github.com/Jason0x55/ToldCalculator
2. On the right side of the page there should be a green button labeled ‘clone or download’
3. Click on that and copy the link.
4. Open IntelliJ IDEA and close any open projects.
5. Click on 'Check out from Version Control' and select Git.
6. Paste the link you copied into the URL box, select the directory you want to download the project to and press clone.
7. Select no when prompted 'Would you like to create an IntelliJ IDEA project for the sources you have checked out to ...'
8. Select 'Import Project' and select the folder you downloaded the project to and click ok.
9. Select 'Import project from external model' and select 'Gradle' and click next.
10. Check 'Use auto-import' and 'Create directories for empty content roots automatically' and select finish.
11. Select yes to overwrite the project file.
12. Allow IntelliJ to load and import and necessary files.
13. Try to build the project. If you get an error that 'SDK location not found. Define location with sdk.dir in the local.properties file or with an ANDROID_HOME environment variable'
14. Copy the ‘local.properties’ file from another project to the root director of the ToldCalculator project. 
Close the project and reopen it and you should be able to run it or
Select Run → Edit configurations. Press the green plus sign on the left and selected Android app. Name in app and under the General tab select app under the Module dropdown list. Press ok and you should be able to run the app.
[Back](../../README.md)
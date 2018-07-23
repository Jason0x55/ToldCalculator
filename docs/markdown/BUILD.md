1. Browse to https://github.com/Jason0x55/ToldCalculator
2. On the right side of the page there should be a green button labeled ‘clone or download’
3. Click on that button and copy the link it generates.
4. Open IntelliJ IDEA and close any open projects.
5. Click on 'Check out from Version Control' and select Git.
6. Paste the link you copied into the URL box, select the directory you want to download the project to and press clone.
7. Select no when prompted 'Would you like to create an IntelliJ IDEA project for the sources you have checked out to ...'
8. Select 'Import Project' and select the folder you downloaded the project to and click ok.
9. Select 'Import project from external model', select 'Gradle' and click next.
10. Check 'Use auto-import' and 'Create directories for empty content roots automatically' and select finish.
11. Select yes to overwrite the project file.
12. Allow IntelliJ to load and import and download any necessary files.  

* If you get an error that 'SDK location not found. Define location with sdk.dir in the local.properties file or with an ANDROID_HOME environment variable'
* Copy the ‘local.properties’ file from another android project to the root director of the ToldCalculator project. Close the project and reopen it and you should be able to run it.
* If you still don't see a Run configuration - Select Run -> Edit configurations. Press the green plus sign on the left of the Run Configurations dialog and select Android app. Give the configuration a name. Then on the General tab select app under the Module dropdown list. Press ok and you should be able to run the app.  
[Back](../../README.md)
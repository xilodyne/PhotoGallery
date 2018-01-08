# Photo Gallery 

Steps to run PhotoGallery in Eclipse or create a gallery.war for production.

## Developer settings
### To run in Eclipse Oxygen JEE:
* Install JDK 9.0.1
* Install Tomcat 8.5
* Install HSQLDB 2.4.0 (hsqldb.org)
* Using Eclipse Oxygen, add Tomcat as server
* Import PhotoGallery project
* Verify Context (Properties -> WebContext) is:   gallery
* Right click Project -> Run On Server
* To turn on debug, edit conf/gallery_user.conf, change DEBUG to true



## Production Settings
### update the following files

In Globals.java (xilodyne.photo_gallery.globals)
* Comment out dev section
* Uncomment prod section

In ConfigFile.java (xilodyne.photo_gallery.fileio):
* Uncomment code at line 479

In web.xml (WebConent/WEB-INF)
* Uncomment log4j settings for production
* Comment out the log4j settings for development

If using Google Analytics, make changes to AssembleHTTP.makeFooter(xilodyne.photo_gallery.http)
* dspFooter, uncomment line 1143
* dspGoogleAnalytics: line 1130, update your settings.


### Create the gallery.war file
In Eclipse, right click the Project, click export, choose Web -> WAR

See README\_for\_production in ProductionDeploymentFiles to setup a production environment.


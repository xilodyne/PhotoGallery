# PhotoGallery Tomcat Production Installation

This gallery.war is configured for a specific Tomcat environment that uses files outside of the webapp.  To use a gallery.war that uses files and photos which reside within the Tomcat webapp, create a new gallery.war from the development environment with only development settings.

As a reminder:

	* only jpeg photos are processed
	* all photos must have a unique filename and use the format <date>-filename.jpg
	* A folder contains photos and the folder becomes a gallery in PhotoGallery

For more options on photo filename format options, see the root README.md.



## Prerequisites

PhotoGallery has been developed and tested with the following environment:

```
Ubuntu 64bit OS 	(tested on 16.03 LTS)
jre-9.0.1  		(java.oracle.com)
tomcat-8.5  		(apache.org)
netpbm 			(apt install netpbm)
hsqldb-2.4.0		(hsqldb.org)
	Install hsqldb to /opt/java/db/hsqldb-2.4.0
```

In addition I use an apache server on the internet with a reverse relay to the tomcat URL.  You'll need to setup apache to allow a reverse relay.  Both my port 80 and port 443 relay files point to my Tomcat port 8080.

Here is a sample http relay:

```
XXX@XXX:/var/opt/apache2/conf/includes$ cat http.tomcat\_relay.conf

ProxyPass              /gallery/        http://localhost:8080/gallery/
RewriteCond            %{HTTP\_REFERER} ^.*/gallery/.*   [NC]
RewriteCond            %{REQUEST\_URI}  !^/gallery/.*    [NC]
RewriteRule            ^/(.*)          /gallery/$1      [C]
RewriteRule            ^/gallery/(.*)   http://localhost:8080/gallery/$1    [P]
ProxyPassReverse       /gallery/        http://localhost:8080/gallery/

<Location /gallery>
        Require all granted
</Location>

XXX@XXX:/var/opt/apache2/conf/includes$
```

In my case, localhost is another server referenced via local DNS or /etc/hosts from the server that hosts apache.  Thus from the internet to access the PhotoGallery:

```
http://<your\_domain>/gallery
```



## Configure Tomcat to allow external access

Add the following line to the <tomcat\_installation>/conf/server.xml into the < HOST> section

```
<Context path="/gallery/images" docBase="/opt/gallery.app/images" debug="0" reloadable="true" crossContext="false" readonly="true"/>
```

## Setup the source photo directory

This application requires all original photos to be in folders located at:

```
/var/opt/Shared
```

The Shared directory and all sub directories should be configure to be **read only**.  **With this Tomcat configuration it IS POSSBLE that ALL PHOTOS in /var/opt/Shared WILL BE DELETED.**

Always maintain an active backup of your original photos.

The photo files can live in /var/opt/Shared or be linked there.  In my case I use a NFS read only mount to a Windows server that holds all of my photos (df -k of my ubuntu server).

```
Filesystem                       1K-blocks      Used Available Use% Mounted on
udev                                492816         0    492816   0% /dev
tmpfs                               102340     10840     91500  11% /run
/dev/sda1                         30316572  17789384  10964156  62% /
tmpfs                               511688         0    511688   0% /dev/shm
tmpfs                                 5120         0      5120   0% /run/lock
tmpfs                               511688         0    511688   0% /sys/fs/cgroup
cgmfs                                  100         0       100   0% /run/cgmanager/fs
my\_windows\_server:/Shared        806967296 713117568  93849728  89% /var/opt/Shared
```

Using the following entry in my fstab to establish the mount:

```
my_windows_server:/Shared /var/opt/Shared nfs   ro,user,noauto,noexec 0 0
```




## Installation

Create the support directories

```
mkdir /opt
mkdir /opt/java
```

Unzip custom-java.zip and move to /opt/java

```
mv custom-java /opt/java/
```

Unzip exif-perl.zip and move to /opt

```
mv perl /opt
```

Unzip gallery\_app.zip and move to /opt
 
```
mv gallery.app /opt
```


### Deploy the gallery.war to Tomcat.  

Deploy the gallery.war by copying into the webapps folder:

```
cp gallery.war <tomcat_installation>/webapps
```

Be sure to review the catalina.out file to see if there are any errors.

The application requires access to the external photo directories.  Create the links in the newly created webapps/gallery folder.

```
cd <tomcat_installation>/webapps/gallery
sudo ln -s /opt/gallery.app/images ./images
sudo ln -s /var/opt/Shared ./Shared  
```

**REMEMBER, remove the links prior to removing your gallery.war from the webapps directory or ALL PHOTOS WILL BE DELETED.**

The application knows to use the files located at /opt/gallery.app as configured in:

```
<tomcat\_installation>/webappa/gallery/conf/gallery_defaults.property 
```


### Change the Admin password

Change the default Admin password if exposing the PhotoGallery to the internet.  The following files must be updated:

```
/opt/gallery.app/conf/gallery_defaults.property
```


### Customization

It is possible to customize some aspects of the PhotoGallery web presentation (like colors or the website name):

```
/opt/gallery.app/conf/gallery_user.property
```
I highly recommend to not make any changes until after you are sure everything is working correctly.  And then only one change at a time.  Be sure to stop and start tomcat after each change and clear your browser cache before viewing the results.



## Preprocessing

PhotoGallery uses thumbnails and web display images based on the original photos located in /var/opt/Shared.  In addition metadata is extracted out into text files and converted to html files.

### Configure the run\_collect.sh to process your photos

This script creates the thumbnails, web photo, metadata, <folder_name>.xml, and the sql files to be loaded.

Edit the file:

```
vi /opt/gallery.app/scripts/process_photos.sh
```

The collect.sh assumes that your original photos are at /var/opt/ and the process\_photos.sh are appending folders located in the Shared directory.

Each folder containing .jpg files is processed.  Subfolders are ignored.  Each folder must have an entry in the process\_photos.sh.  

The folder name becomes the gallery name that appears on the website.


### Process the photos

Run the script.

```
/opt/gallery.app/scripts/process_photos.sh
```


### Update the temporary database

```
/opt/gallery.app/scripts/database/bin/load_db.sh
```

If no errors, continue, otherwise fix the problem prior to moving the temp database to the production database.  Usually failures are due to duplicate photo filenames.   To find which folder has the same photo filename in the database:

```
grep -i <offending_photo_name> /opt/gallery.app/scripts/database/db-load/gallery.script
```


### Load temp database to production

Tomcat needs to be stopped prior the update of the production database.

```
sudo /etc/init.d/tomcat stop
/opt/gallery.app/scripts/database/bin/copy_to_prod.sh
sudo /etc/init.d/tomcat start
```



## Publish the galleries on the website

Prior to using the admin servlet, a session must be established.  First view the gallery/photo URL:

```
http://<your_domain>:8080/gallery/photo
```

Change to the admin servlet (using the password you have assigned, assuming you change the password from "changme").

```
http://<your_domain>:8080/gallery/admin
```

Click "Change Access Codes".  For each folder processed you will see an equivalent <folder name>.xml file.  Click the xml file link, assign to public (or create a unique access code, but before doing so, click "New Access Code" at the upper right hand corner of the web page that lists all of the xml files).

Click the link to your galleries at the bottom of the webpage.  Your gallery is now available for viewing.


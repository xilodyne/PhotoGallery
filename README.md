# PhotoGallery for Tomcat

PhotoGallery is a Tomcat application for displaying public and private photos.  Ver 1.0 was written in 2007 to allow me to see my photos on the internet, at that time over DSL via a Windows server in my garage.  It displays both public photos that anyone could view (and download the original photo) and private photos that use an access code.  For an example of a running version, see [PixiePhoto](http://photo.xandus.net/gallery/photo?image=20170929-Pixie.jpg).  Bugs and enhancements are fixed as time permits (which is rarely).  Please let me know if you find any severe issues. 

PhotoGallery only displays jpeg photos.  For generating thumbnails and web photos from the original photos, and metadata EXIF extraction, you'll need scripts to do the preprocessing, which can be found in the ProductionDeploymentFiles.  That directory also includes the production gallery.war file to be deployed on Tomcat.  Be sure to read follow the [README.md](https://github.com/xilodyne/PhotoGallery/tree/master/ProductionDeploymentFiles).


## Java Development Components

PhotoGallery consists of three Java projects
* PhotoGallery\_4.3\_9.0.1 (the Tomcat application)
* UTIL\_ExtractEXIF\_v1.2\_9.0.1 (command line app for jpeg exif extraction)
* UTIL\_FormatEXIFMetaDataToHTML\_v1.2\_9.0.1 (command line app to convert exif to html)

The following software is required:

```
jre-9.0.1  	(java.oracle.com)
tomcat-8.5  	(apache.org)
hsqldb-2.4.0	(hsqldb.org)
```

All Java development (above) is done in eclipse-jee-oxygen on Windows 10 Pro.


## Scripts for production 

In the ProductionDeploymentFiles directory are the scripts and jars necessary to perform the preprocessing of images and metadata that is used by the PhotoGallery app.  (See the ProductionDeploymentFiles/README\_for\_production.md.)


## Adding a description and author to a jpeg photo

PhotoGallery scripts read the jpeg exif data.  

The Description that appears under a photo on the website is the data taken from the jpeg exif tag < IFD0:XPComment>.  This tag contains the data from the Comments: field that appears in the Details Pane of the Windows File Explorer.  To see the Comments field, highlight a jpeg photo, click the Details Pane, enter your data into the field.  Click Save to update the exif of the jpeg.  

The Details Pane field Authors: is used to populate the Author on the website.  No other fields from the Details Pane are used.  However all fields in the exif will appear in the metadata porition under the photo.

The PhotoGallery photo title is extracted from the photo filename.


## Photo naming conventions

* PhotoGallery only allows unique photo filenames.
* Photo filenames must be in the following format:

```
<date>-filename.jpg (or .JPG)
or
<date>-filename-<seq number>.jpg
```
* The date format can be:

```
YYYY
YYYYMM
YYYYMMDD
```
* Sequence number can be any number:

```
1
01
001
1234
etc.
```
* The '-' and '\_' are reserved.  The '-' (hypen) can only be used to separate date, filename, and sequence number.  The '\_' (underscore) is used to display a space within the text portion (not date nor sequence) of the photo filename.  A space will also appear in the photo name if a capital letter is used (i.e. 20180118-MyFriends\_here-3.jpg would display as My Friends Here - #3).


## License

PhotoGallery is licensed under The Unlicense ([link](http://unlicense.org/)).  Other component and libraries licenses are found in the doc directory.


## Authors

**Austin Davis Holiday** - *Initial work* 

I can be reached at [aholiday@xilodyne.com](mailto:aholiday@xilodyne.com)

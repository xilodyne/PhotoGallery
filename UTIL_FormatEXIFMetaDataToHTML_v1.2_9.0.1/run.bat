
del testing\out\*

java -jar UTIL_FormatEXIFMetaDataToHTML_v1.2_9.0.1.jar HTMLDisplay5 testing/in/20130629-Pixie.jpg.exiftool.xml >> testing/out/html5.txt

java -jar UTIL_FormatEXIFMetaDataToHTML_v1.2_9.0.1.jar HTMLDisplay4 testing/in/20130629-Pixie.jpg.exiftool.xml testing/in/gallery_user.property >> testing/out/html4.txt

java -jar UTIL_FormatEXIFMetaDataToHTML_v1.2_9.0.1.jar HTMLSchema5 testing/in/20130629-Pixie.jpg.exiftool.xml >> testing/out/schema.txt

del /Q testing\out\*


cd testing\in 

java -jar ..\..\UTIL_ExtractEXIF_v1.2_9.0.1.jar date 20060723-Matterhorn-6.jpg >> ../out/date.txt

java -jar ..\..\UTIL_ExtractEXIF_v1.2_9.0.1.jar title 20060723-Matterhorn-6.jpg >> ../out/title.txt

java -jar ..\..\UTIL_ExtractEXIF_v1.2_9.0.1.jar meta 20060723-Matterhorn-6.jpg >> ../out/meta.txt
java -jar ..\..\UTIL_ExtractEXIF_v1.2_9.0.1.jar meta ../../../PhotoGallery_v4.3_9.0.1/WebContent/galleries/originals/Pixie/20160405-FifiHaircut-2.jpg >> ../out/pixie.meta.txt

java -jar ..\..\UTIL_ExtractEXIF_v1.2_9.0.1.jar toLowercase 20060723-Matterhorn-6.jpg >> ../out/lower.txt

java -jar ..\..\UTIL_ExtractEXIF_v1.2_9.0.1.jar marquee ../../../PhotoGallery_v4.3_9.0.1/WebContent/galleries/thumbnails/Matterhorn-2006.07.23 > ../out/marquee.txt

cd ..\..


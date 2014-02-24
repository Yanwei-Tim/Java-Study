#!/bin/sh

case "$1" in
webbuild)
        cd /mygou/data/appstore/
	svn update src
	svn update conf
	svn update lib
    svn update AppStore
        ant init
	cp -R /mygou/data/appstore/online_conf/resource.properties /mygou/data/appstore/classes/resource.properties
	cp -R /mygou/data/appstore/online_conf/application.properties /mygou/data/appstore/classes/application.properties
	rm -rf /mygou/AppStore/WEB-INF/classes
	rm -rf /mygou/AppStore/WEB-INF/lib
        rm -rf /mygou/AppStore/static
	rm -rf /mygou/AppStore/WEB-INF/pages
	cp -R /mygou/data/appstore/classes /mygou/AppStore/WEB-INF/classes
	cp -R /mygou/data/appstore/lib /mygou/AppStore/WEB-INF/lib
	cp -R /mygou/data/appstore/AppStore/WEB-INF/pages /mygou/AppStore/WEB-INF/pages
        cp -R /mygou/data/appstore/AppStore/static /mygou/AppStore/static
	
;;

webstart)
        cd /mygou/tomcat1/bin
        ./startup.sh
        tail -f ../logs/catalina.out
;;

webstop)
        cd /mygou/tomcat1/bin
        ./shutdown.sh
;;

webrestart)
	$0 webstop
	sleep 2
	$0 webstart
;;

*)
echo "Usage: server.sh {webbuild|webstart|webstop|webrestart}"
;;

esac

exit 0

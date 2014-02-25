#!/bin/sh

case "$1" in
webbuild)
        cd /auth_server/source/
	svn update src
	svn update conf
    svn update WebContent
        ant -f /auth_server/source/build.xml init
		
	rm -rf /auth_server/WWW/*
	cp -R /auth_server/source/WebContent/*  /auth_server/WWW
	#这样拷贝会把WebContent子文件夹内的.svn拷贝过去？？？是个问题需要解决
;;

webstart)
        cd /auth_server/apache-tomcat-6.0.36/bin
        ./startup.sh
        tail -f ../logs/catalina.out
;;

webstop)
        cd /auth_server/apache-tomcat-6.0.36/bin
        ./shutdown.sh
		tail -f ../logs/catalina.out
;;

webrestart)
	cd /auth_server/apache-tomcat-6.0.36/bin
    ./shutdown.sh
	sleep 2
	./startup.sh
    tail -f ../logs/catalina.out
;;

*)
echo "Usage: server.sh {webbuild|webstart|webstop|webrestart}"
;;

esac

exit 0

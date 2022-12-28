#!/bin/bash


loc=/home/shahidul/Dropbox/work/workshop/
sourceLoc=com
targetJar=app-library-0.jar.original
LibraryApp=LibraryApplication.class
libraryJar=com.library.jar
requiredJar=raw.jar
sourceJar=source.jar
obfuscator=obfuscator-core-1.9.3.jar
targetJarLoc=library.app/app/target/
obfuscatorLoc=library.app/obfuscator/

echo -e "\nProcess starting please wait ..."; sleep 1;
echo -e "Checking ...\n";

SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")

sleep 1; echo "	Step: 1";

if [[ "$loc" != "$SCRIPTPATH/" ]]; then
    echo "		*Script is in worng place.";
	echo "Process abandon"; exit;
else
    echo "		Script is in right place.";
fi

sleep 1; echo "	Step: 2";

if [[ ! -d "$loc" ]]; then
    echo "		*There is no working place.";
	echo "Process abandon"; exit;
else
    echo "		You have a working place";
fi

sleep 1; echo "	Step: 3";

echo "		Cleaning working place...";
ls -1 | egrep -v "^library.sh$|^library.app$" | xargs rm -r

sleep 1; echo "	Step: 4";

if [[ ! -f "$loc$obfuscatorLoc$obfuscator" ]]; then
    echo " 		*'$loc$obfuscatorLoc$obfuscator' does not exists.";
	echo "Process abandon"; exit;
else
    echo "		Obfuscator is in right place";
fi

sleep 1; echo "	Step: 5";

if [[ ! -f "$loc$targetJarLoc$targetJar" ]]; then
    echo " 		*'$loc$targetJarLoc$targetJar' does not exists.";
	echo "Process abandon"; exit;
else
    echo "		Target jar is in right place";
fi

sleep 1; echo "	Step: 6";

export JAVA_HOME=/opt/jdk/jdk-15.0.2
export PATH=${PATH}:${JAVA_HOME}/bin

javaVersion=$(exec java -version 2>&1 | awk -F '"' '/version/ {print $2}');
javacVersion=$(exec javac -version | tr ' ' '\n' | tail -1 );
javaHomeVersion=$(echo "$JAVA_HOME" | grep -oE "[^-]+$");
javaVar=$(java -version 2>&1 | grep -oP 'version "?(1\.)?\K\d+' || true)

echo "		1. java: $javaVersion";
echo "		2. javac: $javacVersion";
echo "		3. java home: $javaHomeVersion";
echo "		4. java version: $javaVar";

if [[ "$javaVersion" != "$javacVersion" ]] || [[ "$javaVersion" != "$javaHomeVersion" ]]; then
	echo "	*Java configurations are not same";
	echo "		1. sudo update-alternatives --config java";
	echo "		2. sudo update-alternatives --config javac"; 
	echo "Process abandon"; exit;
elif [[ "$javaVar" != "15" ]];  then        
	echo "	*Java 15 is required as default version."
	echo "	Please configure:";
	echo "		1. sudo update-alternatives --config java";
	echo "		2. sudo update-alternatives --config javac"; 
	echo "Process abandon"; exit;
else
	echo -e "\n	Satisfied the java version.";
fi

sleep 1; echo -e "\nChecking complete."; sleep 1; 
echo "......................";sleep .5;
echo "......................";sleep .5;
echo -e "\nStart processing ...\n";

sleep 1; echo "	Step: 1";
echo "		Copying jar file...";
cp $loc$targetJarLoc$targetJar $loc$targetJar;

sleep 1; echo "	Step: 2";
echo "		Renameing jar file...";
mv $targetJar $requiredJar;

sleep 1; echo "	Step: 3";
echo "		Extracing jar file...";
echo "......................";sleep .5;
echo "......................";sleep .5;
jar xvf $requiredJar;
echo "......................";sleep .5;
echo "......................";sleep .5;

# sleep 1; echo "	Step: 4";
# echo "		Removing LibraryApp file...";
# echo "......................";sleep .5;
# echo "......................";sleep .5;
# rm $sourceLoc/$LibraryApp
# echo "......................";sleep .5;
# echo "......................";sleep .5;

sleep 1; echo "	Step: 4";
echo "		Rearranging jar file...";
echo "......................";sleep .5;
echo "......................";sleep .5;
jar cvf $sourceJar $sourceLoc;
echo "......................";sleep .5;
echo "......................";sleep .5;

sleep 1; echo "	Step: 5";
echo "		Creating obfuscate file...";
echo "......................";sleep .5;
echo "......................";sleep .5;
java -jar $loc$obfuscatorLoc$obfuscator --jarIn $sourceJar --jarOut $libraryJar;
echo "......................";sleep .5;
echo "......................";sleep .5;

sleep 1; echo "	Step: 6";
echo "		Removing unnecessary file...";
ls -1 | egrep -v "^com.library.jar$|^library.sh$|^library.app$" | xargs rm -r

sleep 1; echo -e "\nProcess complete."; 

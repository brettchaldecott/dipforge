The Dipforge project
====================

This project is released under LGPL and was orgininaly called Coadunation. It
is currently going through a very serious code rework in order to improve its
design. A re-release is tentatively scheduled for the end of the forth 
quarter of 2011.

This project is still under serious development and will now build the full
application server and present a very simple management desktop once started.
The management desktop is the part under serious development at the moment
and when that is complete the project will officially release the entire 
application server as an innitial alphar release.

The build process requires gradle, and at the time of writing the latest 
version of gradle was 1.0-milestone-3. The build process has not been tested
with a later version.

To build the application server follow the following steps:
----------------------------------------------------------

    bash# git clone https://brettchaldecott@github.com/brettchaldecott/dipforge.git  
    bash# cd dipforge  
    bash# gradle release  
    note: build will only work with gradle milestone 5+  
    bash# cd release/dipforge/bin  
    bash# chown a+r run.sh  
    bash# sudo ./run.sh  

I have not tested the build process on windows. It will be the same but the
last step will require the invocation of the run.bat script. Feel free to try
it and report any problems.

Note: Dipforge requires root or admin privilages to run properly. This is 
because it start services such as pop, imap and dns on reserved ports. If you
do not need these services to function properly than the application server
does not have to run as super user.

The Dipforge project
====================

This project is released under LGPL and was orgininaly called Coadunation. It isa project that focuses on providing rapid enterprise development tools. Thus enabling the rapid development and deployment of enterprise level applciations.

In order to facilitate this it provides the following

    1. A web IDE
    2. A common type system that makes persistance transparent.
    3. A workflow system built around actions on the common types.
    4. An MVC web layer.
    5. A simple script environment for the development of end points.


To build the application server follow the following steps:
----------------------------------------------------------

    bash# git clone https://brettchaldecott@github.com/brettchaldecott/dipforge.git  
    bash# cd dipforge  
    bash# gradle release  
    bash# cd release/dipforge/bin  
    bash# sudo ./run.sh  

I have not tested the build process on windows. It will be the same but the
last step will require the invocation of the run.bat script. Feel free to try
it and report any problems.

Note: Dipforge requires root or admin privilages to run properly. This is 
because it start services such as pop, imap and dns on reserved ports. If you
do not need these services to function properly than the application server
does not have to run as super user.

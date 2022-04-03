## The Refactoring of import_export 

In this little pet project a piece of legacy code, that i had to work with, is transformed   
from procedural, over a more object-oriented approach, to a modern way. 

import_export is connected to an ERP system.   
It`s main functions are: 
* imports business data from xml files into that ERP system  
* exports business data from ERP to xml files 
* update business data in ERP via xml files

The original import_export was written in C# and without any UnitTests.
For demo purposes i have build a model to work with in Java.
I started this as a Spring project so all dependencies would already be there when we need them. 
That`s also why we already have a JUnitTest by default. 

To demonstrate certain aspects of the program i build in a very tiny ERP Model with some behaviours the program works with. 
Those are used trough almost all models.    

The first simple model shows a sketch of the program in the kind of way we found it. 
A complete static / procedural program, wrong comments, dead code, HN, global variables, full of duplication on many layers, switch-case bubbles, 
no way of concurrency.   

After the first cleaning and following simple rules and principles, we had the next step. 
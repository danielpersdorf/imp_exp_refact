## The Refactoring of import_export 

In this little pet project a piece of legacy code is transformed 
from total procedural, over a more object-oriented approach, to a modern way. 

The original imp_exp connects over the API to an ERP system which stores its data to a database. 
All the data of the ERP is also serializable to XML format. 
The main functions of imp_exp are: 
* import business data from XML files into that ERP system  
* export business data from ERP to XML files 
* update business data in ERP via XML files

The first Version of imp_exp was written in C# and without any UnitTests.
To demonstrate certain aspects of the program, I have built a simplified model to work with in Java.
I started this as a Spring project so all dependencies would already be there when we need them. 
That`s also why we already have a JUnitTest by default. 

For demo purposes I build in a very tiny ERP model with some behaviours the program works with. 
Those are used trough almost all models, and it saves its data as Json files.    

The first basicModel shows a sketch of the program in the kind of way we found it. 
A complete static, procedural program, wrong comments, dead code, HN, global variables, full of duplication on many layers, switch-case bubbles, 
no way of concurrency.   

After the first cleaning and following simple rules and principles, we had the first step. 
Many small extracted functions.  
A sort of readable chunk of code.  

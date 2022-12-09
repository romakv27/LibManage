General view of the command line:

java -jar  -Dlogback.<property name and path to the logger configuration> -Dconfig.<property name and the path to the property configuration> <provider type> <operation name> <command> <data>

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar

<operations>
addBook 
library 
informationReceipt 
delBook 
user
getBook

<commands>
art, scientific,  children 
addBook, del
all_user_reviews, all_user_ratings
create, get, upd, del


[id or other data]:
<user>
    for command [create]:
        [id(number)] [name(text)] [age(number)]
    for command [get]:
        [id(number)]
    for command [upd]:
        [id(number)] [name(text)] [age(number)]
    for command [del]:
        [id(number)]

<addBook>
    for command [art]:
        [id(number)] [title(text)] [author(text)] [numberOfPages(number)] [ageRestriction(number)] [direction(текст)] [comics(Boolean)]
    for command [scientific]:
        [id(number)] [title(text)] [author(text)] [numberOfPages(number)] [ageRestriction(number)] [direction(текст)] [forStudy(Boolean)]
    for command [children]:
        [id(number)] [title(text)] [author(text)] [numberOfPages(number)] [ageRestriction(number)] [direction(текст)] [comics(Boolean)] [educational(Boolean)] [interactive(Boolean)]

<getBook>
    for command [art]:
        [id(number)]
    for command [scientific]:
        [id(number)]
    for command [children]:
        [id(number)]

<library>
    for command [addBook]:
        [id(number)] [typeOfBook(text)] [bookId(number)] [userId(number)] [review(text)] [rating(number)]
    for command [del]:
        [typeOfBook(text)] [id(number)]
        
<informationReceipt>
    for command [all_user_reviews]:
        [userId(number)]
    for command [all_user_ratings]:
        [userId(number)]

<deleteBook>
    for command [art]:
        [id(number)]
    for command [scientific]:
        [id(number)]
    for command [children]:
        [id(number)]

        	
To add a book to the library you need to add a user and a book

Adding a user
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml user create 1 Ivan 16
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv user create 1 Ivan 16
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc user create 1 Ivan 16


Adding a book
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml addBook art 1 Doctor_Zhivago Pasternak 500 12 Novel false
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml addBook scientific 1 System_Design_Interview Alex_Xu 300 12 Programming false
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml addBook children 1 Arslan Filger 50 2 The_story true false true

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv addBook art 1 Doctor_Zhivago Pasternak 500 12 Novel false
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv addBook scientific 1 System_Design_Interview Alex_Xu 300 12 Programming false
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv addBook children 1 Arslan Filger 50 2 The_story true false true

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc addBook art 1 Doctor_Zhivago Pasternak 500 12 Novel false
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc addBook scientific 1 System_Design_Interview Alex_Xu 300 12 Programming false
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc addBook children 1 Arslan Filger 50 2 The_story true false true


Getting the book
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml getBook art 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml getBook scientific 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml getBook children 1

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv getBook art 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv getBook scientific 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv getBook children 1

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc getBook art 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc getBook scientific 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc getBook children 1


Adding a book to the library
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml library addBook 1 ART 1 1 Inspiring_book 5
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml library addBook 2 SCIENTIFIC 1 1 I_learned_new_things 4
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml library addBook 3 CHILDREN 1 1 Good_book 3

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv library addBook 1 ART 1 1 Inspiring_book 5
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv library addBook 2 SCIENTIFIC 1 1 I_learned_new_things 4
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv library addBook 3 CHILDREN 1 1 Good_book 3

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc library addBook 1 ART 1 1 Inspiring_book 5
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc library addBook 2 SCIENTIFIC 1 1 I_learned_new_things 4
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc library addBook 3 CHILDREN 1 1 Good_book 3


Receiving all reviews made by the user
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml informationReceipt all_user_reviews 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv informationReceipt all_user_reviews 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc informationReceipt all_user_reviews 1


Receiving all grades assigned by the user
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml informationReceipt all_user_ratings 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv informationReceipt all_user_ratings 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc informationReceipt all_user_ratings 1


Deleting a book from the library
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml library del ART 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml library del SCIENTIFIC 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml library del CHILDREN 1

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv library del ART 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv library del SCIENTIFIC 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv library del CHILDREN 1

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc library del ART 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc library del SCIENTIFIC 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc library del CHILDREN 1


Deleting a book
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml deleteBook art 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml deleteBook scientific 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml deleteBook children 1

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv deleteBook art 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv deleteBook scientific 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv deleteBook children 1

java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc deleteBook art 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc deleteBook scientific 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc deleteBook children 1


Deleting a user
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar xml user del 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar csv user del 1
java -Dlogback.configurationFile=logback.xml -Dconfig=enviroment.properties -jar library_management.jar jdbc user del 1








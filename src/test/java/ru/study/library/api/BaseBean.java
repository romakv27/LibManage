package ru.study.library.api;


import ru.study.library.model.*;

public class BaseBean {

    public User getUser(){
        return new User(1L, "Ivan", 16);
    }
    public User getUserUpdate(){
        return new User(3L, "Ivan", 6);
    }

    public User getUserCheck(){ return new User(2L, "Peter", 6); }


    public ArtBook getArtBook(){
        return new ArtBook(1L, "Doctor Zhivago", "Pasternak", 500, 12, "Novel", false);
    }

    public Scientific getScientific(){
        return new Scientific(1L, "System Design Interview", "Alex Xu", 300, 12, "Programming", false);
    }

    public Children getChildren(){
        return new Children(1L, "Kolobok", "Ushinski", 50, 2, "The story", true, false, false);
    }

    public Library getLibrary(){
        return new Library(1L,getArtBook(), getUser(), "Interesting book", (short) 5);
    }

    public Library getLibraryCheck(){
        return new Library(2L,  getScientific(),
                new User(2L, "Peter", 6), "kool comics", (short) 4);
    }

    public Library getLibraryCheckPos(){
        return new Library(3L,  getChildren(),
                getUserUpdate(), "good book", (short) 4);
    }

}

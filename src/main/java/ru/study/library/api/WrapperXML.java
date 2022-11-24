package ru.study.library.api;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class WrapperXML<T> {
    @ElementList(name ="conteiner")
    private List<T> conteiner;

    public WrapperXML(List<T> conteiner) {
        this.conteiner = conteiner;
    }
    public WrapperXML() { }

    public List<T> getList() {
        return conteiner;
    }

    public void setList(List<T> conteiner) {
        this.conteiner = conteiner;
    }
}

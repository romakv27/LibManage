package ru.study.library.api;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "xml")
public class WrapperXML<T> {
    @ElementList(inline = true, required = false)
    private List<T> container;
    public WrapperXML() { }
    public WrapperXML(List<T> container) {
        this.container = container;
    }

    public List<T> getList() {
        return container;
    }

    public void setList(List<T> container) {
        this.container = container;
    }
}

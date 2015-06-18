package ru.resolutionpoint.edu.animals.model;

import xmlstate.jaxb.Entities;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by admin on 18.06.2015.
 */
public class StateWriter {
    public StateWriter(String jaxbFile, String xmlFile){
        this.jaxbFile = jaxbFile;
        this.xmlFile = xmlFile;
    }
    private String jaxbFile;
    private String xmlFile;
    public Entities entities;
    private Unmarshaller um;

    public void readFile() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(jaxbFile);
        um = jc.createUnmarshaller();
        entities = (Entities) um.unmarshal(new File(xmlFile));
    }
    public void writeFile(Object jaxbElement) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(jaxbFile);
        Marshaller m = jc.createMarshaller();
        m.marshal(jaxbElement, new File(xmlFile));
    }
}

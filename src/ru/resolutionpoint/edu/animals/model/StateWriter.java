package ru.resolutionpoint.edu.animals.model;

import xmlstate.jaxb.Entities;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

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
    private Marshaller m;

    public Environment readFile() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Environment.class);
        um = jc.createUnmarshaller();
        Environment environment = (Environment) um.unmarshal(new File(xmlFile));
        return  environment;
    }
    public void writeFile(Environment environment) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Environment.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(environment, new File(xmlFile));
    }
}

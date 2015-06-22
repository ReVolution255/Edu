package ru.resolutionpoint.edu.animals.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class StateWriter {

    private String jaxbFile;
    private String xmlFile;
    private Unmarshaller um;
    private Marshaller m;

    public StateWriter(String jaxbFile, String xmlFile){
        this.jaxbFile = jaxbFile;
        this.xmlFile = xmlFile;
    }

    public Environment readFile() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Environment.class);
        um = jc.createUnmarshaller();
        return (Environment) um.unmarshal(new File(xmlFile));
    }

    public void writeFile(Environment environment) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Environment.class);
        m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(environment, new File(xmlFile));
    }
}

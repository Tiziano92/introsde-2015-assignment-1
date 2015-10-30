/**
 * @author	Tiziano Antico
 * This class has been made to allow the un-marshalling operation from an XML file.
 * 
 * Doing the unmarshalling of an XML document means to build an objects' tree which
 * represent the organization and the document's content.
 * The objects created are the instances of classes produced by the binding compiler.
 * 
 * 
 */

package people;

import people.generated.*;

import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

public class JAXBUnMarshaller {
	
	/**
	 * Function that un-marshal from a document
	 * @param xmlDocument
	 */
	public void unMarshall(File xmlDocument) {
		try {

			/**
			 * If you want to do the an unmarshalling operation, you need to
			 * instanciate a JAXBContext object, an Unmarshaller object and calling the
			 * method for the unmarshalling.
			 */
			
			JAXBContext jaxbContext = JAXBContext.newInstance("people.generated");

			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File("people.xsd"));
			unMarshaller.setSchema(schema);
			
			//Inizialization of the CustomValidationEventHandler for the management of custom events.
			CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
			unMarshaller.setEventHandler(validationEventHandler);

			@SuppressWarnings("unchecked")
			JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller.unmarshal(xmlDocument);

			PeopleType people = peopleElement.getValue();


			List<PersonType> personList = people.getPerson();
			
			//Print all the information in the xml document provided
			for (int i = 0; i < personList.size(); i++) {

				PersonType person = (PersonType) personList.get(i);

				List<HealthProfileType> healthProfileList = person.getHealthprofile();
				for (int j = 0; j < healthProfileList.size(); j++) {
					HealthProfileType healthProfile = (HealthProfileType) healthProfileList.get(j);

					System.out.println("Person id: "
							+ person.getId());
					System.out.println("Person firstname: " + person.getFirstname());
					System.out.println("Person lastname: " + person.getLastname());
					System.out.println("Person birthdate: " + person.getBirthdate());
					
					System.out.println("Healthprofile lastupdate: " + healthProfile.getLastupdate());
					System.out.println("Healthprofile weight: " + healthProfile.getWeight());
					System.out.println("Healthprofile height: " + healthProfile.getHeight());
					System.out.println("Healthprofile bmi: " + healthProfile.getBmi());
					System.out.println();

				}
			}
		} catch (JAXBException e) {
			System.out.println(e.toString());
		} catch (SAXException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Main to execute the un-marshalling from peopleMarshaling.xml document
	 * @param argv
	 */
	public static void main(String[] argv) {
		File xmlDocument = new File("peopleMarshaling.xml");
		JAXBUnMarshaller jaxbUnmarshaller = new JAXBUnMarshaller();

		jaxbUnmarshaller.unMarshall(xmlDocument);

	}

	
	/**
	 * 
	 * Customized events for this application. 
	 * The class implements the ValidationEventHandler interface and then, it is needed to
	 * register it with either UnMarshaller (in our case).
	 * The JAXB Provider will then report validation errors and warnings encountered during the unmarshal
	 * operations to these event handlers.
	 * 
	 */
	class CustomValidationEventHandler implements ValidationEventHandler {
		
		/**
		 * Class to manage events
		 * @return boolean
		 */
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() == ValidationEvent.WARNING) {
				return true;
			}
			if ((event.getSeverity() == ValidationEvent.ERROR)
					|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

				System.out.println("Validation Error:" + event.getMessage());

				ValidationEventLocator locator = event.getLocator();
				System.out.println("at line number:" + locator.getLineNumber());
				System.out.println("Unmarshalling Terminated");
				return false;
			}
			return true;
		}

	}
}

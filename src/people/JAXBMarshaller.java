package people;
/**
 * @author	Tiziano Antico
 * This class allows you to write into an XML document doing the Marshalling.
 * It is the process of transforming the memory representation of an object to
 * a data format suitable for storage or transmission.
 * It is something like a serialization.
 * 
 * More in general, it makes possible to serialize Java objects to XML.
 * 
 */

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;

public class JAXBMarshaller {  	
	public static PeopleStore people = new PeopleStore();

	/**
	 * Class to store the Object's information
	 */
	public static void initializeDB() {

		HealthProfile hp;
		
		//Create people to be inserted/serialized into the XML document
		
		hp = new HealthProfile("2014-09-20T18:00:00.000+02:00", 90, 1.70);
		Person george = new Person(new Long(1), "George R. R.", "Martin", "1984-09-20T18:00:00.000+02:00", hp);
		
		hp = new HealthProfile("2015-09-20T18:00:00.000+02:00", 70, 1.78);
		Person tiziano = new Person(new Long(2), "Tiziano", "Antico", "1992-04-13T18:00:00.000+02:00", hp);
		
		hp = new HealthProfile("2012-10-30T18:00:00.000+02:00", 50, 1.67);
		Person luigi = new Person(new Long(3), "Luigi", "Denitto", "1990-05-18T18:00:00.000+02:00", hp);
		
		//Adding into the list of people the (Person) objects created above
		people.getData().add(george);
		people.getData().add(tiziano);
		people.getData().add(luigi);
	}	

	/**
	 * Main method to execute the Marshaller into "peopleMarshaling.xml" file.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		
		/**
		 * If you want to serialize something, you need to specify the context, make an instance
		 * of the class Marshaller and call the marshal method. The instantiated object contains
		 * some methods to set some properties, for example "JAXB_FORMATTED_OUTPUT".
		 * 
		 */
		
		initializeDB();
		
		JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        m.marshal(people,new File("peopleMarshaling.xml")); // marshalling into a file
        m.marshal(people, System.out);			  // marshalling into the system default output
    }
}

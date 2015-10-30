package people;
/**
 * @author	Tiziano Antico
 * This class allows you to write into Json document doing the Marshalling.
 * It is the process of transforming the memory representation of an object to
 * a data format suitable for storage or transmission.
 * It is something like a serialization.
 * 
 * More in general, it makes possible to serialize Java objects to Json.
 * JSON(Javascript Object Notation) is becoming a more popular data exchange format. 
 * Normally we get the response from the server in terms of java objects.
 * Then in Servlets or Action classes we need to build the JSON from objects and send them back to client.
 */

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;

public class JAXBJson {  	
	public static PeopleStore people = new PeopleStore();

	/**
	 * Class to store the Object's information
	 */
	public static void initializeDB() {
		HealthProfile hp;
		
		//Create three people
		hp = new HealthProfile("2014-09-20T18:00:00.000+02:00", 90, 1.70);
		Person george = new Person(new Long(1), "George R. R.", "Martin", "1984-09-20T18:00:00.000+02:00", hp);
		
		hp = new HealthProfile("2015-09-20T18:00:00.000+02:00", 70, 1.78);
		Person tiziano = new Person(new Long(2), "Tiziano", "Antico", "1992-04-13T18:00:00.000+02:00", hp);
		
		hp = new HealthProfile("2012-10-30T18:00:00.000+02:00", 50, 1.67);
		Person luigi = new Person(new Long(3), "Luigi", "Denitto", "1990-05-18T18:00:00.000+02:00", hp);

		people.getData().add(george);
		people.getData().add(tiziano);
		people.getData().add(luigi);
	}	

	/**
	 * Main method to serialize the Java object into a Json file.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		initializeDB();
		
		// Jackson Object Mapper
		/**
		 * This mapper (or, data binder, or codec) provides functionality for converting between
		 * Java objects and matching JSON constructs.
		 */
		ObjectMapper mapper = new ObjectMapper();
		
		// Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        
		// configure as necessary
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(new File("peopleJson.json"), people);
    }
}

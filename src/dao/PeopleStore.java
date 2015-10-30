/**
 * @author	Tiziano Antico
 * This class has been coded to push all the Person type into a List.
 * It will be used for doing the marshalling into the class "JAXBMarshaller"
 */

package dao;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.Person;

@XmlRootElement(name="people")
@XmlAccessorType(XmlAccessType.FIELD)
public class PeopleStore {
	@XmlElement(name="person")
	private List<Person> data = new ArrayList<Person>();

	public PeopleStore () {
	}

	/**
	 * Return a List of Person
	 * @return
	 */
	public List<Person> getData() {
		return data;
	}

	/**
	 * Set a the information into a list of Person
	 * @param data
	 */
	public void setData(List<Person> data) {
		this.data = data;
	}
}

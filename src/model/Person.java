/**
 * @author	Tiziano Antico
 * This class has been made to manage all the "Person" information.
 */

package model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// XmlRootElement defines the root element of the XML tree to which this class will be serialized
// --> <person> ... </person> 
@XmlRootElement(name = "person")	
// XmlType can optionally define the order in which the fields of person are written
@XmlType(propOrder = { "firstname", "lastname", "birthdate", "healthprofile" })
// XmlAccessorType indicates what to use to create the mapping: either FIELDS, PROPERTIES (i.e., getters/setters), PUBLIC_MEMBER or NONE (which means, you should indicate manually)
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
	private String firstname;		
	private String lastname;		
	// XmlElement indicates the element to use for this field. Only used if the name in XML will be different than that in the class
	@XmlElement(name="healthprofile")
	private HealthProfile healthprofile;	
	private String birthdate;
	// XmlAttribute indicates that this field will be serialized as an attribute
	@XmlAttribute(name="id")
	private Long personId;
	
	/**
	 * Constructor method for Person object
	 * @param personId
	 * @param fname
	 * @param lname
	 * @param birthdate
	 * @param hp
	 */
	public Person(Long personId, String fname, String lname, String birthdate, HealthProfile hp) {
		this.setPersonId(personId); 	
		this.setFirstname(fname);
		this.setLastname(lname);
		this.setBirthdate(birthdate); 	
		this.healthprofile=hp;
	}
	
	/**
	 * Constructor method for Person object
	 * @param personId
	 * @param fname
	 * @param lname
	 * @param birthdate
	 */
	public Person(Long personId, String fname, String lname, String birthdate) {
		this.setPersonId(personId); 	
		this.setFirstname(fname);
		this.setLastname(lname);
		this.setBirthdate(birthdate); 
		this.healthprofile=new HealthProfile();
	}
	
	/**
	 * Constructor method for Person object
	 */
	public Person() {
		this.firstname="Pinco";
		this.lastname="Pallino";
		this.healthprofile=new HealthProfile();

		// setting personId to a random number between 1 and 9999
		this.personId = Math.round(Math.floor(Math.random()*9998)+1); // Solution to Exercise #01-1d
		this.birthdate = this.getRandomDate();
	}

	/**
	 * Return the first name of a Person object
	 * @return String
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Set the first name of a Person object
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * Return the last name of a Person object
	 * @return String
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * Set the last name of a Person object
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Return the HealthProfile of a Person object
	 * @return HealthProfile
	 */
	public HealthProfile getHProfile() {
		return healthprofile;
	}
	
	/**
	 * Set the healthProfile of a Person object
	 * @param hProfile
	 */
	public void setHProfile(HealthProfile hProfile) {
		this.healthprofile = hProfile;
	}
	
	/**
	 * Return the birthdate of a Person object
	 * @return String
	 */
	public String getBirthdate() {
		return birthdate;
	}
	
	/**
	 * Set the birthdate of a Person object
	 * @param birthdate
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	/**
	 * Return the id of a Person object
	 * @return Long
	 */
	public Long getPersonId() {
		return personId;
	}
	
	/**
	 * Set the id of a Person object
	 * @param personId
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	/**
	 * creating a random date between now and 1950
	 * @return String
	 */
	private String getRandomDate() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR); 		// 1. get the current year
		int year = (int) Math.round(Math.random()*(currentYear-1950)+1950); // 2. generate a random year 
																			//    between 1950 and currentYear
		int month = (int) Math.round(Math.floor(Math.random()*11)+1);		// 3. select a random month of the year
		int[] months = new int[]{31,28,30,30,31,30,31,31,30,31,30,31};
		if ((currentYear % 4 == 0) && ((currentYear % 100 != 0) || (currentYear % 400 == 0))) {
			months[1] = 29;
		}
		long day = Math.round(Math.floor(Math.random()*(months[month-1]-1)+1));
		return ""+year+"-"+month+"-"+day;
	}
}

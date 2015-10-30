/**
 * @author	Tiziano Antico
 * This class has been made to manage all the "HealthProfile" information.
 */

package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="healthprofile")
@XmlType(propOrder = { "lastupdate", "weight", "height", "bmi" })
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthProfile {
	private double weight; // in kg
	private double height; // in m
	@XmlElement(name="bmi")
	private double bmi;
	private String lastupdate;

	/**
	 * Constructor method for HealthProfile
	 * @param lastupdate
	 * @param weight
	 * @param height
	 * @param bmi
	 */
	public HealthProfile(String lastupdate, double weight, double height) {
		this.lastupdate = lastupdate;
		this.weight = weight;
		this.height = height;
		this.bmi = this.getBMI();
	}

	/**
	 * Constructor method for HealthProfile
	 */
	public HealthProfile() {
		this.lastupdate = "2015-09-20T18:00:00.000+02:00";
		this.weight = 85.5;
		this.height = 1.72;
		this.bmi = this.getBMI();
	}

	/**
	 * Return the weight of an HealthProfile object
	 * @return double
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * set the weight of an HealthProfile object
	 * @param weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Return the height of an HealthProfile object
	 * @return double
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Set the height of an HealthProfile object
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * Tostring method
	 * @return String
	 */
	public String toString() {
		return "Height="+height+", Weight="+weight;
	}

	/**
	 * Return the bmi of an HealthProfile object
	 * @return double
	 */
	public double getBMI() {
		return this.weight/(Math.pow(this.height, 2));
	}
}

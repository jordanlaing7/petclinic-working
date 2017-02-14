/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dom.pets;

import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.DateTime;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import dom.owners.Owner;
import dom.owners.Owners;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(
				name = "find", 
				language = "JDOQL", 
				value = "SELECT "
						+ "FROM dom.pets.Pet "),
		@javax.jdo.annotations.Query(
				name = "findByName", 
				language = "JDOQL",
				value = "SELECT "
						+ "FROM dom.pets.Pet "
						+ "WHERE name.matches(:name)"),
		@javax.jdo.annotations.Query(
				name = "findByNameIndexOf", 
				language = "JDOQL", 
				value = "SELECT "
						+ "FROM dom.pets.Pet " 
						+ "WHERE name.indexOf(:name) >= 0 ") 
})
@javax.jdo.annotations.Unique(name = "Pet_name_UNQ", members = { "name" })
@DomainObject(objectType = "PET")
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT)
public class Pet implements Comparable<Pet> {

	// region > iconName
	public String iconName() {
		return getSpecies().name();
	}

	// endregion

	// region > name (property)

	private String name;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Title(sequence = "1")
	@MemberOrder(sequence = "1")
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	// endregion

	// region > species (property)
	private PetSpecies species;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public PetSpecies getSpecies() {
		return species;
	}

	public void setSpecies(final PetSpecies species) {
		this.species = species;
	}

	// endregion

	// region > owner (property)
	// this is an OPTIONAL property
	private Owner owner;

	@javax.jdo.annotations.Column(allowsNull = "true")
	@MemberOrder(sequence = "3")
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(final Owner owner) {
		this.owner = owner;
	}

	public List<Owner> autoCompleteOwner(final String name) {
		return owners.findByName(name);
	}

	// endregion

	//region > health
	private Health health;
	@javax.jdo.annotations.Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public Health getHealth() {
		return health;
	}

	public void setHealth(final Health h) {
		this.health = h;
	}
	/*public void changeHealth()
	{
		if(this.health == Health.Healthy)
			setHealth(Health.Sick);
		else
			setHealth(Health.Healthy);
	}*/

	// endregion

	// region > compareTo

	@Override
	public int compareTo(Pet other) {
		return ObjectContracts.compare(this, other, "name");
	}

	// endregion

	private DateTime CheckIn;
	@javax.jdo.annotations.Column(allowsNull = "true")
	@MemberOrder(sequence = "5")
	public DateTime getCheckIn() {
		return CheckIn;
	}

	public void setCheckIn(final DateTime d) {
		this.CheckIn = d;
	}

	private DateTime CheckOut;
	@javax.jdo.annotations.Column(allowsNull = "true")
	@MemberOrder(sequence = "6")
	public DateTime getCheckOut() {
		return CheckOut;
	}

	public void setCheckOut(final DateTime d) {
		this.CheckOut = d;
	}

	private String notes;
	@javax.jdo.annotations.Column(allowsNull = "true")
	@MemberOrder(sequence = "7")
	public String getNotes(){return notes;}

	public void setNotes(final String note)
	{
		this.notes = note;
	}

	// region > injected services

	@javax.inject.Inject
	@SuppressWarnings("unused")
	private DomainObjectContainer container;

	@javax.inject.Inject
	private Owners owners;
	// endregion



}


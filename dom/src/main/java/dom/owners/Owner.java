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
package dom.owners;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import dom.pets.Pet;
import dom.pets.PetSpecies;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Queries( {
        @javax.jdo.annotations.Query(
                name = "findOwnerByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM dom.owners.Owner "
                        + "WHERE name.matches(:name)")
})
@javax.jdo.annotations.Unique(name="Owner_name_UNQ", members = {"name"})
@DomainObject(objectType = "OWNER")
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT)
public class Owner implements Comparable<Owner> {

    //region > name (property)

    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //endregion
    
    // {{ Pets (Collection)
    @javax.jdo.annotations.Persistent(mappedBy="owner", dependentElement = "false")
	private SortedSet<Pet> pets = new TreeSet<Pet>();

	@MemberOrder(sequence = "2")
	@CollectionLayout(render=RenderType.EAGERLY)
	public SortedSet<Pet> getPets() {
		return pets;
	}

	public void setPets(final SortedSet<Pet> pets) {
		this.pets = pets;
	}
	// }}

    //region > compareTo

    @Override
    public int compareTo(final Owner other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    // {{ AddPet (action)
	@MemberOrder(sequence = "1")
	public Owner addPet(
			@ParameterLayout(named="Name") final String name, 
			@ParameterLayout(named="Species") final PetSpecies species) {
		Pet pet = container.newTransientInstance(Pet.class);
		pet.setName(name);
		pet.setSpecies(species);
		container.persist(pet);
		pets.add(pet);
		return this;
	}
	// }}


    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion

}

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

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.query.QueryDefault;

import dom.owners.Owner;

@DomainService(repositoryFor = Pet.class)
@DomainServiceLayout(menuOrder = "10")
public class Pets {

    //region > listAll (action)

    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            describedAs="Lists all the pets currently in the Animal Hospital")
    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "1")
    public List<Pet> listAll() {
        return container.allInstances(Pet.class);
    }

    //endregion

    //region > create (action)
    /*
     * Do NOT add owner at creation
     */
    @ActionLayout(contributed=Contributed.AS_ASSOCIATION)
    @MemberOrder(sequence = "2")
    public Pet create(
            final @ParameterLayout(named = "Name") String name
            , final PetSpecies species)
    {
        final Pet pet = container.newTransientInstance(Pet.class);
        pet.setName(name);
        pet.setSpecies(species);
        pet.setHealth(Health.Sick);
        container.persistIfNotAlready(pet);
        return pet;
    }

    //endregion

    //region > findByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            describedAs="Enter the name of the pet you want to see (case-sensitive)"
    )
    public List<Pet> findByName(
        @ParameterLayout(named = "Name")
        final String name) {
        final String nameArg = String.format(".*%s.*", name);
        final List<Pet> pets = container.allMatches(
                new QueryDefault<>(
                        Pet.class,
                        "findByName",
                        "name", nameArg));
        return pets;
    }
    //endregion

    //region > delete (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            describedAs="Enter the name of the pet you want to delete (case-sensitive)")
    public void delete(
        @ParameterLayout(named = "Name")
        final String name)
    {
        final String nameArg = String.format(".*%s.*", name);
        Query query = new QueryDefault<>(
                Pet.class,
                "findByName",
                "name", nameArg);
        final List<Pet> pets = container.allMatches(query);
       if(pets.size() == 0)
        {
            throw new IllegalArgumentException(String.format("No pets found with name '%s'", name));
        }

        container.removeIfNotAlready(pets.get(0));
    }
    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}

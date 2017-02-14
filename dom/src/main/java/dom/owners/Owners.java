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

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.query.QueryDefault;

import dom.pets.Pet;

@DomainService(repositoryFor = Owner.class)
@DomainServiceLayout(menuOrder = "20")
public class Owners {

    //region > listAll (action)

    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT )
    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "1")
    public List<Owner> listAll() {
        return container.allInstances(Owner.class);
    }

    //endregion

    //region > create (action)
    @MemberOrder(sequence = "2")
    public Owner create(
            final @ParameterLayout(named = "Name") String name) {
        final Owner obj = container.newTransientInstance(Owner.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > findByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    public List<Owner> findByName(
            @ParameterLayout(named = "Name")
            final String name) {
        final String nameArg = String.format(".*%s.*", name);
        final List<Owner> owners = container.allMatches(
                new QueryDefault<>(
                        Owner.class,
                        "findOwnerByName",
                        "name", nameArg));
        return owners;
    }
    //endregion

    //region > delete (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            describedAs="Enter the name of the owner you want to delete (case-sensitive)")
    public void delete(
            @ParameterLayout(named = "Name")
            final String name)
    {
        final String nameArg = String.format(".*%s.*", name);
        Query query = new QueryDefault<>(
                Owner.class,
                "findOwnerByName",
                "name", nameArg);
        final List<Owner> owners = container.allMatches(query);
        if(owners.size() == 0)
        {
            //throw new IllegalArgumentException(String.format("No owners found with name '%s'", name));
        }
        container.removeIfNotAlready(owners.get(0));
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    DomainObjectContainer container;
    //endregion

}

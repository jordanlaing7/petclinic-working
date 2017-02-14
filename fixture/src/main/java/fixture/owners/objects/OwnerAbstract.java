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

package fixture.owners.objects;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import dom.owners.Owner;
import dom.owners.Owners;
import dom.pets.Pet;
import dom.pets.Pets;

public abstract class OwnerAbstract extends FixtureScript {

    protected Owner create(
            final String name,
            final Pet pet,
            final ExecutionContext executionContext) {
        Owner owner = owners.create(name);
        if (pet != null) {
        	owner.getPets().add(pet);
        }
        return executionContext.addResult(this, owner);
    }
    
    /**
     * for convenience of subclasses
     */
    protected Pet findPet(final String name) {
        final List<Pet> pets = this.pets.findByName(name);
        switch (pets.size()) {
            case 0:
                throw new IllegalArgumentException(String.format("No pets found with name '%s'", name));
            case 1:
                return pets.get(0);
            default:
                throw new IllegalArgumentException(String.format("Found %d matching name '%s'", pets.size(), name));
        }
    }


    @javax.inject.Inject
    private Owners owners;

    @javax.inject.Inject
    private Pets pets;

}

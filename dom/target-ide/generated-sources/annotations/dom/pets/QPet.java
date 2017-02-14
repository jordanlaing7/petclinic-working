package dom.pets;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QPet extends org.datanucleus.api.jdo.query.PersistableExpressionImpl<Pet> implements PersistableExpression<Pet>
{
    public static final QPet jdoCandidate = candidate("this");

    public static QPet candidate(String name)
    {
        return new QPet(null, name, 5);
    }

    public static QPet candidate()
    {
        return jdoCandidate;
    }

    public static QPet parameter(String name)
    {
        return new QPet(Pet.class, name, ExpressionType.PARAMETER);
    }

    public static QPet variable(String name)
    {
        return new QPet(Pet.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;
    public final ObjectExpression<dom.pets.PetSpecies> species;
    public final dom.owners.QOwner owner;
    public final ObjectExpression<dom.pets.Health> health;
    public final ObjectExpression<org.apache.isis.applib.DomainObjectContainer> container;
    public final ObjectExpression<dom.owners.Owners> owners;

    public QPet(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
        this.species = new ObjectExpressionImpl<dom.pets.PetSpecies>(this, "species");
        if (depth > 0)
        {
            this.owner = new dom.owners.QOwner(this, "owner", depth-1);
        }
        else
        {
            this.owner = null;
        }
        this.health = new ObjectExpressionImpl<dom.pets.Health>(this, "health");
        this.container = new ObjectExpressionImpl<org.apache.isis.applib.DomainObjectContainer>(this, "container");
        this.owners = new ObjectExpressionImpl<dom.owners.Owners>(this, "owners");
    }

    public QPet(Class type, String name, org.datanucleus.api.jdo.query.ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
        this.species = new ObjectExpressionImpl<dom.pets.PetSpecies>(this, "species");
        this.owner = new dom.owners.QOwner(this, "owner", 5);
        this.health = new ObjectExpressionImpl<dom.pets.Health>(this, "health");
        this.container = new ObjectExpressionImpl<org.apache.isis.applib.DomainObjectContainer>(this, "container");
        this.owners = new ObjectExpressionImpl<dom.owners.Owners>(this, "owners");
    }
}

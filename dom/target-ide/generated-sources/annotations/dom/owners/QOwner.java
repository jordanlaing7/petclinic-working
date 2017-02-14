package dom.owners;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QOwner extends org.datanucleus.api.jdo.query.PersistableExpressionImpl<Owner> implements PersistableExpression<Owner>
{
    public static final QOwner jdoCandidate = candidate("this");

    public static QOwner candidate(String name)
    {
        return new QOwner(null, name, 5);
    }

    public static QOwner candidate()
    {
        return jdoCandidate;
    }

    public static QOwner parameter(String name)
    {
        return new QOwner(Owner.class, name, ExpressionType.PARAMETER);
    }

    public static QOwner variable(String name)
    {
        return new QOwner(Owner.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;
    public final CollectionExpression pets;
    public final ObjectExpression<org.apache.isis.applib.DomainObjectContainer> container;

    public QOwner(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
        this.pets = new CollectionExpressionImpl(this, "pets");
        this.container = new ObjectExpressionImpl<org.apache.isis.applib.DomainObjectContainer>(this, "container");
    }

    public QOwner(Class type, String name, org.datanucleus.api.jdo.query.ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
        this.pets = new CollectionExpressionImpl(this, "pets");
        this.container = new ObjectExpressionImpl<org.apache.isis.applib.DomainObjectContainer>(this, "container");
    }
}

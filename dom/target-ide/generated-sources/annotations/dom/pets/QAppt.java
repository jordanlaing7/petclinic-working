package dom.pets;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QAppt extends org.datanucleus.api.jdo.query.PersistableExpressionImpl<Appt> implements PersistableExpression<Appt>
{
    public static final QAppt jdoCandidate = candidate("this");

    public static QAppt candidate(String name)
    {
        return new QAppt(null, name, 5);
    }

    public static QAppt candidate()
    {
        return jdoCandidate;
    }

    public static QAppt parameter(String name)
    {
        return new QAppt(Appt.class, name, ExpressionType.PARAMETER);
    }

    public static QAppt variable(String name)
    {
        return new QAppt(Appt.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;
    public final ObjectExpression<org.joda.time.DateTime> date;

    public QAppt(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
        this.date = new ObjectExpressionImpl<org.joda.time.DateTime>(this, "date");
    }

    public QAppt(Class type, String name, org.datanucleus.api.jdo.query.ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
        this.date = new ObjectExpressionImpl<org.joda.time.DateTime>(this, "date");
    }
}

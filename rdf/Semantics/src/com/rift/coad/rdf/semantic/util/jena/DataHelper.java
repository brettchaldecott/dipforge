/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  2015 Burntjam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * SPARQLList.java
 */
// package path
package com.rift.coad.rdf.semantic.util.jena;

// java objects
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;

// jena import
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;

// coadunation imports
import com.rift.coad.rdf.semantic.util.DateHelper;

/**
 * This class has been copied from the Jena Bean projects, as dates can be
 * handled slightly differently in this projects. This object is responsible for
 *
 * @author brett chaldecott
 */
public class DataHelper {

    /**
     * This method converts the literal to the specified object type.
     *
     * @param node The rdf node to convert from.
     * @param c The object to convert to.
     * @return The result.
     */
    public static Object convertLiteral(RDFNode node, Class<?> c) {
        return convertLiteral((Literal) node.as(Literal.class), c);
    }

    /**
     * This method converts the literal.
     *
     * @param l The literal value to convert.
     * @param c The type to convert to.
     * @return The result object.
     */
    public static Object convertLiteral(Literal l, Class<?> c) {
        
        if (c.equals(String.class)) {
            return l.getString();
        } else if (c.equals(Date.class)) {
            return date(l);
        } else if (c.equals(Calendar.class)) {
            return ((XSDDateTime) l.getValue()).asCalendar();
        } else if (c.equals(BigDecimal.class)) {
            return bigDecimal(l);
        } else if (Integer.TYPE.equals(c)) {
            return l.getInt();
        } else if (Long.TYPE.equals(c)) {
            return l.getLong();
        } else if (Double.TYPE.equals(c)) {
            return l.getDouble();
        } else if (Character.TYPE.equals(c)) {
            return l.getValue().toString().charAt(0);
        } else if (Short.TYPE.equals(c)) {
            return l.getShort();
        } else if (c.equals(Integer.class)) {
            return l.getInt();
        } else if (c.equals(Long.class)) {
            return l.getLong();
        } else if (c.equals(Double.class)) {
            return l.getDouble();
        } else if (c.equals(Character.class)) {
            return l.getValue().toString().charAt(0);
        } else if (c.equals(Short.class)) {
            return l.getShort();
        } else {
            return l.getValue();
        }
    }

    /**
     * This method returns the literal as a date object.
     *
     * @param l The literal to perform the conversion on.
     * @return The date object.
     */
    public static Date date(Literal l) {
        try {
            XSDDateTime date = (XSDDateTime) l.getValue();
            return date.asCalendar().getTime();
        } catch (Exception ex) {
            try {
                return DateHelper.parse(l.getString());
            } catch (Exception ex2) {
                return null;
            }
        }
    }

    /**
     * This method is responsible for converting a liter to a big decimal.
     *
     * @param l The literal to perform the conversion on.
     * @return The result object.
     */
    public static Object bigDecimal(Literal l) {
        Object o = l.getDouble();
        return o;
    }

    public static Literal asLiteral(RDFNode n) {
        return (Literal) n.as(Literal.class);
    }

    /**
     * Convert the object to a literal.
     *
     * @param m The model this query is attached to.
     * @param o The object to perform the conversion on.
     * @return The resultant literal
     */
    public static Literal toLiteral(Model m, Object o) {
        if (o instanceof String) {
            return m.createTypedLiteral(o.toString());
        } else if (o instanceof Date) {
            Calendar c = Calendar.getInstance();
            c.setTime((Date) o);
            return m.createTypedLiteral(c);
        } else if (o instanceof Integer) {
            return m.createTypedLiteral(((Integer) o).intValue());
        } else if (o instanceof Long) {
            return m.createTypedLiteral(((Long) o).longValue());
        } else if (o instanceof Short) {
            return m.createTypedLiteral((Short) o);
        } else if (o instanceof Float) {
            return m.createTypedLiteral(((Float) o).floatValue());
        } else if (o instanceof Double) {
            return m.createTypedLiteral(((Double) o).doubleValue());
        } else if (o instanceof Character) {
            return m.createTypedLiteral(((Character) o).charValue());
        } else if (o instanceof Boolean) {
            return m.createTypedLiteral(((Boolean) o).booleanValue());
        } else if (o instanceof Calendar) {
            return m.createTypedLiteral((Calendar) o);
        } else if (o instanceof BigDecimal) {
            return m.createTypedLiteral(((BigDecimal) o).doubleValue(), XSDDatatype.XSDdouble);
        } else if (o instanceof BigInteger) {
            return m.createTypedLiteral((BigInteger) o);
        } else if (o instanceof URI) {
            return m.createTypedLiteral(o, XSDDatatype.XSDanyURI);
        }
        return null;
    }

    /**
     * This method returns true if the object is of type basic.
     *
     * @param c The class to perform the check on.
     * @return TRUE if the object is of basic type, FALSE if not.
     */
    public static boolean isBasic(Class<?> c) {
        if (c.equals(String.class)) {
            return true;
        } else if (c.equals(Date.class)) {
            return true;
        } else if (c.equals(Calendar.class)) {
            return true;
        } else if (c.equals(BigDecimal.class)) {
            return true;
        } else if (Integer.TYPE.equals(c)) {
            return true;
        } else if (Long.TYPE.equals(c)) {
            return true;
        } else if (Double.TYPE.equals(c)) {
            return true;
        } else if (Character.TYPE.equals(c)) {
            return true;
        } else if (c.equals(Short.class)) {
            return true;
        } else if (c.equals(Integer.class)) {
            return true;
        } else if (c.equals(Long.class)) {
            return true;
        } else if (c.equals(Double.class)) {
            return true;
        } else if (c.equals(Character.class)) {
            return true;
        } else if (c.equals(Short.class)) {
            return true;
        }
        return false;
    }
}

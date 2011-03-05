/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * BasicJDOList.java
 */

package com.rift.coad.rdf.semantic.jdo.basic.collection;

import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This is the implementation of the basic jdo list.
 * 
 * @author brett chaldecot
 */
public class BasicJDOList<T> implements List {

    private List<T> base;
    private PersistanceSession persistanceSession;
    private OntologySession ontologySession;
    private PersistanceIdentifier identifier;


    /**
     * The constructor of the Basic JDO list object.
     *
     * @param base The memory version of the basic jdo list.
     * @param persistanceSession The persistance session.
     * @param ontologySession The ontology session.
     */
    public BasicJDOList(List<T> base, PersistanceSession persistanceSession,
            OntologySession ontologySession, PersistanceIdentifier identifier) {
        this.base = base;
        this.persistanceSession = persistanceSession;
        this.ontologySession = ontologySession;
        this.identifier = identifier;
    }


    /**
     * This method returns the size of the basic JDO list.
     *
     * @return The size of the jdo list.
     */
    public int size() {
        return base.size();
    }


    /**
     * This method returns true if empty.
     *
     * @return TRUE if empty FALSE if not.
     */
    public boolean isEmpty() {
        return base.isEmpty();
    }


    /**
     * This method returns true if the list contains the specified object.
     *
     * @param o The object to check for in the list.
     * @return TRUE if found, FALSE if not.
     */
    public boolean contains(Object o) {
        return base.contains(o);
    }


    /**
     * This method returns the iterator.
     *
     * @return The iterator to the internal values.
     */
    public Iterator<T> iterator() {
        return base.iterator();
    }


    /**
     * This method returns an array of the internal values.
     *
     * @return This method returns an array of the internal objects.
     */
    public T[] toArray() {
        return (T[])base.toArray();
    }


    /**
     * This method returns the array based on the type information passed in.
     *
     * @param a The the type method.
     * @return The resulting array list.
     */
    public T[] toArray(Object[] a) {
        return (T[])base.toArray(a);
    }

    public boolean add(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * This method returns true if the target contains the collections list.
     *
     * @param c The collections list to check for.
     * @return TRUE if found.
     */
    public boolean containsAll(Collection c) {
        return base.containsAll(c);
    }

    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This method returns the object identified by the index.
     *
     * @param index The index of the object to retrieve.
     * @return The reference to the object.
     */
    public T get(int index) {
        return base.get(index);
    }

    public Object set(int index, Object element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(int index, Object element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T remove(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * This method returns the index of the given object.
     *
     * @param o The given object to look for.
     * @return The index of the object to look for.
     */
    public int indexOf(Object o) {
        return base.indexOf(o);
    }


    /**
     * This method returns the last index of the given object.
     *
     * @param o The index of the given object.
     * @return The index number/
     */
    public int lastIndexOf(Object o) {
        return base.lastIndexOf(o);
    }


    /**
     * This method returns the list iterator.
     *
     * @return The list iterator.
     */
    public ListIterator<T> listIterator() {
        return base.listIterator();
    }


    /**
     * This method returns the list iterator.
     *
     * @param index The starting point for the list iterator.
     * @return The list iterator value
     */
    public ListIterator<T> listIterator(int index) {
        return base.listIterator(index);
    }


    /**
     * This method returns the sub list.
     *
     * @param fromIndex The index position to start from.
     * @param toIndex The index position to end at.
     * @return
     */
    public List<T> subList(int fromIndex, int toIndex) {
        return base.subList(fromIndex, toIndex);
    }


}

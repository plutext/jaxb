package com.sun.xml.bind.v2.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.namespace.QName;

import com.sun.xml.bind.v2.model.core.MapPropertyInfo;
import com.sun.xml.bind.v2.model.core.PropertyKind;
import com.sun.xml.bind.v2.model.core.TypeInfo;
import com.sun.xml.bind.v2.model.core.NonElement;

/**
 * @author Kohsuke Kawaguchi
 */
class MapPropertyInfoImpl<T,C,F,M> extends PropertyInfoImpl<T,C,F,M> implements MapPropertyInfo<T,C> {

    private final QName xmlName;
    private boolean nil;
    private final T keyType;
    private final T valueType;

    // laziy computed to handle cyclic references
    private NonElement<T,C> keyTypeInfo;
    private NonElement<T,C> valueTypeInfo;


    public MapPropertyInfoImpl(ClassInfoImpl<T,C,F,M> ci, PropertySeed<T,C,F,M> seed) {
        super(ci, seed);

        XmlElementWrapper xe = seed.readAnnotation(XmlElementWrapper.class);
        xmlName = calcXmlName(xe);
        nil = xe!=null && xe.nillable();

        T raw = getRawType();
        T bt = nav().getBaseClass(raw, nav().asDecl(Map.class) );
        assert bt!=null;    // Map property is only for Maps

        if(nav().isParameterizedType(bt)) {
            keyType = nav().getTypeArgument(bt,0);
            valueType = nav().getTypeArgument(bt,1);
        } else {
            keyType = valueType = nav().ref(Object.class);
        }
    }

    public Collection<? extends TypeInfo<T,C>> ref() {
        List<TypeInfo<T,C>> r = new ArrayList<TypeInfo<T,C>>();
        r.add(getKeyType());
        r.add(getValueType());
        return r;
    }

    public final PropertyKind kind() {
        return PropertyKind.MAP;
    }

    public QName getXmlName() {
        return xmlName;
    }

    public boolean isCollectionNillable() {
        return nil;
    }

    public NonElement<T,C> getKeyType() {
        if(keyTypeInfo==null)
            keyTypeInfo = getTarget(keyType);
        return keyTypeInfo;
    }

    public NonElement<T,C> getValueType() {
        if(valueTypeInfo==null)
            valueTypeInfo = getTarget(valueType);
        return valueTypeInfo;
    }

    public NonElement<T,C> getTarget(T type) {
        assert parent.builder!=null : "this method must be called during the build stage";
        return parent.builder.getTypeInfo(type,this);
    }
}

/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package net.morimekta.test.thrift.serialization.deep;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class ThreeLevels implements org.apache.thrift.TBase<ThreeLevels, ThreeLevels._Fields>, java.io.Serializable, Cloneable, Comparable<ThreeLevels> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ThreeLevels");

  private static final org.apache.thrift.protocol.TField TWO1_FIELD_DESC = new org.apache.thrift.protocol.TField("two1", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField TWO2_FIELD_DESC = new org.apache.thrift.protocol.TField("two2", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ThreeLevelsStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ThreeLevelsTupleSchemeFactory();

  private TwoLevels two1; // optional
  private TwoLevels two2; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TWO1((short)1, "two1"),
    TWO2((short)2, "two2");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TWO1
          return TWO1;
        case 2: // TWO2
          return TWO2;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.TWO1,_Fields.TWO2};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TWO1, new org.apache.thrift.meta_data.FieldMetaData("two1", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TwoLevels.class)));
    tmpMap.put(_Fields.TWO2, new org.apache.thrift.meta_data.FieldMetaData("two2", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TwoLevels.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ThreeLevels.class, metaDataMap);
  }

  public ThreeLevels() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThreeLevels(ThreeLevels other) {
    if (other.isSetTwo1()) {
      this.two1 = new TwoLevels(other.two1);
    }
    if (other.isSetTwo2()) {
      this.two2 = new TwoLevels(other.two2);
    }
  }

  public ThreeLevels deepCopy() {
    return new ThreeLevels(this);
  }

  @Override
  public void clear() {
    this.two1 = null;
    this.two2 = null;
  }

  public TwoLevels getTwo1() {
    return this.two1;
  }

  public ThreeLevels setTwo1(TwoLevels two1) {
    this.two1 = two1;
    return this;
  }

  public void unsetTwo1() {
    this.two1 = null;
  }

  /** Returns true if field two1 is set (has been assigned a value) and false otherwise */
  public boolean isSetTwo1() {
    return this.two1 != null;
  }

  public void setTwo1IsSet(boolean value) {
    if (!value) {
      this.two1 = null;
    }
  }

  public TwoLevels getTwo2() {
    return this.two2;
  }

  public ThreeLevels setTwo2(TwoLevels two2) {
    this.two2 = two2;
    return this;
  }

  public void unsetTwo2() {
    this.two2 = null;
  }

  /** Returns true if field two2 is set (has been assigned a value) and false otherwise */
  public boolean isSetTwo2() {
    return this.two2 != null;
  }

  public void setTwo2IsSet(boolean value) {
    if (!value) {
      this.two2 = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TWO1:
      if (value == null) {
        unsetTwo1();
      } else {
        setTwo1((TwoLevels)value);
      }
      break;

    case TWO2:
      if (value == null) {
        unsetTwo2();
      } else {
        setTwo2((TwoLevels)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TWO1:
      return getTwo1();

    case TWO2:
      return getTwo2();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TWO1:
      return isSetTwo1();
    case TWO2:
      return isSetTwo2();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ThreeLevels)
      return this.equals((ThreeLevels)that);
    return false;
  }

  public boolean equals(ThreeLevels that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_two1 = true && this.isSetTwo1();
    boolean that_present_two1 = true && that.isSetTwo1();
    if (this_present_two1 || that_present_two1) {
      if (!(this_present_two1 && that_present_two1))
        return false;
      if (!this.two1.equals(that.two1))
        return false;
    }

    boolean this_present_two2 = true && this.isSetTwo2();
    boolean that_present_two2 = true && that.isSetTwo2();
    if (this_present_two2 || that_present_two2) {
      if (!(this_present_two2 && that_present_two2))
        return false;
      if (!this.two2.equals(that.two2))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTwo1()) ? 131071 : 524287);
    if (isSetTwo1())
      hashCode = hashCode * 8191 + two1.hashCode();

    hashCode = hashCode * 8191 + ((isSetTwo2()) ? 131071 : 524287);
    if (isSetTwo2())
      hashCode = hashCode * 8191 + two2.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ThreeLevels other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetTwo1()).compareTo(other.isSetTwo1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTwo1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.two1, other.two1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTwo2()).compareTo(other.isSetTwo2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTwo2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.two2, other.two2);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ThreeLevels(");
    boolean first = true;

    if (isSetTwo1()) {
      sb.append("two1:");
      if (this.two1 == null) {
        sb.append("null");
      } else {
        sb.append(this.two1);
      }
      first = false;
    }
    if (isSetTwo2()) {
      if (!first) sb.append(", ");
      sb.append("two2:");
      if (this.two2 == null) {
        sb.append("null");
      } else {
        sb.append(this.two2);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (two1 != null) {
      two1.validate();
    }
    if (two2 != null) {
      two2.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ThreeLevelsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ThreeLevelsStandardScheme getScheme() {
      return new ThreeLevelsStandardScheme();
    }
  }

  private static class ThreeLevelsStandardScheme extends org.apache.thrift.scheme.StandardScheme<ThreeLevels> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ThreeLevels struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TWO1
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.two1 = new TwoLevels();
              struct.two1.read(iprot);
              struct.setTwo1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TWO2
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.two2 = new TwoLevels();
              struct.two2.read(iprot);
              struct.setTwo2IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ThreeLevels struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.two1 != null) {
        if (struct.isSetTwo1()) {
          oprot.writeFieldBegin(TWO1_FIELD_DESC);
          struct.two1.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.two2 != null) {
        if (struct.isSetTwo2()) {
          oprot.writeFieldBegin(TWO2_FIELD_DESC);
          struct.two2.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ThreeLevelsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ThreeLevelsTupleScheme getScheme() {
      return new ThreeLevelsTupleScheme();
    }
  }

  private static class ThreeLevelsTupleScheme extends org.apache.thrift.scheme.TupleScheme<ThreeLevels> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ThreeLevels struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTwo1()) {
        optionals.set(0);
      }
      if (struct.isSetTwo2()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTwo1()) {
        struct.two1.write(oprot);
      }
      if (struct.isSetTwo2()) {
        struct.two2.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ThreeLevels struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.two1 = new TwoLevels();
        struct.two1.read(iprot);
        struct.setTwo1IsSet(true);
      }
      if (incoming.get(1)) {
        struct.two2 = new TwoLevels();
        struct.two2.read(iprot);
        struct.setTwo2IsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}


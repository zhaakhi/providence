/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package net.morimekta.test.thrift.serialization.deep;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class DeepStructure implements org.apache.thrift.TBase<DeepStructure, DeepStructure._Fields>, java.io.Serializable, Cloneable, Comparable<DeepStructure> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DeepStructure");

  private static final org.apache.thrift.protocol.TField THREE1_FIELD_DESC = new org.apache.thrift.protocol.TField("three1", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField THREE2_FIELD_DESC = new org.apache.thrift.protocol.TField("three2", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new DeepStructureStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new DeepStructureTupleSchemeFactory();

  private ThreeLevels three1; // optional
  private ThreeLevels three2; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    THREE1((short)1, "three1"),
    THREE2((short)2, "three2");

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
        case 1: // THREE1
          return THREE1;
        case 2: // THREE2
          return THREE2;
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
  private static final _Fields optionals[] = {_Fields.THREE1,_Fields.THREE2};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.THREE1, new org.apache.thrift.meta_data.FieldMetaData("three1", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ThreeLevels.class)));
    tmpMap.put(_Fields.THREE2, new org.apache.thrift.meta_data.FieldMetaData("three2", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ThreeLevels.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DeepStructure.class, metaDataMap);
  }

  public DeepStructure() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DeepStructure(DeepStructure other) {
    if (other.isSetThree1()) {
      this.three1 = new ThreeLevels(other.three1);
    }
    if (other.isSetThree2()) {
      this.three2 = new ThreeLevels(other.three2);
    }
  }

  public DeepStructure deepCopy() {
    return new DeepStructure(this);
  }

  @Override
  public void clear() {
    this.three1 = null;
    this.three2 = null;
  }

  public ThreeLevels getThree1() {
    return this.three1;
  }

  public DeepStructure setThree1(ThreeLevels three1) {
    this.three1 = three1;
    return this;
  }

  public void unsetThree1() {
    this.three1 = null;
  }

  /** Returns true if field three1 is set (has been assigned a value) and false otherwise */
  public boolean isSetThree1() {
    return this.three1 != null;
  }

  public void setThree1IsSet(boolean value) {
    if (!value) {
      this.three1 = null;
    }
  }

  public ThreeLevels getThree2() {
    return this.three2;
  }

  public DeepStructure setThree2(ThreeLevels three2) {
    this.three2 = three2;
    return this;
  }

  public void unsetThree2() {
    this.three2 = null;
  }

  /** Returns true if field three2 is set (has been assigned a value) and false otherwise */
  public boolean isSetThree2() {
    return this.three2 != null;
  }

  public void setThree2IsSet(boolean value) {
    if (!value) {
      this.three2 = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case THREE1:
      if (value == null) {
        unsetThree1();
      } else {
        setThree1((ThreeLevels)value);
      }
      break;

    case THREE2:
      if (value == null) {
        unsetThree2();
      } else {
        setThree2((ThreeLevels)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case THREE1:
      return getThree1();

    case THREE2:
      return getThree2();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case THREE1:
      return isSetThree1();
    case THREE2:
      return isSetThree2();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof DeepStructure)
      return this.equals((DeepStructure)that);
    return false;
  }

  public boolean equals(DeepStructure that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_three1 = true && this.isSetThree1();
    boolean that_present_three1 = true && that.isSetThree1();
    if (this_present_three1 || that_present_three1) {
      if (!(this_present_three1 && that_present_three1))
        return false;
      if (!this.three1.equals(that.three1))
        return false;
    }

    boolean this_present_three2 = true && this.isSetThree2();
    boolean that_present_three2 = true && that.isSetThree2();
    if (this_present_three2 || that_present_three2) {
      if (!(this_present_three2 && that_present_three2))
        return false;
      if (!this.three2.equals(that.three2))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetThree1()) ? 131071 : 524287);
    if (isSetThree1())
      hashCode = hashCode * 8191 + three1.hashCode();

    hashCode = hashCode * 8191 + ((isSetThree2()) ? 131071 : 524287);
    if (isSetThree2())
      hashCode = hashCode * 8191 + three2.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(DeepStructure other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetThree1()).compareTo(other.isSetThree1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThree1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.three1, other.three1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetThree2()).compareTo(other.isSetThree2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThree2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.three2, other.three2);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("DeepStructure(");
    boolean first = true;

    if (isSetThree1()) {
      sb.append("three1:");
      if (this.three1 == null) {
        sb.append("null");
      } else {
        sb.append(this.three1);
      }
      first = false;
    }
    if (isSetThree2()) {
      if (!first) sb.append(", ");
      sb.append("three2:");
      if (this.three2 == null) {
        sb.append("null");
      } else {
        sb.append(this.three2);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (three1 != null) {
      three1.validate();
    }
    if (three2 != null) {
      three2.validate();
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

  private static class DeepStructureStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DeepStructureStandardScheme getScheme() {
      return new DeepStructureStandardScheme();
    }
  }

  private static class DeepStructureStandardScheme extends org.apache.thrift.scheme.StandardScheme<DeepStructure> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DeepStructure struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // THREE1
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.three1 = new ThreeLevels();
              struct.three1.read(iprot);
              struct.setThree1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // THREE2
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.three2 = new ThreeLevels();
              struct.three2.read(iprot);
              struct.setThree2IsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DeepStructure struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.three1 != null) {
        if (struct.isSetThree1()) {
          oprot.writeFieldBegin(THREE1_FIELD_DESC);
          struct.three1.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.three2 != null) {
        if (struct.isSetThree2()) {
          oprot.writeFieldBegin(THREE2_FIELD_DESC);
          struct.three2.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DeepStructureTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DeepStructureTupleScheme getScheme() {
      return new DeepStructureTupleScheme();
    }
  }

  private static class DeepStructureTupleScheme extends org.apache.thrift.scheme.TupleScheme<DeepStructure> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DeepStructure struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetThree1()) {
        optionals.set(0);
      }
      if (struct.isSetThree2()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetThree1()) {
        struct.three1.write(oprot);
      }
      if (struct.isSetThree2()) {
        struct.three2.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DeepStructure struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.three1 = new ThreeLevels();
        struct.three1.read(iprot);
        struct.setThree1IsSet(true);
      }
      if (incoming.get(1)) {
        struct.three2 = new ThreeLevels();
        struct.three2.read(iprot);
        struct.setThree2IsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}


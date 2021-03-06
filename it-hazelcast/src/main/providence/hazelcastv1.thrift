namespace java net.morimekta.test.hazelcast.v1

const i32 FACTORY_ID = 123456;

enum Fibonacchi {
   FIRST = 1,
   SECOND = 2,
   THIRD = 3,
   FOURTH = 5,
   FIFTH = 8,
   SIXTH = 13,
   SEVENTH = 21,
   EIGHTH = 34,
   NINTH = 55,
   TENTH = 89,
   ELEVENTH = 144,
   TWELWETH = 233,
   THIRTEENTH = 377,
   FOURTEENTH = 610,
   FIFTEENTH = 987,
   SIXTEENTH = 1597,
   SEVENTEENTH = 2584,
   EIGHTEENTH = 4181,
   NINTEENTH = 6765,
   TWENTIETH = 10946
}

/* struct Compact {
    1: required string name
    2: required i32 id,
    3: string label;
} (json.compact = "", hazelcast.class.id = "1") */

struct PortableFields {
    1: optional bool booleanValue;
    2: optional byte byteValue;
    3: optional i16 shortValue;
    4: optional i32 integerValue;
    5: optional i64 longValue;
    6: optional double doubleValue;
    // 7: optional string stringValue;
    8: optional binary binaryValue;
    9: optional Fibonacchi enumValue;
    // 10: optional Compact compactValue;
    // 12: optional NotPortableFields notPortableValue;
} (hazelcast.class.id = "2")

struct PortableListFields {
    1: optional list<bool> booleanValues;
    2: optional list<byte> byteValues;
    3: optional list<i16> shortValues;
    4: optional list<i32> integerValue;
    5: optional list<i64> longValue;
    6: optional list<double> doubleValue;
    // 7: optional list<string> stringValue;
    8: optional list<binary> binaryValue;
    9: optional list<Fibonacchi> valueValue;
    // 10: optional list<Compact> compactValue;
    // 12: optional list<NotPortableFields> notPortableValue;
} (hazelcast.class.id = "3")

struct PortableSetFields {
    1: optional set<bool> booleanValues;
    2: optional set<byte> byteValues;
    3: optional set<i16> shortValues;
    4: optional set<i32> integerValue;
    5: optional set<i64> longValue;
    6: optional set<double> doubleValue;
    // 7: optional set<string> stringValue;
    8: optional set<binary> binaryValue;
    9: optional set<Fibonacchi> valueValue;
    // 10: optional set<Compact> compactValue;
    // 12: optional set<NotPortableFields> notPortableValue;
} (hazelcast.class.id = "4")

struct PortableMapFields {
    1: optional map<bool,bool> booleanValue;
    2: optional map<byte,byte> byteValue;
    3: optional map<i16,i16> shortValue;
    4: optional map<i32,i32> integerValue;
    5: optional map<i64,i64> longValue;
    6: optional map<double,double> doubleValue;
    // 7: optional map<string,string> stringValue;
    8: optional map<binary,binary> binaryValue;
    9: optional map<Fibonacchi,Fibonacchi> valueValue;
    // 10: optional map<Compact,Compact> compactValue;
    // 12: optional map<i32,NotPortableFields> notPortableValue;
} (hazelcast.class.id = "5")

struct PortableMapListFields {
    1: optional map<i32,list<bool>> booleanValueList;
    2: optional map<i32,list<byte>> byteValueList;
    3: optional map<i32,list<i16>> shortValueList;
    4: optional map<i32,list<i32>> integerValueList;
    5: optional map<i32,list<i64>> longValueList;
    6: optional map<i32,list<double>> doubleValueList;
    // 7: optional map<i32,list<string>> stringValueList;
    8: optional map<i32,list<binary>> binaryValueList;
    9: optional map<i32,list<Fibonacchi>> valueValueList;
    // 10: optional map<i32,list<Compact>> compactValueList;
    // 12: optional map<i32,list<NotPortableFields>> notPortableList;
} (hazelcast.class.id = "6")

struct PortableMapSetFields {
    1: optional map<i32,set<bool>> booleanValueSet;
    2: optional map<i32,set<byte>> byteValueSet;
    3: optional map<i32,set<i16>> shortValueSet;
    4: optional map<i32,set<i32>> integerValueSet;
    5: optional map<i32,set<i64>> longValueSet;
    6: optional map<i32,set<double>> doubleValueSet;
    // 7: optional map<i32,set<string>> stringValueSet;
    8: optional map<i32,set<binary>> binaryValueSet;
    9: optional map<i32,set<Fibonacchi>> valueValueSet;
    // 10: optional map<i32,set<Compact>> compactValueSet;
    // 12: optional map<i32,set<NotPortableFields>> notPortableSet;
} (hazelcast.class.id = "7")

/*
union NotPortableFields {
    1: optional bool booleanValue;
    2: optional byte byteValue;
    3: optional i16 shortValue;
    4: optional i32 integerValue;
    5: optional i64 longValue;
    6: optional double doubleValue;
    7: optional string stringValue;
    8: optional binary binaryValue;
    9: optional Fibonacchi enumValue;
    10: optional Compact compactValue;
} */
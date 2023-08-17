package com.lks.netty.day003.nio.proto;

import com.google.protobuf.*;


/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 17:17
 */
public class Person extends GeneratedMessageV3 implements PersonOrBuilder{

    private static final long serialVersionUID = 0L;

    /**
     * Use Person.newBuilder() to construct.
     * @param builder
     */
    private Person(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }
    
    private Person() {
        name_ = "";
    }

    @java.lang.Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Person(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
            throws InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new java.lang.NullPointerException();
        }
        int mutable_bitField0_ = 0;
        UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
        try {
            boolean done = false;
            while (!done) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 10: {
                        java.lang.String s = input.readStringRequireUtf8();

                        name_ = s;
                        break;
                    }
                    case 16: {

                        age_ = input.readInt32();
                        break;
                    }
                    default: {
                        if (!parseUnknownField(
                                input, unknownFields, extensionRegistry, tag)) {
                            done = true;
                        }
                        break;
                    }
                }
            }
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
            throw new InvalidProtocolBufferException(
                    e).setUnfinishedMessage(this);
        } finally {
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }
    }
    public static final Descriptors.Descriptor getDescriptor() {
        return PersonMessage.internal_static_Person_descriptor;
    }

    @java.lang.Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return PersonMessage.internal_static_Person_fieldAccessorTable
                .ensureFieldAccessorsInitialized(Person.class, Person.Builder.class);
    }

    public static final int NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object name_;
    /**
     * <pre>
     * string 类型
     * </pre>
     *
     * <code>string name = 1;</code>
     */
    @Override
    public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (ref instanceof java.lang.String) {
            return (java.lang.String) ref;
        } else {
            ByteString bs =
                    (ByteString) ref;
            java.lang.String s = bs.toStringUtf8();
            name_ = s;
            return s;
        }
    }
    /**
     * <pre>
     * string 类型
     * </pre>
     *
     * <code>string name = 1;</code>
     */
    @Override
    public ByteString getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof java.lang.String) {
            ByteString b =
                    ByteString.copyFromUtf8(
                            (java.lang.String) ref);
            name_ = b;
            return b;
        } else {
            return (ByteString) ref;
        }
    }

    public static final int AGE_FIELD_NUMBER = 2;
    private int age_;
    /**
     * <pre>
     * int 类型
     * </pre>
     *
     * <code>int32 age = 2;</code>
     */
    @Override
    public int getAge() {
        return age_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1){
            return true;
        }
        if (isInitialized == 0){
            return false;
        }

        memoizedIsInitialized = 1;
        return true;
    }

    @java.lang.Override
    public void writeTo(CodedOutputStream output)
            throws java.io.IOException {
        if (!getNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, name_);
        }
        if (age_ != 0) {
            output.writeInt32(2, age_);
        }
        unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1){
            return size;
        }

        size = 0;
        if (!getNameBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(1, name_);
        }
        if (age_ != 0) {
            size += CodedOutputStream
                    .computeInt32Size(2, age_);
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Person)) {
            return super.equals(obj);
        }
        Person other = (Person) obj;

        if (!getName().equals(other.getName())){
            return false;
        }
        if (getAge() != other.getAge()){
            return false;
        }
        if (!unknownFields.equals(other.unknownFields)) {
            return false;
        }
        return true;
    }

    @java.lang.Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        hash = (37 * hash) + NAME_FIELD_NUMBER;
        hash = (53 * hash) + getName().hashCode();
        hash = (37 * hash) + AGE_FIELD_NUMBER;
        hash = (53 * hash) + getAge();
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    public static Person parseFrom(
            java.nio.ByteBuffer data)
            throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }
    public static Person parseFrom(
            java.nio.ByteBuffer data,
            ExtensionRegistryLite extensionRegistry)
            throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Person parseFrom(
            ByteString data)
            throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }
    public static Person parseFrom(
            ByteString data,
            ExtensionRegistryLite extensionRegistry)
            throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Person parseFrom(byte[] data)
            throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }
    public static Person parseFrom(
            byte[] data,
            ExtensionRegistryLite extensionRegistry)
            throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Person parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }
    public static Person parseFrom(
            java.io.InputStream input,
            ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Person parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }
    public static Person parseDelimitedFrom(
            java.io.InputStream input,
            ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Person parseFrom(
            CodedInputStream input)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }
    public static Person parseFrom(
            CodedInputStream input,
            ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Person prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE
                ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
            GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }
    /**
     * Protobuf type {@code Person}
     */
    public static final class Builder extends
            GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:Person)
            PersonOrBuilder {
        public static final Descriptors.Descriptor
        getDescriptor() {
            return PersonMessage.internal_static_Person_descriptor;
        }

        @java.lang.Override
        protected GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return PersonMessage.internal_static_Person_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            Person.class, Person.Builder.class);
        }

        // Construct using Person.newBuilder()
        private Builder() {
            maybeForceBuilderInitialization();
        }

        private Builder(
                GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            maybeForceBuilderInitialization();
        }
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3
                    .alwaysUseFieldBuilders) {
            }
        }
        @java.lang.Override
        public Builder clear() {
            super.clear();
            name_ = "";

            age_ = 0;

            return this;
        }

        @java.lang.Override
        public Descriptors.Descriptor
        getDescriptorForType() {
            return PersonMessage.internal_static_Person_descriptor;
        }

        @java.lang.Override
        public Person getDefaultInstanceForType() {
            return Person.getDefaultInstance();
        }

        @java.lang.Override
        public Person build() {
            Person result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        @java.lang.Override
        public Person buildPartial() {
            Person result = new Person(this);
            result.name_ = name_;
            result.age_ = age_;
            onBuilt();
            return result;
        }

        @java.lang.Override
        public Builder clone() {
            return super.clone();
        }
        @java.lang.Override
        public Builder setField(
                Descriptors.FieldDescriptor field,
                java.lang.Object value) {
            return super.setField(field, value);
        }
        @java.lang.Override
        public Builder clearField(
                Descriptors.FieldDescriptor field) {
            return super.clearField(field);
        }
        @java.lang.Override
        public Builder clearOneof(
                Descriptors.OneofDescriptor oneof) {
            return super.clearOneof(oneof);
        }
        @java.lang.Override
        public Builder setRepeatedField(
                Descriptors.FieldDescriptor field,
                int index, java.lang.Object value) {
            return super.setRepeatedField(field, index, value);
        }
        @java.lang.Override
        public Builder addRepeatedField(
                Descriptors.FieldDescriptor field,
                java.lang.Object value) {
            return super.addRepeatedField(field, value);
        }
        @java.lang.Override
        public Builder mergeFrom(Message other) {
            if (other instanceof Person) {
                return mergeFrom((Person)other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(Person other) {
            if (other == Person.getDefaultInstance()){
                return this;
            }
            if (!other.getName().isEmpty()) {
                name_ = other.name_;
                onChanged();
            }
            if (other.getAge() != 0) {
                setAge(other.getAge());
            }
            this.mergeUnknownFields(other.unknownFields);
            onChanged();
            return this;
        }

        @java.lang.Override
        public final boolean isInitialized() {
            return true;
        }

        @java.lang.Override
        public Builder mergeFrom(
                CodedInputStream input,
                ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            Person parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException e) {
                parsedMessage = (Person) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        private java.lang.Object name_ = "";
        /**
         * <pre>
         * string 类型
         * </pre>
         *
         * <code>string name = 1;</code>
         */
        @Override
        public java.lang.String getName() {
            java.lang.Object ref = name_;
            if (!(ref instanceof java.lang.String)) {
                ByteString bs =
                        (ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                name_ = s;
                return s;
            } else {
                return (java.lang.String) ref;
            }
        }
        /**
         * <pre>
         * string 类型
         * </pre>
         *
         * <code>string name = 1;</code>
         */
        @Override
        public ByteString getNameBytes() {
            java.lang.Object ref = name_;
            if (ref instanceof String) {
                ByteString b =
                        ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                name_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }
        /**
         * <pre>
         * string 类型
         * </pre>
         *
         * <code>string name = 1;</code>
         */
        public Builder setName(
                java.lang.String value) {
            if (value == null) {
                throw new NullPointerException();
            }

            name_ = value;
            onChanged();
            return this;
        }
        /**
         * <pre>
         * string 类型
         * </pre>
         *
         * <code>string name = 1;</code>
         */
        public Builder clearName() {

            name_ = getDefaultInstance().getName();
            onChanged();
            return this;
        }
        /**
         * <pre>
         * string 类型
         * </pre>
         *
         * <code>string name = 1;</code>
         */
        public Builder setNameBytes(
                ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            checkByteStringIsUtf8(value);

            name_ = value;
            onChanged();
            return this;
        }

        private int age_ ;
        /**
         * <pre>
         * int 类型
         * </pre>
         *
         * <code>int32 age = 2;</code>
         */
        @Override
        public int getAge() {
            return age_;
        }
        /**
         * <pre>
         * int 类型
         * </pre>
         *
         * <code>int32 age = 2;</code>
         */
        public Builder setAge(int value) {

            age_ = value;
            onChanged();
            return this;
        }
        /**
         * <pre>
         * int 类型
         * </pre>
         *
         * <code>int32 age = 2;</code>
         */
        public Builder clearAge() {

            age_ = 0;
            onChanged();
            return this;
        }
        @java.lang.Override
        public final Builder setUnknownFields(
                final UnknownFieldSet unknownFields) {
            return super.setUnknownFields(unknownFields);
        }

        @java.lang.Override
        public final Builder mergeUnknownFields(
                final UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }


        // @@protoc_insertion_point(builder_scope:Person)
    }

    // @@protoc_insertion_point(class_scope:Person)
    private static final Person DEFAULT_INSTANCE;
    static {
        DEFAULT_INSTANCE = new Person();
    }

    public static Person getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static final Parser<Person> PARSER = new AbstractParser<Person>() {
        @java.lang.Override
        public Person parsePartialFrom(
                CodedInputStream input,
                ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            return new Person(input, extensionRegistry);
        }
    };

    public static Parser<Person> parser() {
        return PARSER;
    }

    @java.lang.Override
    public Parser<Person> getParserForType() {
        return PARSER;
    }

    @java.lang.Override
    public Person getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

}

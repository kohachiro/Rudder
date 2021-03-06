// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Logout.proto

package net.combatserver.protobuf;

public final class Logout {
  private Logout() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface LogoutRequestOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required int64 time = 1;
    boolean hasTime();
    long getTime();
  }
  public static final class LogoutRequest extends
      com.google.protobuf.GeneratedMessage
      implements LogoutRequestOrBuilder {
    // Use LogoutRequest.newBuilder() to construct.
    private LogoutRequest(Builder builder) {
      super(builder);
    }
    private LogoutRequest(boolean noInit) {}
    
    private static final LogoutRequest defaultInstance;
    public static LogoutRequest getDefaultInstance() {
      return defaultInstance;
    }
    
    public LogoutRequest getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return net.combatserver.protobuf.Logout.internal_static_net_combatserver_protobuf_LogoutRequest_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return net.combatserver.protobuf.Logout.internal_static_net_combatserver_protobuf_LogoutRequest_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required int64 time = 1;
    public static final int TIME_FIELD_NUMBER = 1;
    private long time_;
    public boolean hasTime() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public long getTime() {
      return time_;
    }
    
    private void initFields() {
      time_ = 0L;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasTime()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt64(1, time_);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, time_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static net.combatserver.protobuf.Logout.LogoutRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static net.combatserver.protobuf.Logout.LogoutRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(net.combatserver.protobuf.Logout.LogoutRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements net.combatserver.protobuf.Logout.LogoutRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return net.combatserver.protobuf.Logout.internal_static_net_combatserver_protobuf_LogoutRequest_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return net.combatserver.protobuf.Logout.internal_static_net_combatserver_protobuf_LogoutRequest_fieldAccessorTable;
      }
      
      // Construct using net.combatserver.protobuf.Logout.LogoutRequest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        time_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return net.combatserver.protobuf.Logout.LogoutRequest.getDescriptor();
      }
      
      public net.combatserver.protobuf.Logout.LogoutRequest getDefaultInstanceForType() {
        return net.combatserver.protobuf.Logout.LogoutRequest.getDefaultInstance();
      }
      
      public net.combatserver.protobuf.Logout.LogoutRequest build() {
        net.combatserver.protobuf.Logout.LogoutRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private net.combatserver.protobuf.Logout.LogoutRequest buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        net.combatserver.protobuf.Logout.LogoutRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public net.combatserver.protobuf.Logout.LogoutRequest buildPartial() {
        net.combatserver.protobuf.Logout.LogoutRequest result = new net.combatserver.protobuf.Logout.LogoutRequest(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.time_ = time_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof net.combatserver.protobuf.Logout.LogoutRequest) {
          return mergeFrom((net.combatserver.protobuf.Logout.LogoutRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(net.combatserver.protobuf.Logout.LogoutRequest other) {
        if (other == net.combatserver.protobuf.Logout.LogoutRequest.getDefaultInstance()) return this;
        if (other.hasTime()) {
          setTime(other.getTime());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasTime()) {
          
          return false;
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              time_ = input.readInt64();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required int64 time = 1;
      private long time_ ;
      public boolean hasTime() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public long getTime() {
        return time_;
      }
      public Builder setTime(long value) {
        bitField0_ |= 0x00000001;
        time_ = value;
        onChanged();
        return this;
      }
      public Builder clearTime() {
        bitField0_ = (bitField0_ & ~0x00000001);
        time_ = 0L;
        onChanged();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:net.combatserver.protobuf.LogoutRequest)
    }
    
    static {
      defaultInstance = new LogoutRequest(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:net.combatserver.protobuf.LogoutRequest)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_net_combatserver_protobuf_LogoutRequest_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_net_combatserver_protobuf_LogoutRequest_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014Logout.proto\022\031net.combatserver.protobu" +
      "f\"\035\n\rLogoutRequest\022\014\n\004time\030\001 \002(\003B\002H\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_net_combatserver_protobuf_LogoutRequest_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_net_combatserver_protobuf_LogoutRequest_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_net_combatserver_protobuf_LogoutRequest_descriptor,
              new java.lang.String[] { "Time", },
              net.combatserver.protobuf.Logout.LogoutRequest.class,
              net.combatserver.protobuf.Logout.LogoutRequest.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}

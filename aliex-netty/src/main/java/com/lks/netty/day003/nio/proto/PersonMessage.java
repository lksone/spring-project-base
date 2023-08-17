package com.lks.netty.day003.nio.proto;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/15 17:20
 */
public class PersonMessage {

    private PersonMessage() {}
    
    
    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite) registry);
    }
    static final Descriptors.Descriptor internal_static_Person_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_Person_fieldAccessorTable;

    private static  Descriptors.FileDescriptor descriptor;


    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }


    static {
        java.lang.String[] descriptorData = {
                "\n\014Person.proto\"#\n\006Person\022\014\n\004name\030\001 \001(\t\022\013" +
                        "\n\003age\030\002 \001(\005B\021B\rPersonMessageP\001b\006proto3"
        };
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            @Override
            public ExtensionRegistry assignDescriptors(
                    Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {}, assigner);
        internal_static_Person_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Person_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_Person_descriptor, new java.lang.String[] { "Name", "Age", });
    }

}

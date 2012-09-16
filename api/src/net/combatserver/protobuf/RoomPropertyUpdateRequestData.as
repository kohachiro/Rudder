package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	import net.combatserver.protobuf.Property;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class RoomPropertyUpdateRequestData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const PROPERTY:FieldDescriptor$TYPE_MESSAGE = new FieldDescriptor$TYPE_MESSAGE("net.combatserver.protobuf.RoomPropertyUpdateRequestData.property", "property", (1 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED, net.combatserver.protobuf.Property);

		public var property:net.combatserver.protobuf.Property;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 1);
			com.google.protobuf.WriteUtils.write$TYPE_MESSAGE(output, this.property);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var property$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (property$count != 0) {
						throw new flash.errors.IOError('Bad data format: RoomPropertyUpdateRequestData.property cannot be set twice.');
					}
					++property$count;
					this.property = new net.combatserver.protobuf.Property();
					com.google.protobuf.ReadUtils.read$TYPE_MESSAGE(input, this.property);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

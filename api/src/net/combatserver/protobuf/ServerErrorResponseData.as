package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	import net.combatserver.protobuf.ReturnStatus;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class ServerErrorResponseData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const ERROR:FieldDescriptor$TYPE_ENUM = new FieldDescriptor$TYPE_ENUM("net.combatserver.protobuf.ServerErrorResponseData.error", "error", (1 << 3) | com.google.protobuf.WireType.VARINT, net.combatserver.protobuf.ReturnStatus);

		public var error:int;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 1);
			com.google.protobuf.WriteUtils.write$TYPE_ENUM(output, this.error);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var error$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (error$count != 0) {
						throw new flash.errors.IOError('Bad data format: ServerErrorResponseData.error cannot be set twice.');
					}
					++error$count;
					this.error = com.google.protobuf.ReadUtils.read$TYPE_ENUM(input);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class PrivateMessageRequestData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const MESSAGE:FieldDescriptor$TYPE_STRING = new FieldDescriptor$TYPE_STRING("net.combatserver.protobuf.PrivateMessageRequestData.message", "message", (1 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED);

		public var message:String;

		/**
		 *  @private
		 */
		public static const USERID:FieldDescriptor$TYPE_INT32 = new FieldDescriptor$TYPE_INT32("net.combatserver.protobuf.PrivateMessageRequestData.userid", "userid", (2 << 3) | com.google.protobuf.WireType.VARINT);

		public var userid:int;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 1);
			com.google.protobuf.WriteUtils.write$TYPE_STRING(output, this.message);
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 2);
			com.google.protobuf.WriteUtils.write$TYPE_INT32(output, this.userid);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var message$count:uint = 0;
			var userid$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (message$count != 0) {
						throw new flash.errors.IOError('Bad data format: PrivateMessageRequestData.message cannot be set twice.');
					}
					++message$count;
					this.message = com.google.protobuf.ReadUtils.read$TYPE_STRING(input);
					break;
				case 2:
					if (userid$count != 0) {
						throw new flash.errors.IOError('Bad data format: PrivateMessageRequestData.userid cannot be set twice.');
					}
					++userid$count;
					this.userid = com.google.protobuf.ReadUtils.read$TYPE_INT32(input);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

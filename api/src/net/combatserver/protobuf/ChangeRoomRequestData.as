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
	public dynamic final class ChangeRoomRequestData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const TOROOMID:FieldDescriptor$TYPE_INT32 = new FieldDescriptor$TYPE_INT32("net.combatserver.protobuf.ChangeRoomRequestData.toRoomId", "toRoomId", (1 << 3) | com.google.protobuf.WireType.VARINT);

		public var toRoomId:int;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 1);
			com.google.protobuf.WriteUtils.write$TYPE_INT32(output, this.toRoomId);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var toRoomId$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (toRoomId$count != 0) {
						throw new flash.errors.IOError('Bad data format: ChangeRoomRequestData.toRoomId cannot be set twice.');
					}
					++toRoomId$count;
					this.toRoomId = com.google.protobuf.ReadUtils.read$TYPE_INT32(input);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

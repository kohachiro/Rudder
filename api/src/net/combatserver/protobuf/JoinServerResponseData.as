package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	import net.combatserver.protobuf.User;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class JoinServerResponseData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const PINGINTERVAL:FieldDescriptor$TYPE_INT32 = new FieldDescriptor$TYPE_INT32("net.combatserver.protobuf.JoinServerResponseData.pingInterval", "pingInterval", (1 << 3) | com.google.protobuf.WireType.VARINT);

		public var pingInterval:int;

		/**
		 *  @private
		 */
		public static const USER:FieldDescriptor$TYPE_MESSAGE = new FieldDescriptor$TYPE_MESSAGE("net.combatserver.protobuf.JoinServerResponseData.user", "user", (2 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED, net.combatserver.protobuf.User);

		public var user:net.combatserver.protobuf.User;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 1);
			com.google.protobuf.WriteUtils.write$TYPE_INT32(output, this.pingInterval);
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 2);
			com.google.protobuf.WriteUtils.write$TYPE_MESSAGE(output, this.user);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var pingInterval$count:uint = 0;
			var user$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (pingInterval$count != 0) {
						throw new flash.errors.IOError('Bad data format: JoinServerResponseData.pingInterval cannot be set twice.');
					}
					++pingInterval$count;
					this.pingInterval = com.google.protobuf.ReadUtils.read$TYPE_INT32(input);
					break;
				case 2:
					if (user$count != 0) {
						throw new flash.errors.IOError('Bad data format: JoinServerResponseData.user cannot be set twice.');
					}
					++user$count;
					this.user = new net.combatserver.protobuf.User();
					com.google.protobuf.ReadUtils.read$TYPE_MESSAGE(input, this.user);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

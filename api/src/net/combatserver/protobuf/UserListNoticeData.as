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
	public dynamic final class UserListNoticeData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const USER:RepeatedFieldDescriptor$TYPE_MESSAGE = new RepeatedFieldDescriptor$TYPE_MESSAGE("net.combatserver.protobuf.UserListNoticeData.user", "user", (1 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED, net.combatserver.protobuf.User);

		[ArrayElementType("net.combatserver.protobuf.User")]
		public var user:Array = [];

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			for (var user$index:uint = 0; user$index < this.user.length; ++user$index) {
				com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 1);
				com.google.protobuf.WriteUtils.write$TYPE_MESSAGE(output, this.user[user$index]);
			}
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					this.user.push(com.google.protobuf.ReadUtils.read$TYPE_MESSAGE(input, new net.combatserver.protobuf.User()));
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

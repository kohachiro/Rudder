package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	import net.combatserver.protobuf.MessageRouter;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class NewMessageNoticeData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const USERID:FieldDescriptor$TYPE_INT32 = new FieldDescriptor$TYPE_INT32("net.combatserver.protobuf.NewMessageNoticeData.userid", "userid", (1 << 3) | com.google.protobuf.WireType.VARINT);

		public var userid:int;

		/**
		 *  @private
		 */
		public static const USERNAME:FieldDescriptor$TYPE_STRING = new FieldDescriptor$TYPE_STRING("net.combatserver.protobuf.NewMessageNoticeData.username", "username", (2 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED);

		public var username:String;

		/**
		 *  @private
		 */
		public static const ROUTER:FieldDescriptor$TYPE_ENUM = new FieldDescriptor$TYPE_ENUM("net.combatserver.protobuf.NewMessageNoticeData.router", "router", (3 << 3) | com.google.protobuf.WireType.VARINT, net.combatserver.protobuf.MessageRouter);

		public var router:int;

		/**
		 *  @private
		 */
		public static const MESSAGE:FieldDescriptor$TYPE_STRING = new FieldDescriptor$TYPE_STRING("net.combatserver.protobuf.NewMessageNoticeData.message", "message", (4 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED);

		public var message:String;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 1);
			com.google.protobuf.WriteUtils.write$TYPE_INT32(output, this.userid);
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 2);
			com.google.protobuf.WriteUtils.write$TYPE_STRING(output, this.username);
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 3);
			com.google.protobuf.WriteUtils.write$TYPE_ENUM(output, this.router);
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 4);
			com.google.protobuf.WriteUtils.write$TYPE_STRING(output, this.message);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var userid$count:uint = 0;
			var username$count:uint = 0;
			var router$count:uint = 0;
			var message$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (userid$count != 0) {
						throw new flash.errors.IOError('Bad data format: NewMessageNoticeData.userid cannot be set twice.');
					}
					++userid$count;
					this.userid = com.google.protobuf.ReadUtils.read$TYPE_INT32(input);
					break;
				case 2:
					if (username$count != 0) {
						throw new flash.errors.IOError('Bad data format: NewMessageNoticeData.username cannot be set twice.');
					}
					++username$count;
					this.username = com.google.protobuf.ReadUtils.read$TYPE_STRING(input);
					break;
				case 3:
					if (router$count != 0) {
						throw new flash.errors.IOError('Bad data format: NewMessageNoticeData.router cannot be set twice.');
					}
					++router$count;
					this.router = com.google.protobuf.ReadUtils.read$TYPE_ENUM(input);
					break;
				case 4:
					if (message$count != 0) {
						throw new flash.errors.IOError('Bad data format: NewMessageNoticeData.message cannot be set twice.');
					}
					++message$count;
					this.message = com.google.protobuf.ReadUtils.read$TYPE_STRING(input);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

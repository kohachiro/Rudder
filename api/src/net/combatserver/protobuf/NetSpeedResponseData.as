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
	public dynamic final class NetSpeedResponseData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const DELAY:FieldDescriptor$TYPE_INT32 = new FieldDescriptor$TYPE_INT32("net.combatserver.protobuf.NetSpeedResponseData.delay", "delay", (1 << 3) | com.google.protobuf.WireType.VARINT);

		public var delay:int;

		/**
		 *  @private
		 */
		public static const TIME:FieldDescriptor$TYPE_INT64 = new FieldDescriptor$TYPE_INT64("net.combatserver.protobuf.NetSpeedResponseData.time", "time", (2 << 3) | com.google.protobuf.WireType.VARINT);

		public var time:Int64;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 1);
			com.google.protobuf.WriteUtils.write$TYPE_INT32(output, this.delay);
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 2);
			com.google.protobuf.WriteUtils.write$TYPE_INT64(output, this.time);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var delay$count:uint = 0;
			var time$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (delay$count != 0) {
						throw new flash.errors.IOError('Bad data format: NetSpeedResponseData.delay cannot be set twice.');
					}
					++delay$count;
					this.delay = com.google.protobuf.ReadUtils.read$TYPE_INT32(input);
					break;
				case 2:
					if (time$count != 0) {
						throw new flash.errors.IOError('Bad data format: NetSpeedResponseData.time cannot be set twice.');
					}
					++time$count;
					this.time = com.google.protobuf.ReadUtils.read$TYPE_INT64(input);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

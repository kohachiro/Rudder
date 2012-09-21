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
	public dynamic final class GetRegionRequestData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const REGIONID:FieldDescriptor$TYPE_INT32 = new FieldDescriptor$TYPE_INT32("net.combatserver.protobuf.GetRegionRequestData.regionid", "regionid", (1 << 3) | com.google.protobuf.WireType.VARINT);

		public var regionid:int;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.VARINT, 1);
			com.google.protobuf.WriteUtils.write$TYPE_INT32(output, this.regionid);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var regionid$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (regionid$count != 0) {
						throw new flash.errors.IOError('Bad data format: GetRegionRequestData.regionid cannot be set twice.');
					}
					++regionid$count;
					this.regionid = com.google.protobuf.ReadUtils.read$TYPE_INT32(input);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}

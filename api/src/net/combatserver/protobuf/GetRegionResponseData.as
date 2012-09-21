package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	import net.combatserver.protobuf.Region;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class GetRegionResponseData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const REGION:FieldDescriptor$TYPE_MESSAGE = new FieldDescriptor$TYPE_MESSAGE("net.combatserver.protobuf.GetRegionResponseData.region", "region", (1 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED, net.combatserver.protobuf.Region);

		public var region:net.combatserver.protobuf.Region;

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 1);
			com.google.protobuf.WriteUtils.write$TYPE_MESSAGE(output, this.region);
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var region$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (region$count != 0) {
						throw new flash.errors.IOError('Bad data format: GetRegionResponseData.region cannot be set twice.');
					}
					++region$count;
					this.region = new net.combatserver.protobuf.Region();
					com.google.protobuf.ReadUtils.read$TYPE_MESSAGE(input, this.region);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}
